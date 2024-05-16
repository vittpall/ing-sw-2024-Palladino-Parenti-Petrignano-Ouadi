package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.util.HashMap;

public class GetNotStartedGamesMsg extends ClientToServerMsg{

    public GetNotStartedGamesMsg() {
        super();
    }

    @Override
    public ReturnableObject<HashMap<Integer, Game>> functionToCall(LobbyController controller) {
        ReturnableObject<HashMap<Integer, Game>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getVisibleGames());
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.NOT_STARTED_GAMES;
    }
}
