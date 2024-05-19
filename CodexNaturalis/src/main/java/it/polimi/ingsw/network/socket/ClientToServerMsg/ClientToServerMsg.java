package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.Serializable;

public abstract class ClientToServerMsg implements Serializable {

    protected String username;
    protected ServerToClientMsg typeofResponse;
    protected boolean doItNeedToBeBroadcasted;
    protected String broadCastMessage;

    public abstract ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException;
    public abstract TypeServerToClientMsg getType();
    public abstract boolean getDoItNeedToBeBroadcasted();
    public abstract String getBroadCastMessage();
    public abstract int getIdGame();
}
