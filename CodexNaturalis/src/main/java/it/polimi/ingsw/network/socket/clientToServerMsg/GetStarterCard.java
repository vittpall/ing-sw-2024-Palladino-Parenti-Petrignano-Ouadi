package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to get the starter card of a specified player
 */
public class GetStarterCard extends ClientToServerMsg {

    private final int idGame;
    private final int idClientIntoGame;

    /**
     * Constructor
     *
     * @param idGame           Integer representing the id of the game
     * @param idClientIntoGame Integer representing the id of the client into the gme
     */
    public GetStarterCard(int idGame, int idClientIntoGame) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }

    @Override
    public ReturnableObject<StarterCard> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<StarterCard> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getStarterCard(idGame, idClientIntoGame));
        return response;

    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_STARTED_CARD;
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
