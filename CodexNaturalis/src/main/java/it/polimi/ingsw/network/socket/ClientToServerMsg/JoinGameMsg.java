package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.GameIdMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class JoinGameMsg extends ClientToServerMsg{

    private int id;

    public JoinGameMsg (String username, int id) {
        this.username = username;
        this.id = id;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.joinGame(this.id, this.username));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new GameIdMsg();
    }
}
