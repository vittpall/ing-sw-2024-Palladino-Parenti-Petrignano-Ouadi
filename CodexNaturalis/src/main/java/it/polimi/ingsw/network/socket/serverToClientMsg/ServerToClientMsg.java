package it.polimi.ingsw.network.socket.serverToClientMsg;

import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.Serializable;
import java.rmi.RemoteException;


public class ServerToClientMsg implements Serializable {

    protected ReturnableObject response;
    protected TypeServerToClientMsg type;
    protected int idGame;

    /**
     * Constructor
     *
     * @param type  is the type of the message
     */
    public ServerToClientMsg(TypeServerToClientMsg type) {
        this.type = type;
        this.response = new ReturnableObject<>();
    }

    /**
     * setter for the response
     * @param response is the response to set
     */
    public void setResponse(ReturnableObject response) {
        this.response = response;
    }

    /**
     * getter for the response
     *
     * @return the response
     */
    public ReturnableObject getResponse() {
        return this.response;
    }

    /**
     * setter for the type
     * @param type
     */
    public void setType(TypeServerToClientMsg type) {
        this.type = type;
    }

    public TypeServerToClientMsg getType() {
        return this.type;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getIdGame() {
        return this.idGame;
    }

    public void functionToCall(BaseClient client) throws RemoteException {
    }
}
