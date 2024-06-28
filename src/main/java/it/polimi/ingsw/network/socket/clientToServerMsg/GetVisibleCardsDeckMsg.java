package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.ErrorCodes;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.util.ArrayList;

/**
 * Client to server message sent in socket connection to get the visible cards of a deck in a specified game
 */
public class GetVisibleCardsDeckMsg extends ClientToServerMsg {

    private final int deck;
    private final int idGame;

    /**
     * Constructor
     *
     * @param idGame Integer representing the id of the game
     * @param deck   Integer representing the deck from which to get the cards
     */
    public GetVisibleCardsDeckMsg(int idGame, int deck) {
        this.deck = deck;
        this.idGame = idGame;
    }


    @Override
    public ReturnableObject<ArrayList<GameCard>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<ArrayList<GameCard>> response = new ReturnableObject<>();
        try {
            response.setResponseReturnable(controller.getVisibleCardsDeck(idGame, deck));
            return response;
        } catch (NullPointerException e) {
            response.setErrorCode(ErrorCodes.GAME_NOT_FOUND);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }


    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_VISIBLE_CARDS_DECK;
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
