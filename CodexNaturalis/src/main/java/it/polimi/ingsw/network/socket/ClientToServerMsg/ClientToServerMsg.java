package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.IOException;
import java.io.Serializable;

public abstract class ClientToServerMsg implements Serializable {

    protected String username;

    public abstract ReturnableObject functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException, IOException;

    public abstract TypeServerToClientMsg getType();

    public abstract int getIdGame();
}
