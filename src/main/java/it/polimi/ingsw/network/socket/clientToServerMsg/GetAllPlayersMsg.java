package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.util.ArrayList;

/**
 * Client to server message sent in socket connection to get all the players of the specified game
 */
public class GetAllPlayersMsg extends ClientToServerMsg {

    private final int gameId;

    /**
     * Constructor
     *
     * @param gameId Integer representing the id of the game
     */
    public GetAllPlayersMsg(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public ReturnableObject<ArrayList<Player>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<ArrayList<Player>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getAllPlayers(gameId));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.ALL_PLAYERS;
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
