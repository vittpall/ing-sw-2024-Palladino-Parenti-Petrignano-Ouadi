package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class ClosedConnectionMsg extends ClientToServerMsg {

    private final String username;

    public ClosedConnectionMsg(String username) {
        this.username = username;
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
}
