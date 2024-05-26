package it.polimi.ingsw.tui;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientState extends Remote {

    void display();

    void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException;

    void promptForInput();

    @Override
    String toString();
}
