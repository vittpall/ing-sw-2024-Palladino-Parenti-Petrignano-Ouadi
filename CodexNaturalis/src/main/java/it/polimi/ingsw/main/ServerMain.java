package it.polimi.ingsw.main;

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

        String input;
        String connectionChoose;
        //we're going to initiliaze both the connection

        boolean defaultIPChosen = false;
        input = new Scanner(System.in).nextLine();
        if(input.equals("defaultIP"))
        {
            defaultIPChosen = true;
        }
        while(!defaultIPChosen)
        {
            System.out.println("Invalid input, please try again");
            input = new Scanner(System.in).nextLine();
            if(input.equals("defaultIP"))
            {
                //user "" to chose default IP
                defaultIPChosen = true;
            }
        }

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

        /*
        ServerSocket listenSocket = null;
        try {
            listenSocket = new ServerSocket(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */


    //        new SocketServer(listenSocket, new AdderController(1)).runServer();
    }

    private static boolean checkValidity(String input)
    {
        String pattern = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)[.]((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)|([0-9]{1,2}))$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }
}

