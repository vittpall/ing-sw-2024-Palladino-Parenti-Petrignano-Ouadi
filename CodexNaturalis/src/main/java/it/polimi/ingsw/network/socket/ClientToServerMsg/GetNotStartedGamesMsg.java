package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.AvailableGamesMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetNotStartedGamesMsg extends ClientToServerMsg{

    public GetNotStartedGamesMsg() {
        super();
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) {
        this.response.setGames(controller.getVisibleGames());
        return this.response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new AvailableGamesMsg();
    }
}
