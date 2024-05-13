package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.GameIdMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.awt.*;

public class PlayLastTurnMsg extends ClientToServerMsg{
    private int idGame;
    private int idClientIntoGame;
    private int chosenCard;
    private boolean faceDown;
    private Point chosenPosition;

    public PlayLastTurnMsg(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition) {
        this.idClientIntoGame = idClientIntoGame;
        this.idGame = idGame;
        this.chosenCard = chosenCard;
        this.faceDown = faceDown;
        this.chosenPosition = chosenPosition;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        ReturnableObject response = new ReturnableObject();
        controller.playLastTurn(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
        response.setIntResponse(-1);
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new GameIdMsg();
    }
}
