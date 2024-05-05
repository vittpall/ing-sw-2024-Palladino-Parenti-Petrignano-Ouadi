package it.polimi.ingsw.network.RemoteInterfaces;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.tui.ClientState;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public interface VirtualView extends Remote {

    void setUsername(String username) throws RemoteException;

    HashMap<Integer, Game> getNotStartedGames() throws RemoteException;

    void createGame(String username, int nPlayers) throws RemoteException, InterruptedException;

    void setCurrentState(ClientState state) throws RemoteException;

    String getUsername() throws RemoteException;

    public boolean checkUsername(String username) throws IOException, RemoteException, ClassNotFoundException, InterruptedException;

    void joinGame(int input, String username) throws RemoteException, InterruptedException;
}
