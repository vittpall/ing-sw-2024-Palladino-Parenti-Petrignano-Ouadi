package it.polimi.ingsw.network.socket.ServerToClientMsg;

import java.io.Serializable;

public abstract class ServerToClientMsg implements Serializable {

    public abstract void setResponse(String response);
    public abstract String getResponse();

}
