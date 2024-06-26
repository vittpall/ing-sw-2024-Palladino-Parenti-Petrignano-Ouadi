package it.polimi.ingsw.main;

import it.polimi.ingsw.model.UsefulData;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.remoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.rmi.client.RMIClient;
import it.polimi.ingsw.network.socket.client.SocketClient;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain extends Application {

    private BaseClient client;
    private String serverAddress;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server IP address (empty is 127.0.0.1): ");

        do {
            serverAddress = scanner.nextLine().trim();
            if (serverAddress.isEmpty())
                serverAddress = "127.0.0.1";
            else if (!serverAddress.matches(UsefulData.PATTERN))
                System.out.println("Invalid IP address");
        } while (!serverAddress.matches(UsefulData.PATTERN) && !serverAddress.isEmpty());

        String[] options = {
                "TUI + SOCKET",
                "TUI + RMI",
                "GUI + SOCKET",
                "GUI + RMI"
        };


        System.out.println("Please choose an option:");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        int choice = getValidChoice(scanner, options.length);
        // Add these fields to manage the state based on user input
        boolean useSocket = (choice == 1 || choice == 3);
        boolean useTUI = (choice == 1 || choice == 2);

        if (useSocket) {
            setupSocketClient(useTUI ? "TUI" : "GUI", stage);
        } else {
            setupRMIClient(useTUI ? "TUI" : "GUI", stage);
        }
    }

    /**
     * This method initializes the socket client and starts the client thread to communicate with the server.
     * it shows a message in case of error
     *
     * @param interfaceType  it can be TUI or GUI
     * @param stage          the scene of the client
     */
    private void setupSocketClient(String interfaceType, Stage stage) {
        try {
            Socket serverSocket = new Socket(serverAddress, 2345);
            ObjectOutputStream socketTx = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream socketRx = new ObjectInputStream(serverSocket.getInputStream());
            this.client = new SocketClient(socketRx, socketTx, interfaceType, stage);
            client.run();
        } catch (IOException e) {
            System.err.println("Failed to initialize Socket client: " + e.getMessage());
        }
    }

    /**
     * This method initializes the RMI client and starts the client thread to communicate with the server.
     * it shows a message in case of error.
     *
     * @param interfaceType  it can be TUI or GUI
     * @param stage          the scene of the client
     */
    private void setupRMIClient(String interfaceType, Stage stage) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverAddress, 1234);
            VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");
            this.client = new RMIClient(server, interfaceType, stage);
            client.run();
        } catch (NotBoundException | IOException e) {
            System.err.println("Something went wrong, restart the game...");
        }
    }

    /**
     * this method get the client's input and return an error message in case of invalid input.
     *
     * @param scanner the scanner to get the input
     * @param max     the maximum number of choices
     * @return        the valid choice
     */
    private static int getValidChoice(Scanner scanner, int max) {
        int choice;
        do {
            System.out.print("Enter your choice (1-" + max + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                scanner.next(); // this is important!
                System.out.print("Enter your choice (1-" + max + "): ");
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > max);
        return choice;
    }

    @Override
    public void stop() {
        if (client != null)
            client.close();
    }


}