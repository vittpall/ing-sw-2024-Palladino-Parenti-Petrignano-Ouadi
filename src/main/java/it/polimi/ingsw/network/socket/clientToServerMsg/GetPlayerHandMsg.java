package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.util.ArrayList;

/**
 * Client to server message sent in socket connection to get the hand of the specified player
 */
public class GetPlayerHandMsg extends ClientToServerMsg {
    private final int idGame;
    private final int idPlayer;

    /**
     * Constructor
     *
     * @param idGame   Integer representing the id of the game
     * @param idPlayer Integer representing the id of the client into the game
     */
    public GetPlayerHandMsg(int idGame, int idPlayer) {
        this.idGame = idGame;
        this.idPlayer = idPlayer;
    }


    @Override
    public ReturnableObject<ArrayList<GameCard>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<ArrayList<GameCard>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getPlayerHand(idGame, idPlayer));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_PLAYER_HAND;
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
