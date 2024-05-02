package it.polimi.ingsw.network.socket.Server;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocketServer implements VirtualServer, Remote {

    final ServerSocket listenSocket;
    final LobbyController lobbyController;
    final List<ClientHandler> clients = new ArrayList<>();

    public SocketServer(ServerSocket listenSocket, LobbyController controller) {
        this.listenSocket = listenSocket;
        this.lobbyController = controller;
    }

    public void runServer() throws IOException {
        Socket clientSocket = null;
        while ((clientSocket = this.listenSocket.accept()) != null) {
            //this one arrives from the client
            ObjectInputStream socketRx = new ObjectInputStream(clientSocket.getInputStream());
            //this one contains the data sent to the client
            ObjectOutputStream socketTx = new ObjectOutputStream(clientSocket.getOutputStream());

            //this one serves as the remote object for the client
            ClientHandler handler = new ClientHandler(this, socketRx, socketTx, this.lobbyController);

            clients.add(handler);
            //it's the same as the list of virtual client view
            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {

    }

    @Override
    public void reset() throws RemoteException {

    }

    public boolean checkUsername(String username) throws RemoteException {
        return lobbyController.checkUsername(username);
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
