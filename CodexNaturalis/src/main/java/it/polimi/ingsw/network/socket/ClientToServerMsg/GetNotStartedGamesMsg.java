package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.AvailableGamesMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

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
    public ServerToClientMsg getTypeofResponse() {
        return new AvailableGamesMsg();
    }
}
