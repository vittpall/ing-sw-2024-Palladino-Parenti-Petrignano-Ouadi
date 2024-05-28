package it.polimi.ingsw.model.observer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameListener extends Remote {

    void onTokenColorSelected() throws RemoteException;

    void onGameJoined() throws RemoteException;

    void onGameCreated() throws RemoteException;

    void onChatMessageReceived() throws RemoteException;
}
