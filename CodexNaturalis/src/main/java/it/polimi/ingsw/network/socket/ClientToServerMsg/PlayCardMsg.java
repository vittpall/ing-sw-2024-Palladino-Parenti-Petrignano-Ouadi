package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.awt.*;

public class PlayCardMsg extends ClientToServerMsg {
    private final int idGame;
    private final int idClientIntoGame;
    private final int chosenCard;
    private final boolean faceDown;
    private final Point chosenPosition;

    public PlayCardMsg(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition) {
        this.idClientIntoGame = idClientIntoGame;
        this.idGame = idGame;
        this.chosenCard = chosenCard;
        this.faceDown = faceDown;
        this.chosenPosition = chosenPosition;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        controller.playCard(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.PLAY_CARD;
    }


    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
