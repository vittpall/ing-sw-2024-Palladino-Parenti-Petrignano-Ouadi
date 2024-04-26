package it.polimi.ingsw.network.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;

    protected RMIClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }

    private void run() throws RemoteException {
        this.server.connect(this);
        runCli();
    }

    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        while (true) {

        }
    }


    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(args[0], 1234);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");

        new RMIClient(server).run();
    }
}
