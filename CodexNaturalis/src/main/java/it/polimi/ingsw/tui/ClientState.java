package it.polimi.ingsw.tui;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientState extends Remote {

    void display();

    void inputHandler(int input) throws RemoteException;

    void promptForInput();
}
