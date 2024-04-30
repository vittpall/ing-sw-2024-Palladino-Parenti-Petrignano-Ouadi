package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.rmi.VirtualServer;
import it.polimi.ingsw.network.rmi.VirtualView;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;

public class ServerProxy implements VirtualServer {
    final PrintWriter output;

    public ServerProxy(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {

    }

    @Override
    public void reset() throws RemoteException {

    }

    @Override
    public boolean checkUsername(String username) throws RemoteException {
        return false;
    }

    @Override
    public HashMap<Integer, Game> getNotStartedGames() throws RemoteException {
        return null;
    }

    @Override
    public void joinGame(int id, String username) throws RemoteException {

    }

    @Override
    public void createGame(String username, int nPlayers) throws RemoteException {

    }

    @Override
    public void sendMessage(String username, String receiver, String message) throws RemoteException {

    }
}


