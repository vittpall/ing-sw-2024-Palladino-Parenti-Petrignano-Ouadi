package it.polimi.ingsw.main;

import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.rmi.Client.RMIClient;
import it.polimi.ingsw.network.socket.Client.SocketClient;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain extends Application {

    private BaseClient client;
    private String serverAddress;

    public static void main(String[] args) {
        launch(args); // Use launch to start JavaFX application and pass arguments
    }

    @Override
    public void start(Stage stage) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server IP address (empty is 127.0.0.1): ");
        serverAddress = scanner.nextLine().trim();
        if (!serverAddress.matches(PATTERN) && !serverAddress.isEmpty()) {
            System.out.println("Invalid IP address");
            System.exit(0);
        } else if (serverAddress.isEmpty()) {
            serverAddress = "127.0.0.1";
        }
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

        try {
            if (useSocket) {
                setupSocketClient(useTUI ? "TUI" : "GUI", stage);
            } else {
                setupRMIClient(useTUI ? "TUI" : "GUI", stage);
            }
        } catch (Exception e) {
            System.out.println("Failed to initialize client: " + e.getMessage());
        }
    }

    private void setupSocketClient(String interfaceType, Stage stage) throws IOException, ClassNotFoundException, InterruptedException {
        ObjectOutputStream socketTx;
        ObjectInputStream socketRx;
        try (Socket serverSocket = new Socket(serverAddress, 2345)) {
            socketTx = new ObjectOutputStream(serverSocket.getOutputStream());
            socketRx = new ObjectInputStream(serverSocket.getInputStream());
            this.client = new SocketClient(socketRx, socketTx, interfaceType, stage);
            client.run();
        }
    }

    private void setupRMIClient(String interfaceType, Stage stage) throws Exception {
        Registry registry = LocateRegistry.getRegistry(serverAddress, 1234);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");
        this.client = new RMIClient(server, interfaceType, stage);
        client.run();
    }

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
    public void stop() throws IOException, InterruptedException {
        if (client != null)
            client.close();
    }


}