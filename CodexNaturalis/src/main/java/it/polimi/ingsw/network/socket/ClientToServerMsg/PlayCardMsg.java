package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.GameIdMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.awt.*;

public class PlayCardMsg extends ClientToServerMsg{
    private int idGame;
    private int idClientIntoGame;
    private int chosenCard;
    private boolean faceDown;
    private Point chosenPosition;

    public PlayCardMsg(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition) {
        this.idClientIntoGame = idClientIntoGame;
        this.idGame = idGame;
        this.chosenCard = chosenCard;
        this.faceDown = faceDown;
        this.chosenPosition = chosenPosition;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller) throws InterruptedException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        controller.playCard(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new GameIdMsg();
    }
}
