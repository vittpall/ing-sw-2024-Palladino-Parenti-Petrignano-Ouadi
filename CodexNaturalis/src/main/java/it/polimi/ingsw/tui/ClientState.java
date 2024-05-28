package it.polimi.ingsw.tui;

import java.io.IOException;
import java.rmi.Remote;

public interface ClientState extends Remote {

    void display();

    void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException;

    void promptForInput();

    @Override
    String toString();

    void refresh(String msg);
}
