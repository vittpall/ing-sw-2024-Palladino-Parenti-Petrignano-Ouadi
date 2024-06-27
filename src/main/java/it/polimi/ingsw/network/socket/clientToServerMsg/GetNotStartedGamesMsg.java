package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.util.ArrayList;

/**
 * Client to server message sent in socket connection to get a list of the games that has not started yet
 */
public class GetNotStartedGamesMsg extends ClientToServerMsg {
    /**
     * Constructor
     */
    public GetNotStartedGamesMsg() {
        super();
    }

    @Override
    public ReturnableObject<ArrayList<Integer>> functionToCall(LobbyController controller, GameListener playerListener) {
        ReturnableObject<ArrayList<Integer>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getVisibleGames(playerListener));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.NOT_STARTED_GAMES;
    }


    @Override
    public int getIdGame() {
        return -1;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
