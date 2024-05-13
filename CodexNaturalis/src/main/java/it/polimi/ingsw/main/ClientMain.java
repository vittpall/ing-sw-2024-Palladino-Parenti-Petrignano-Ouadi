package it.polimi.ingsw.main;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.rmi.Client.RMIClient;
import it.polimi.ingsw.network.socket.Client.SocketClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose the connection type: rmi or socket");
        String connectionType;
        do {
            connectionType = scanner.nextLine();
        } while (!connectionType.equals("rmi") && !connectionType.equals("socket"));

        System.out.println("Please choose the interface type: GUI or TUI");
        String interfaceType;
        do {
            interfaceType = scanner.nextLine();
        } while (!interfaceType.equalsIgnoreCase("GUI") && !interfaceType.equalsIgnoreCase("TUI"));

        try {
            if (connectionType.equals("rmi")) {
                Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1234);
                VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");
                RMIClient client = new RMIClient(server, interfaceType.equalsIgnoreCase("GUI") ? "GUI" : "TUI");
                client.run();
            } else {
                Socket serverSocket = new Socket("127.0.0.1", 2345);
                ObjectOutputStream socketTx = new ObjectOutputStream(serverSocket.getOutputStream());
                ObjectInputStream socketRx = new ObjectInputStream(serverSocket.getInputStream());
                SocketClient client = new SocketClient(socketRx, socketTx, interfaceType.equalsIgnoreCase("GUI") ? "GUI" : "TUI");
                client.run();
            }
        } catch (IOException | ClassNotFoundException | NotBoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
