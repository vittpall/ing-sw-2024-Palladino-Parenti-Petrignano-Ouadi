package it.polimi.ingsw.network.socket.server;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.notifications.CloseGameNotification;
import it.polimi.ingsw.network.notifications.ServerNotification;
import it.polimi.ingsw.network.socket.clientToServerMsg.ClientToServerMsg;
import it.polimi.ingsw.network.socket.serverToClientMsg.ServerToClientMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The class is used to handle the communication between the server and the client
 */
public class ClientHandler implements GameListener {
    final SocketServer server;
    final transient ObjectInputStream input;
    final LobbyController controller;
    final transient ObjectOutputStream output;
    private String clientUsername;
    private Integer gameId;

    /**
     * Constructor for the ClientHandler class
     *
     * @param server     the server that the client is connected to
     * @param input      the input stream of the client
     * @param output     the output stream of the client
     * @param controller the controller of the lobby
     */
    public ClientHandler(SocketServer server, ObjectInputStream input, ObjectOutputStream output, LobbyController controller) {
        this.server = server;
        this.input = input;
        this.controller = controller;
        this.output = output;
    }

    /**
     * Method to run the virtual view for the client and handle the communication between the client and the server
     * it handles the beginning and the end of the game: the creation of the game, the joining of the game, the disconnection of the client
     *
     * @throws IOException                if an I/O error occurs when creating the server socket
     * @throws ClassNotFoundException     if the class of a serialized object cannot be found
     * @throws InterruptedException       if a thread is interrupted
     * @throws PlaceNotAvailableException if the place is not available
     */
    public void runVirtualView() throws IOException, ClassNotFoundException, InterruptedException, PlaceNotAvailableException {
        ClientToServerMsg request;
        ServerToClientMsg response;
        try {
            while (true) {
                synchronized (input) {
                    request = (ClientToServerMsg) input.readObject();
                }
                response = new ServerToClientMsg(request.getType());
                response.setResponse(request.functionToCall(controller, this));
                if (request.getType() == TypeServerToClientMsg.JOIN_GAME || request.getType() == TypeServerToClientMsg.CREATED_GAME) {
                    gameId = request.getIdGame();
                }
                if (request.getType() == TypeServerToClientMsg.USER_ALREADY_TAKEN) {
                    clientUsername = request.getUsername();
                }
                if (request.getType() == TypeServerToClientMsg.CLOSE_GAME_WHEN_ENDED) {
                    gameId = null;
                }
                sendMessage(response);
            }
        } catch (IOException e) {
            closeClient();
        } catch (ClassNotFoundException | InterruptedException |
                 PlaceNotAvailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method closes the client after his disconnection, removes the username from the list of the usernames
     * and closes the game if the client was in a game
     *
     * @throws IOException if an I/O error occurs when creating the server socket
     */
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


    /**
     * this method sends a message from the server to the client
     *
     * @param msgToBroadCast the message that the server wants to send to the client
     * @throws IOException if an I/O error occurs when creating the server socket
     */
    public void sendMessage(ServerToClientMsg msgToBroadCast) throws IOException {
        synchronized (output) {
            output.writeObject(msgToBroadCast);
            output.flush();
            output.reset();
        }

    }


    @Override
    public void update(ServerNotification notification) throws IOException {
        //i need to remove the gameId to avoid the server the close a game that is already closed
        if (notification instanceof CloseGameNotification)
            gameId = null;
        synchronized (output) {
            output.writeObject(notification);
            output.flush();
            output.reset();
        }
    }

    /**
     * Method to get the username of the client
     *
     * @return the username of the client
     */
    public String getUsername() {
        return clientUsername;
    }

}
