package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.AllPlayersMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetAllPlayersMsg extends ClientToServerMsg{

    private int gameId;

    public GetAllPlayersMsg(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
        ReturnableObject response = new ReturnableObject();
        response.setArrayListResponse(controller.getAllPlayers(gameId));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new AllPlayersMsg();
    }
}
