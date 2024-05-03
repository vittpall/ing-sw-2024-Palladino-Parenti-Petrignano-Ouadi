package it.polimi.ingsw.network.RemoteInterfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.model.Game;

import java.util.HashMap;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    void connect(VirtualView client) throws RemoteException;


    boolean checkUsername(String username) throws RemoteException;
    
    HashMap<Integer, Game> getNotStartedGames() throws RemoteException;

    void joinGame(int id, String username) throws RemoteException;

    void createGame(String username, int nPlayers) throws RemoteException;


}
