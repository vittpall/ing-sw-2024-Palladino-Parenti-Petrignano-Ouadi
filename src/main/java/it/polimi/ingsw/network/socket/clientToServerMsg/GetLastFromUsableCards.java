package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.ErrorCodes;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

/**
 * Client to server message sent in socket connection to get the last of the deck's cards that are not drawn yet
 */
public class GetLastFromUsableCards extends ClientToServerMsg {

    private final int idGame;
    private final int deck;

    /**
     * Constructor
     *
     * @param idGame Integer representing the id of the game
     * @param deck   Integer representing the deck from which to get the last card
     */
    public GetLastFromUsableCards(int idGame, int deck) {
        this.idGame = idGame;
        this.deck = deck;
    }

    @Override
    public ReturnableObject<GameCard> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException, IOException {
        ReturnableObject<GameCard> response = new ReturnableObject<>();
        try {
            response.setResponseReturnable(controller.getLastCardOfUsableCards(idGame, deck));
            return response;
        } catch (NullPointerException e) {
            response.setErrorCode(ErrorCodes.GAME_NOT_FOUND);
            response.setErrorMessage(e.getMessage());
            return response;
        }
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_LAST_FROM_USABLE_CARDS;
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
