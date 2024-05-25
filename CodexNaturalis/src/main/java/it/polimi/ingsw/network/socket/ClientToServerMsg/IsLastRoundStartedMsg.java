package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class IsLastRoundStartedMsg extends ClientToServerMsg{
    private int idGame;

    public IsLastRoundStartedMsg(int idGame) {
        this.idGame = idGame;
        this.broadCastMessage = "Last round started";
    }

    @Override
    public ReturnableObject<Boolean> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<Boolean> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getIsLastRoundStarted(idGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.IS_LAST_ROUND_STARTED;
    }

    /**
     * @return
     */
    @Override
    public boolean getDoItNeedToBeBroadcasted() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return this.broadCastMessage;
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return idGame;
    }
}
