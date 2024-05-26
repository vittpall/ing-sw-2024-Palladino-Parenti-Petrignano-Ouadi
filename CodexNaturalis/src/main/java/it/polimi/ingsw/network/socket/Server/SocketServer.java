package it.polimi.ingsw.network.socket.Server;


import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

public class SocketServer implements Remote {

    final ServerSocket listenSocket;
    final LobbyController lobbyController;
    static final List<ClientHandler> clients = new ArrayList<>();

    public SocketServer(LobbyController lobbyController) throws IOException {
        this.listenSocket = new ServerSocket(2345);
        this.lobbyController = lobbyController;
    }

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
            //it's the same as the list of virtual client view
            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException | InterruptedException | ClassNotFoundException | RequirementsNotMetException |
                         PlaceNotAvailableException | CardNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }, "ClientHandler").start();
        }
    }


    public static void broadCastWhatHappened(ReturnableObject messageToBroadCast, TypeServerToClientMsg type, int idGame) throws IOException {
        ServerToClientMsg receivedMessage = new ServerToClientMsg(type, true, idGame);
        ReturnableObject<Integer> test = new ReturnableObject<>();
        test.setResponseReturnable(1);
        //receivedMessage.setResponse(messageToBroadCast);
        for (ClientHandler client : clients) {
            client.sendMessage(receivedMessage);
        }
    }
}
