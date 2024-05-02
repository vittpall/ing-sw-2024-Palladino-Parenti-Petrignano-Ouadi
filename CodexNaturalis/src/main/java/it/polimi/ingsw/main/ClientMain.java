package it.polimi.ingsw.main;

import it.polimi.ingsw.network.rmi.Client.RMIClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.socket.Client.SocketClient;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientMain {

    public static void main(String[] args)
    {
        System.out.println("Client\n");
        System.out.println("Please enter the IP address of the server you want to connect to: ");

        String input;
        String connectionChoose;
        //we can save those variable in another class like a configuration class with all the important values
        //1 rmi
        //2 socket
        boolean defaultIPChosen = false;
        String serverIP = new Scanner(System.in).nextLine();
        if(serverIP.equals("defaultIP"))
        {
            defaultIPChosen = true;
            serverIP = "127.0.0.1";
        }
        while(!defaultIPChosen && checkValidity(serverIP))
        {
            System.out.println("Invalid input, please try again");
            serverIP = new Scanner(System.in).nextLine();
            if(serverIP.equals("defaultIP") && checkValidity(serverIP))
            {
                defaultIPChosen = true;
                serverIP = "127.0.0.1";
            }
        }

        System.out.println("Please choose the connection type: rmi or socket");
        do {
            connectionChoose = new Scanner(System.in).nextLine();
        }while(!connectionChoose.equals("rmi") && !connectionChoose.equals("socket"));

        //rmi
        if(connectionChoose.equals("rmi"))
        {
            try {
                Registry registry = LocateRegistry.getRegistry(serverIP, 1234);
                VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");
                new RMIClient(server).run();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {//socket
            Socket serverSocket = null;
            try {
                serverSocket = new Socket(serverIP, 2345);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ObjectInputStream socketRx = null;
            ObjectOutputStream socketTx = null;
            try {
                socketTx = new ObjectOutputStream(serverSocket.getOutputStream());
                socketRx = new ObjectInputStream(serverSocket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                new SocketClient(socketRx, socketTx).run();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static boolean checkValidity(String input)
    {
        String pattern = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)[.]((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)|([0-9]{1,2}))$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }
}
