package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to get the secret objective card of a player
 */
public class GetObjectiveCardMsg extends ClientToServerMsg {
    private final int idGame;
    private final int idClientIntoGame;

    /**
     * Constructor
     *
     * @param idGame           Integer representing the id of the game
     * @param idClientIntoGame Integer representing the id of the client into the game
     */
    public GetObjectiveCardMsg(int idGame, int idClientIntoGame) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }

    @Override
    public ReturnableObject<ObjectiveCard> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<ObjectiveCard> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getObjectiveCard(idGame, idClientIntoGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_OBJECTIVE_CARD;
    }


    @Override
    public int getIdGame() {
        return idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
