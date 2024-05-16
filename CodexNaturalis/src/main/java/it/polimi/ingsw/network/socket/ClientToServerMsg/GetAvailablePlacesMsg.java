package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.awt.*;
import java.util.HashSet;

public class GetAvailablePlacesMsg extends ClientToServerMsg{

    private int idGame;
    private int idClientIntoGame;

    public GetAvailablePlacesMsg(int idGame, int idClientIntoGame) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }

    @Override
    public ReturnableObject<HashSet<Point>> functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
        ReturnableObject<HashSet<Point>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getAvailablePlaces(idGame, idClientIntoGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.AVAILABLE_PLACES;
    }
}
