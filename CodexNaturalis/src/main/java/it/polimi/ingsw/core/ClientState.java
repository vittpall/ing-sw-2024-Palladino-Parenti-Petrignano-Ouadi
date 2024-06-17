package it.polimi.ingsw.core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientState extends Remote {

    void display() throws RemoteException;

    @Override
    String toString();

}
