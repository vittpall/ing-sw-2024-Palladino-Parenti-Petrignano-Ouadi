package it.polimi.ingsw.main;

import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.rmi.VirtualServer;
import it.polimi.ingsw.network.socket.SocketClient;

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
            if(serverIP.equals("defaultIP"))
            {
                //user "" to chose default IP
                defaultIPChosen = true;
                serverIP = "127.0.0.1";
            }
        }

        /* Does the client need to insert his local IP? Does it only have to know the server one?
        System.out.println("Please enter your local IP: ");
        defaultIPChosen = false;
        input = new Scanner(System.in).nextLine();
        if(input.equals("defaultIP"))
        {
            defaultIPChosen = true;
        }
        while(!defaultIPChosen && checkValidity(input))
        {
            System.out.println("Invalid input, please try again");
            input = new Scanner(System.in).nextLine();
            if(input.equals("defaultIP"))
            {
                //user "" to chose default IP
                defaultIPChosen = true;
            }
        }
*/
        System.out.println("Please choose the connection type: rmi or socket");
        do {
            connectionChoose = new Scanner(System.in).nextLine();
        }while(!connectionChoose.equals("rmi") && !connectionChoose.equals("socket"));

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
            }
        }
        else
        {
            Socket serverSocket = null;
            try {
                serverSocket = new Socket(serverIP, 1234);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            InputStreamReader socketRx = null;
            OutputStreamWriter socketTx = null;
            try {
                socketRx = new InputStreamReader(serverSocket.getInputStream());
                socketTx = new OutputStreamWriter(serverSocket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx)).run();
            } catch (RemoteException e) {
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
