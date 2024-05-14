package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.AllPlayersMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.util.ArrayList;

public class GetAllPlayersMsg extends ClientToServerMsg{

    private int gameId;

    public GetAllPlayersMsg(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public ReturnableObject<ArrayList<Player>> functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
        ReturnableObject<ArrayList<Player>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getAllPlayers(gameId));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new AllPlayersMsg();
    }
}
