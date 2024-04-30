package it.polimi.ingsw.main;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.network.rmi.VirtualServer;
import it.polimi.ingsw.network.socket.SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerMain {
    public static void main(String[] args)
    {

        System.out.println("Server\n");
        System.out.println("Please enter the IP address of the server you want to connect to: ");

        String name = "VirtualServer";
        try {
            VirtualServer engine = new RMIServer();
            VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(1234);
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server bound");

        ServerSocket listenSocket;
        try {
            listenSocket = new ServerSocket(2345);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            new SocketServer(listenSocket, new LobbyController()).runServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

