package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.Controller.LobbyController;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.Serializable;

public abstract class ClientToServerMsg implements Serializable {

    protected String username;
    protected ServerToClientMsg typeofResponse;
    protected String functionToCall;

    public abstract String functionToCall(LobbyController controller);
    public abstract ServerToClientMsg getTypeofResponse();
}
