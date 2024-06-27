package it.polimi.ingsw.main;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.UsefulData;
import it.polimi.ingsw.network.remoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.rmi.server.RMIServer;
import it.polimi.ingsw.network.socket.server.SocketServer;

import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * This class is the main class of the server side.
 * The server has to input the server IP address and starts the RMI server and the socket server.
 */
public class ServerMain {

    /**
     * Main method to start the server
     * It starts the RMI server and the socket server
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        LobbyController lobbyController = new LobbyController();
        setupRMIServer(lobbyController);
        setupSocketServer(lobbyController);
    }

    /**
     * this method sets up the RMI server. it controls the server address is valid and starts the server or send an error message
     *
     * @param lobbyController The lobby controller used at the start of the game
     */
    private static void setupRMIServer(LobbyController lobbyController) {
        try {
            VirtualServer engine = new RMIServer(lobbyController);
            VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
            Scanner scanner = new Scanner(System.in);
            String serverAddress;

            System.out.print("Enter server IP address (empty is 127.0.0.1): ");
            do {
                serverAddress = scanner.nextLine().trim();
                if (serverAddress.isEmpty())
                    serverAddress = "127.0.0.1";
                else if (!serverAddress.matches(UsefulData.PATTERN))
                    System.out.println("Invalid IP address");
            } while (!serverAddress.matches(UsefulData.PATTERN));
            System.out.println("Server IP address: " + serverAddress);

            System.setProperty("java.rmi.server.hostname", serverAddress);
            Registry registry = LocateRegistry.createRegistry(1234);
            registry.rebind("VirtualServer", stub);
        } catch (RemoteException e) {
            System.err.println("Unable to start RMI server, retry...");
        }
    }

    /**
     * this method sets up the socket server and starts it or send an error message if it fails
     *
     * @param lobbyController The lobby controller used at the start of the game
     */
    private static void setupSocketServer(LobbyController lobbyController) {
        try {
            new SocketServer(lobbyController).runServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

