package it.polimi.ingsw.core;

import java.rmi.Remote;

public interface ClientState extends Remote {

    void display();

    @Override
    String toString();

}
