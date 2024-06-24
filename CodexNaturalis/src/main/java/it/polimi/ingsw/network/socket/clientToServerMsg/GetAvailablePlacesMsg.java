package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.awt.*;
import java.util.HashSet;

public class GetAvailablePlacesMsg extends ClientToServerMsg {

    private final int idGame;
    private final int idClientIntoGame;

    public GetAvailablePlacesMsg(int idGame, int idClientIntoGame) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }

    @Override
    public ReturnableObject<HashSet<Point>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<HashSet<Point>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getAvailablePlaces(idGame, idClientIntoGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.AVAILABLE_PLACES;
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
