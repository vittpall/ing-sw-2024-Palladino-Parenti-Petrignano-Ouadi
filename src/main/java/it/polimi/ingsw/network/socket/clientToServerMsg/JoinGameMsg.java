package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

/**
 * Client to server message sent in socket connection to make the player join the specified game
 */
public class JoinGameMsg extends ClientToServerMsg {

    private final int gameId;

    /**
     * Constructor
     *
     * @param username String representing the username of the player
     * @param id       Integer representing the id od the game
     */
    public JoinGameMsg(String username, int id) {
        this.username = username;
        gameId = id;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, IOException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.joinGame(this.gameId, this.username, playerListener));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.JOIN_GAME;
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
