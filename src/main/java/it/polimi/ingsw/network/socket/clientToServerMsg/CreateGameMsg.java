package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

/**
 * Client to server message sent in socket connection to create a game
 */
public class CreateGameMsg extends ClientToServerMsg {

    private final int numberOfPlayers;
    private int gameId;

    /**
     * Constructor
     *
     * @param username        String representing the user's username
     * @param numberOfPlayers Integer representing the number of player of the game
     */
    public CreateGameMsg(String username, int numberOfPlayers) {
        this.username = username;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, IOException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.createGame(this.username, this.numberOfPlayers, playerListener));
        gameId = response.getResponseReturnable();
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.CREATED_GAME;
    }


    @Override
    public int getIdGame() {
        return gameId;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
