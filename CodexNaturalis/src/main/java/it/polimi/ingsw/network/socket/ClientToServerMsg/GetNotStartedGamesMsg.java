package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetNotStartedGamesMsg extends ClientToServerMsg{

    public GetNotStartedGamesMsg() {
        super();
    }

    @Override
    public ReturnableObject<ArrayList<Integer>> functionToCall(LobbyController controller, GameListener playerListener) {
        ReturnableObject<ArrayList<Integer>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getVisibleGames());
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.NOT_STARTED_GAMES;
    }

    /**
     * @return
     */
    @Override
    public boolean getDoItNeedToBeBroadcasted() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return -1;
    }
}
