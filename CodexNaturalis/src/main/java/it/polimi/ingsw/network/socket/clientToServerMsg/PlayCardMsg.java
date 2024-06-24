package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.ErrorCodes;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

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
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        try {
            response.setResponseReturnable(controller.playCard(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition));
        } catch (RequirementsNotMetException e) {
            response.setErrorCode(ErrorCodes.REQUIREMENTS_NOT_MET);
            response.setErrorMessage(e.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.PLAY_CARD;
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
