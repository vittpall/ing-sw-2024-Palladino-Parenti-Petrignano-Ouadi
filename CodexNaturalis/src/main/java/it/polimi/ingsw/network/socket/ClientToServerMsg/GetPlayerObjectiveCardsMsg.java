package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.util.ArrayList;

public class GetPlayerObjectiveCardsMsg extends ClientToServerMsg {

    private final int idGame;
    private final int idPlayer;

    public GetPlayerObjectiveCardsMsg(int idGame, int idClientIntoGame) {
        this.idGame = idGame;
        this.idPlayer = idClientIntoGame;
    }

    @Override
    public ReturnableObject<ArrayList<ObjectiveCard>> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException {
        ReturnableObject<ArrayList<ObjectiveCard>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getObjectiveCards(idGame, idPlayer));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_PLAYER_OBJECTIVE_CARDS;
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
