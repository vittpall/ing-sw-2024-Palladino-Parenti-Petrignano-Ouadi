package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.controller.LobbyController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

    final ServerSocket listenSocket;
    final LobbyController controller;
    final List<ClientHandler> clients = new ArrayList<>();

    public SocketServer(ServerSocket listenSocket, LobbyController controller) {
        this.listenSocket = listenSocket;
        this.controller = controller;
    }

    public void runServer() throws IOException {
        Socket clientSocket = null;
        while ((clientSocket = this.listenSocket.accept()) != null) {
            //this one arrives from the client
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            //this one contains the data sent to the client
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            //this one serves as the remote object for the client
            ClientHandler handler = new ClientHandler(this, new BufferedReader(socketRx), new BufferedWriter(socketTx), this.controller);

            clients.add(handler);
            //it's the same as the list of virtual client view
            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

}
