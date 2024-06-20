package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

public class ClosedConnectionMsg extends ClientToServerMsg {

    private final String username;
    private final int idGame;

    public ClosedConnectionMsg(String username, int idGame) {
        this.username = username;
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<String> functionToCall(LobbyController controller, GameListener playerListener) throws IOException {
        ReturnableObject<String> response = new ReturnableObject<>();
        controller.closeGame(idGame, username);
        response.setResponseReturnable("connection closed");
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.CONNECTION_CLOSED;
    }


    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return this.idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
