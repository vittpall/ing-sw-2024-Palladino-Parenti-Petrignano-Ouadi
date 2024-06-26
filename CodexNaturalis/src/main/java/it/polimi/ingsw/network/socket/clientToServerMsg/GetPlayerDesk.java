package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.awt.*;
import java.util.HashMap;

/**
 * Client to server message sent in socket connection to get the desk of a specified player
 */
public class GetPlayerDesk extends ClientToServerMsg {
    private final int idGame;
    private final int idPlayer;

    /**
     * Constructor
     *
     * @param idGame   Integer representing the id of the game
     * @param idPlayer Integer representing the id of the client into the game
     */
    public GetPlayerDesk(int idGame, int idPlayer) {
        this.idGame = idGame;
        this.idPlayer = idPlayer;
    }

    @Override
    public ReturnableObject<HashMap<Point, GameCard>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<HashMap<Point, GameCard>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getPlayerDesk(idGame, idPlayer));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_PLAYER_DESK;
    }


    @Override
    public int getIdGame() {
        return idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
