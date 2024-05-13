package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.GameIdMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class CreateGameMsg extends ClientToServerMsg{

    private int numberOfPlayers;
    public CreateGameMsg(String username, int numberOfPlayers) {
        this.username = username;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        this.response.setIntResponse(controller.createGame(this.username, this.numberOfPlayers));
        return this.response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new GameIdMsg();
    }
}
