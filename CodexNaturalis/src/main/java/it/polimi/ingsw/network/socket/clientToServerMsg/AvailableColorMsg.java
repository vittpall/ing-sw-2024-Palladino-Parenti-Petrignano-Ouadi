package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.util.ArrayList;

/**
 * Client to server message sent in socket connection to get the available token colors
 */
public class AvailableColorMsg extends ClientToServerMsg {
    private final int idGame;

    /**
     * Class constructor
     *
     * @param username String representing the username of the player
     * @param idGame   Integer representing the id of the specific game
     */
    public AvailableColorMsg(String username, int idGame) {
        this.username = username;
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<ArrayList<TokenColor>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<ArrayList<TokenColor>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getAvailableColors(idGame, playerListener));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.AVAILABLE_COLORS;
    }

    @Override
    public int getIdGame() {
        return this.idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
