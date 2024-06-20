package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

public class GetLastFromUsableCards extends ClientToServerMsg {

    private final int idGame;
    private final int deck;

    public GetLastFromUsableCards(int idGame, int deck) {
        this.idGame = idGame;
        this.deck = deck;
    }

    @Override
    public ReturnableObject<GameCard> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException, IOException {
        ReturnableObject<GameCard> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getLastCardOfUsableCards(idGame, deck));
        return response;
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
