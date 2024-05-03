package it.polimi.ingsw.main;

import it.polimi.ingsw.network.rmi.Client.RMIClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.socket.Client.SocketClient;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
    System.out.println("Please choose the connection type: rmi or socket");
    Scanner scanner = new Scanner(System.in);
    String connectionChoose;

    do {
        connectionChoose = scanner.nextLine();
    } while (!connectionChoose.equals("rmi") && !connectionChoose.equals("socket"));

    try {
        if (connectionChoose.equals("rmi")) {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1234);
            VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");
            new RMIClient(server).run();
        } else {
            Socket serverSocket = new Socket("127.0.0.1", 2345);
            ObjectOutputStream socketTx = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream socketRx = new ObjectInputStream(serverSocket.getInputStream());
            new SocketClient(socketRx, socketTx).run();
        }
    } catch (IOException | ClassNotFoundException | NotBoundException | InterruptedException e) {
        throw new RuntimeException(e);
    }
}
}
