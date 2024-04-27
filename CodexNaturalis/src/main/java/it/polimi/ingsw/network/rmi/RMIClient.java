package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.tui.MainMenuState;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;
    ClientState currentState;

    protected RMIClient(VirtualServer server) throws RemoteException {
        this.server = server;
        currentState = new MainMenuState(this);
    }

    private void run() throws RemoteException {
        this.server.connect(this);
        runCli();

    }

    public void setCurrentState(ClientState state) {
        this.currentState = state;
        this.currentState.display();
    }

    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            currentState.display();
            int input = scan.nextInt();
            currentState.inputHandler(input);
        }
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(args[0], 1234);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");

        new RMIClient(server).run();
    }
}
