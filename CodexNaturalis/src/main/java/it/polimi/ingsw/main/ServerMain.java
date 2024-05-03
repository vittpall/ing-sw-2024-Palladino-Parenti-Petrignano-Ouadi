package it.polimi.ingsw.main;

import it.polimi.ingsw.Controller.LobbyController;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.rmi.Server.RMIServer;
import it.polimi.ingsw.network.socket.Server.SocketServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {


    public static void main(String[] args) {
        LobbyController lobbyController = new LobbyController();
        setupRMIServer(lobbyController);
        setupSocketServer(lobbyController);
    }

    private static void setupRMIServer(LobbyController lobbyController) {
        try {
            VirtualServer engine = new RMIServer(lobbyController);
            VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(1234);
            registry.rebind("VirtualServer", stub);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setupSocketServer(LobbyController lobbyController) {
        try {
            new SocketServer(lobbyController).runServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

