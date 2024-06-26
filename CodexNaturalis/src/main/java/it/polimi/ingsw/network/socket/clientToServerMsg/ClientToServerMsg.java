package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;
import java.io.Serializable;

/**
 * Message sent from the client to the server used in socket connection.
 * It provides the structure for messages that can be processed by the server.
 */
public abstract class ClientToServerMsg implements Serializable {

    protected String username;

    /**
     * Return the server-side function associated with this message.
     *
     * @param controller     the lobbyController used to execute the function
     * @param playerListener the GameLister representing the client
     * @return ReturnableObject containing the result of the server function
     * @throws InterruptedException       when the thread running is interrupted
     * @throws PlaceNotAvailableException if the requested place is not available
     * @throws IOException                when an I/O operation fails
     */
    public abstract ReturnableObject functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException, IOException;

    /**
     * Return the type of the message
     *
     * @return the enum value representing the type of the message
     */
    public abstract TypeServerToClientMsg getType();

    /**
     * Getter of the idGame of the client that sent the request
     *
     * @return Integer representing the idGame of the client that sent the request
     */
    public abstract int getIdGame();

    /**
     * Getter of the username of the client that sent the request
     *
     * @return String representing the username of the user that sent the request
     */
    public abstract String getUsername();
}
