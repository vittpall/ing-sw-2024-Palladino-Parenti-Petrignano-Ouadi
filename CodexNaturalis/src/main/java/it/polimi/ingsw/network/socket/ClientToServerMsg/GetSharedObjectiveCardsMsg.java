package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetSharedObjectiveCardsMsg extends ClientToServerMsg{
    private int idGame;

    public GetSharedObjectiveCardsMsg(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<ObjectiveCard[]> functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject<ObjectiveCard[]> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getSharedObjectiveCards(idGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_SHARED_OBJECTIVE_CARDS;
    }

    /**
     * @return
     */
    @Override
    public boolean getDoItNeedToBeBroadcasted() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return idGame;
    }
}
