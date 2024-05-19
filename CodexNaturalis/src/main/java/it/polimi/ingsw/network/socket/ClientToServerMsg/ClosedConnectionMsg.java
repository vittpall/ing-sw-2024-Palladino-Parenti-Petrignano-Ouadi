package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class ClosedConnectionMsg extends ClientToServerMsg {

    private final String username;
    private final int idGame;

    public ClosedConnectionMsg(String username, int idGame) {
        this.username = username;
        this.broadCastMessage = "User " + username + " has left the game";
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<String> functionToCall(LobbyController controller) {
        ReturnableObject<String> response = new ReturnableObject<>();
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
    public boolean getDoItNeedToBeBroadcasted() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return this.broadCastMessage;
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return this.idGame;
    }
}
