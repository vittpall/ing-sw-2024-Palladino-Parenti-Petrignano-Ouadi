package it.polimi.ingsw.network.socket.ServerToClientMsg;

import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.Serializable;
import java.rmi.RemoteException;


public class ServerToClientMsg implements Serializable {

    protected ReturnableObject response;
    protected TypeServerToClientMsg type;
    protected int idGame;

    public ServerToClientMsg(TypeServerToClientMsg type) {
        this.type = type;
        this.response = new ReturnableObject<>();
    }


    public ServerToClientMsg(TypeServerToClientMsg type, int idGame) {
        this.type = type;
        this.response = new ReturnableObject<>();
        this.idGame = idGame;
    }

    public void setResponse(ReturnableObject response) {
        this.response = response;
    }

    public ReturnableObject getResponse() {
        return this.response;
    }

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

    public void functionToCall(VirtualView client) throws RemoteException {
    }
}
