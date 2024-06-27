package it.polimi.ingsw.network.socket.server;


import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

/**
 * The class is used to handle the server in a socket connection
 */
public class SocketServer implements Remote {

    final ServerSocket listenSocket;
    final LobbyController lobbyController;
    static final List<ClientHandler> clients = new ArrayList<>();

    /**
     * Constructor for the SocketServer class
     *
     * @param lobbyController the lobby controller of the game
     * @throws IOException if an I/O error occurs when creating the server socket
     */
    public SocketServer(LobbyController lobbyController) throws IOException {
        this.listenSocket = new ServerSocket(2345);
        this.lobbyController = lobbyController;
    }

    /**
     * Method to run the server and accept incoming connections
     * It creates a new ClientHandler for each client that connects and starts a new thread for each of them
     * The ClientHandler is responsible for the communication with the client and the VirtualView
     * It also starts the VirtualView for the client and catches exceptions that can be thrown during the execution of the method
     *
     * @throws IOException if an I/O error occurs when creating the server socket
     */
    public void runServer() throws IOException {
        Socket clientSocket;
        while ((clientSocket = this.listenSocket.accept()) != null) {
            //this one arrives from the client
            ObjectInputStream socketRx = new ObjectInputStream(clientSocket.getInputStream());
            //this one contains the data sent to the client
            ObjectOutputStream socketTx = new ObjectOutputStream(clientSocket.getOutputStream());

            //this one serves as the remote object for the client
            ClientHandler handler = new ClientHandler(this, socketRx, socketTx, this.lobbyController);

            clients.add(handler);
            System.err.println("New Socket client connected");
            //it's the same as the list of virtual client view
            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException | InterruptedException | ClassNotFoundException |
                         PlaceNotAvailableException e) {
                    throw new RuntimeException(e);
                }
            }, "ClientHandler").start();
        }
    }

}
