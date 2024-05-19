package it.polimi.ingsw.network.socket.ServerToClientMsg;

import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.Serializable;
import java.lang.reflect.Type;


public class ServerToClientMsg implements Serializable {

    protected ReturnableObject response;
    protected TypeServerToClientMsg type;
    protected boolean doItNeedToBeBroadCasted;
    protected int idGame;

    public ServerToClientMsg(TypeServerToClientMsg type, boolean doItNeedToBeBroadCasted) {
        this.type = type;
        this.doItNeedToBeBroadCasted = doItNeedToBeBroadCasted;
        this.response = new ReturnableObject<>();
    }

    public ServerToClientMsg(TypeServerToClientMsg type, boolean doItNeedToBeBroadCasted, int idGame) {
        this.type = type;
        this.doItNeedToBeBroadCasted = doItNeedToBeBroadCasted;
        this.response = new ReturnableObject<>();
        this.idGame = idGame;
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

    public void setIdGame(int idGame)
    {
        this.idGame = idGame;
    }

    public int getIdGame()
    {
        return this.idGame;
    }

    public boolean doItNeedToBeBroadCasted()
    {
        return this.doItNeedToBeBroadCasted;
    }

}
