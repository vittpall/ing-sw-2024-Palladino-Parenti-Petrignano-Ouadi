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
import java.rmi.RemoteException;

public class ClientHandler implements GameListener {
    final SocketServer server;
    final transient ObjectInputStream input;
    final LobbyController controller;
    final transient ObjectOutputStream output;
    Integer gameId;

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
                output.writeObject(response);
                output.flush();
                output.reset();
            }
        } catch (IOException e) {
            //technically heartbeat is not useful anymore if everytime a client disconnect
            //This catch is taken when the client disconnects from the server
            closeClient();
            //TODO add logic to remove the user
        } catch (ClassNotFoundException | InterruptedException | CardNotFoundException |
                 PlaceNotAvailableException | RequirementsNotMetException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() throws IOException {
        input.close();
        output.close();
        System.out.println("Client disconnected");
        if (gameId != null)
            controller.closeGame(gameId);
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
    public void update(ServerNotification notification) throws RemoteException {
        try {
            output.writeObject(notification);
            output.flush();
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
