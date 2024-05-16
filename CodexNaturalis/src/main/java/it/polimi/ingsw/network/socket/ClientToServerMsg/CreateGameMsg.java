package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class CreateGameMsg extends ClientToServerMsg{

    private final int numberOfPlayers;
    public CreateGameMsg(String username, int numberOfPlayers) {
        this.username = username;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.createGame(this.username, this.numberOfPlayers));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.CREATED_GAME;
    }
}
