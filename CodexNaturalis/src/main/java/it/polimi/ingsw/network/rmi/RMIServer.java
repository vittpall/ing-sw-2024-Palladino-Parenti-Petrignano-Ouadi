package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Game;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RMIServer implements VirtualServer {
    final List<VirtualView> clients = new ArrayList<>();

    private ArrayList<String> privateChat = new ArrayList<>();
    private ArrayList<String> globalChat = new ArrayList<>();

    private LobbyController lobbyController;

    public RMIServer() throws RemoteException {
        super();  // Call the constructor of UnicastRemoteObject
        lobbyController = new LobbyController();
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        System.err.println("new client connected");
        this.clients.add(client);
    }

    @Override
    public void reset() throws RemoteException {
        System.err.println("reset request");

    }

    @Override
    public boolean checkUsername(String username) throws RemoteException {
        return lobbyController.checkUsername(username);
    }

    @Override
    public HashMap<Integer, Game> getNotStartedGames() throws RemoteException {
        return lobbyController.getVisibleGames();
    }

    @Override
    public void joinGame(int id, String username) throws RemoteException {
        lobbyController.joinGame(id, username);
    }

    @Override
    public void createGame(String username, int nPlayers) throws RemoteException {
        lobbyController.createGame(username, nPlayers);
    }

    @Override
    public void sendMessage(String sender, String receiver, String message) throws RemoteException {
        String toSave = sender + ": " + message;
        if(receiver != null)
            privateChat.add(toSave);
        else
            globalChat.add(toSave);

    }
/*
    public static void main(String[] args) throws RemoteException {
        String name = "VirtualServer";

        VirtualServer engine = new RMIServer();
        VirtualServer stub =
                (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(name, stub);
        System.out.println("Server bound");
    }
    */
}
