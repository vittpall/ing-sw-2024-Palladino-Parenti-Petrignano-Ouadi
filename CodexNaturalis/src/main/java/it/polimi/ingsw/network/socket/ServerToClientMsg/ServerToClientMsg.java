package it.polimi.ingsw.network.socket.ServerToClientMsg;

import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.Serializable;

public class ServerToClientMsg implements Serializable {

    protected ReturnableObject response;

    public ServerToClientMsg() {
        this.response = new ReturnableObject();
    }
    public void setResponse(ReturnableObject response)
    {
        this.response = response;
    }

    public ReturnableObject getResponse()
    {
        return this.response;
    }

}
