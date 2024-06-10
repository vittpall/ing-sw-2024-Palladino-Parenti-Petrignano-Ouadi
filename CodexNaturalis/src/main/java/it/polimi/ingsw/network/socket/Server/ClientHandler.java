package it.polimi.ingsw.network.socket.Server;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.notifications.ServerNotification;
import it.polimi.ingsw.network.socket.ClientToServerMsg.ClientToServerMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.rmi.RemoteException;

public class ClientHandler implements GameListener {
    final SocketServer server;
    final transient ObjectInputStream input;
    final LobbyController controller;
    final transient ObjectOutputStream output;
    private String clientUsername;
    private Integer gameId;

    public ClientHandler(SocketServer server, ObjectInputStream input, ObjectOutputStream output, LobbyController controller) {
        this.server = server;
        this.input = input;
        this.controller = controller;
        this.output = output;
    }

    public void runVirtualView() throws IOException, ClassNotFoundException, InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ClientToServerMsg request;
        ServerToClientMsg response;
        try {
            while ((request = (ClientToServerMsg) input.readObject()) != null) {
                response = new ServerToClientMsg(request.getType());
                response.setResponse(request.functionToCall(controller, this));
                if (request.getType() == TypeServerToClientMsg.JOIN_GAME || request.getType() == TypeServerToClientMsg.CREATED_GAME) {
                    gameId = request.getIdGame();
                }
                if (request.getType() == TypeServerToClientMsg.USER_ALREADY_TAKEN) {
                    clientUsername = request.getUsername();
                }
                output.writeObject(response);
                output.flush();
                output.reset();
            }
        } catch (SocketException e) {
            closeClient();
        } catch (IOException | ClassNotFoundException | InterruptedException | CardNotFoundException |
                 PlaceNotAvailableException | RequirementsNotMetException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() throws IOException {
        input.close();
        output.close();
        System.out.println("Client disconnected(SocketClient)");
        if (clientUsername != null) {
            controller.removeUsername(clientUsername);
            if (gameId != null)
                controller.closeGame(gameId, clientUsername);
        }
        Thread.currentThread().interrupt();
    }


    public void sendMessage(ServerToClientMsg msgToBroadCast) throws IOException {
        output.writeObject(msgToBroadCast);
        output.flush();
        output.reset();
    }

    /**
     * @param notification the notification to send to the client
     * @throws RemoteException if the client is not reachable
     */
    @Override
    public void update(ServerNotification notification) throws IOException {
        output.writeObject(notification);
        output.flush();
        output.reset();
    }

    public String getUsername() {
        return clientUsername;
    }

}
