package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to get the objective cards shared by the players of a game
 */
public class GetSharedObjectiveCardsMsg extends ClientToServerMsg {
    private final int idGame;

    /**
     * Constructor
     *
     * @param idGame Integer representing the id of the game
     */
    public GetSharedObjectiveCardsMsg(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<ObjectiveCard[]> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<ObjectiveCard[]> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getSharedObjectiveCards(idGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_SHARED_OBJECTIVE_CARDS;
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
