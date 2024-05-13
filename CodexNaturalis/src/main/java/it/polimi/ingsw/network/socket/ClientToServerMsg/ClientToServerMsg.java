package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.Serializable;

public abstract class ClientToServerMsg implements Serializable {

    protected ReturnableObject response;
    protected String username;
    protected ServerToClientMsg typeofResponse;

    public ClientToServerMsg() {
        this.response = new ReturnableObject();
    }

    public abstract ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException;
    public abstract ServerToClientMsg getTypeofResponse();
}
