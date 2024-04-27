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
    public final VirtualServer server;
    ClientState currentState;
    private String username;
    private final Scanner scan;

    protected RMIClient(VirtualServer server) throws RemoteException {
        this.scan = new Scanner(System.in);
        this.server = server;
        currentState = new MainMenuState(this, scan);
    }

    public void setUsername(String username) {
        this.username = username;  // Method to set the username
    }

    public String getUsername() {
        return username;  // Method to get the username
    }

    private void run() throws RemoteException {
        this.server.connect(this);
        runStateLoop();
    }

    public void setCurrentState(ClientState state) {
        this.currentState = state;
    }

    private void runStateLoop() throws RemoteException {
        while (true) {
            currentState.display();
            currentState.promptForInput();
            int input = scan.nextInt();
            scan.nextLine();  // Consume newline left-over
            currentState.inputHandler(input);
        }
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(args[0], 1234);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");

        new RMIClient(server).run();
    }
}
