package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class ClosedConnectionMsg extends ClientToServerMsg {

    private final String username;

    public ClosedConnectionMsg(String username) {
        this.username = username;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) {
        ReturnableObject toReturn = new ReturnableObject();
        toReturn.setStringResponse("connection closed");
        return null;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return null;
    }
}
