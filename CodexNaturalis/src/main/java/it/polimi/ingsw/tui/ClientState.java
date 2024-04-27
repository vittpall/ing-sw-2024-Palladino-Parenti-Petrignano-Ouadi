package it.polimi.ingsw.tui;

import java.rmi.RemoteException;

public interface ClientState {

    void display();

    void inputHandler(int input) throws RemoteException;

    void promptForInput();
}
