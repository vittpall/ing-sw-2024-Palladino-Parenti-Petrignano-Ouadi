package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.IOException;

public class JoinGameMsg extends ClientToServerMsg {

    private int gameId;

    public JoinGameMsg(String username, int id) {
        this.username = username;
        gameId = id;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, IOException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.joinGame(this.gameId, this.username, playerListener));
        return response;
    }


    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.JOIN_GAME;
    }


    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return gameId;
    }
}
