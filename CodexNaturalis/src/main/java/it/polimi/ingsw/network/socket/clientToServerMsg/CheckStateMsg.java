package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to check the current state of the specified player into the specified game
 */
public class CheckStateMsg extends ClientToServerMsg {
    private final int idGame;
    private final int idClientIntoGame;
    private final RequestedActions requestedActions;

    /**
     * Constructor
     *
     * @param idGame           Integer representing the id of the game
     * @param idClientIntoGame Integer representing the id of the client into the game
     * @param requestedActions RequestedAction of the user
     */
    public CheckStateMsg(int idGame, int idClientIntoGame, RequestedActions requestedActions) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.requestedActions = requestedActions;
    }


    @Override
    public ReturnableObject<Boolean> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<Boolean> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.checkState(idGame, idClientIntoGame, requestedActions));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.CHECK_STATE;
    }


    @Override
    public int getIdGame() {
        return this.idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
