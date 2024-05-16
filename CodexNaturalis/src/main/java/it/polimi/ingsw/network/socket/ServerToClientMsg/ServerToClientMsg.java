package it.polimi.ingsw.network.socket.ServerToClientMsg;

import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.Serializable;
import java.lang.reflect.Type;


public class ServerToClientMsg implements Serializable {

    protected ReturnableObject response;
    protected TypeServerToClientMsg type;

    public ServerToClientMsg(TypeServerToClientMsg type) {
        this.type = type;
        this.response = new ReturnableObject<>();
    }
    public void setResponse(ReturnableObject response)
    {
        this.response = response;
    }

    public ReturnableObject getResponse()
    {
        return this.response;
    }

    public void setType(TypeServerToClientMsg type)
    {
        this.type = type;
    }

    public TypeServerToClientMsg getType()
    {
        return this.type;
    }

}
