package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;
import java.io.Serializable;

public abstract class ClientToServerMsg implements Serializable {

    protected String username;

    public abstract ReturnableObject functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException, IOException;

    public abstract TypeServerToClientMsg getType();

    public abstract int getIdGame();

    public abstract String getUsername();
}
