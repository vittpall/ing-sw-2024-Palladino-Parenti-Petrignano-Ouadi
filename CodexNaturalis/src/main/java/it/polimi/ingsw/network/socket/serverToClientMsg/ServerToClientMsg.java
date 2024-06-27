package it.polimi.ingsw.network.socket.serverToClientMsg;

import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * The class is used to send a message from the server to the client
 */
public class ServerToClientMsg implements Serializable {

    protected ReturnableObject response;
    protected TypeServerToClientMsg type;
    protected int idGame;

    /**
     * Constructor
     *
     * @param type is the type of the message
     */
    public ServerToClientMsg(TypeServerToClientMsg type) {
        this.type = type;
        this.response = new ReturnableObject<>();
    }

    /**
     * setter for the response
     *
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
     *
     * @param type
     */
    public void setType(TypeServerToClientMsg type) {
        this.type = type;
    }

    /**
     * getter for the type
     *
     * @return the type of the message
     */
    public TypeServerToClientMsg getType() {
        return this.type;
    }

    /**
     * Set the id game of the game the message is related to
     *
     * @param idGame is the id of the game
     */
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    /**
     * Getter of the id game
     *
     * @return the id of the game
     */
    public int getIdGame() {
        return this.idGame;
    }

    /**
     * This method is used to call a function of the client
     *
     * @param client is the client that will receive the message
     * @throws RemoteException if a communication error occurs
     */
    public void functionToCall(BaseClient client) throws RemoteException {
    }
}
