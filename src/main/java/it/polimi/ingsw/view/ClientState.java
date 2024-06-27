package it.polimi.ingsw.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for the client state. In it used to display the state of the client both in GUI and TUI mode
 */
public interface ClientState extends Remote {
    /**
     * Display the state of the client. In GUI mode it set and starts the stage of every state,
     * in TUI mode it prints the description the state
     *
     * @throws RemoteException if any error occurs during the remote invocation
     */
    void display() throws RemoteException;

    @Override
    String toString();
}
