package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.util.ArrayList;

public class GetVisibleCardsDeckMsg extends ClientToServerMsg {

    int deck;
    int idGame;

    public GetVisibleCardsDeckMsg(int idGame, int deck) {
        this.deck = deck;
        this.idGame = idGame;
    }

    /**
     * @param controller
     * @return ReturnableObject
     * @throws InterruptedException
     * @throws CardNotFoundException
     * @throws PlaceNotAvailableException
     * @throws RequirementsNotMetException
     */
    @Override
    public ReturnableObject<ArrayList<GameCard>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<ArrayList<GameCard>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getVisibleCardsDeck(idGame, deck));
        return response;
    }


    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_VISIBLE_CARDS_DECK;
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return idGame;
    }
}
