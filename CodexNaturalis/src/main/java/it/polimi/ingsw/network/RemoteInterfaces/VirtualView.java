package it.polimi.ingsw.network.RemoteInterfaces;

import it.polimi.ingsw.tui.ClientState;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface VirtualView extends Remote{

    VirtualServer getServer() throws RemoteException;

    void setUsername(String username) throws RemoteException;

    void setCurrentState(ClientState state) throws RemoteException;

    String getUsername() throws RemoteException;

    public boolean checkUsername(String username) throws IOException, RemoteException, ClassNotFoundException;



}
