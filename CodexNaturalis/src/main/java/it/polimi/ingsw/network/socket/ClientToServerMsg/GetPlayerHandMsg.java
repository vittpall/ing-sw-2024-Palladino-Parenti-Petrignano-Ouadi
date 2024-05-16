package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.util.ArrayList;

public class GetPlayerHandMsg extends ClientToServerMsg{
    private int idGame;
    private int idPlayer;

    public GetPlayerHandMsg(int idGame, int idPlayer) {
        this.idGame = idGame;
        this.idPlayer = idPlayer;
    }


    @Override
    public ReturnableObject<ArrayList<GameCard>> functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<ArrayList<GameCard>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getPlayerHand(idGame, idPlayer));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_PLAYER_HAND;
    }
}
