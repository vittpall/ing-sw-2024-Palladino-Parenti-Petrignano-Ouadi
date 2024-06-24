package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

public class IsLastRoundStartedMsg extends ClientToServerMsg {
    private final int idGame;

    public IsLastRoundStartedMsg(int idGame) {
        this.idGame = idGame;
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


    @Override
    public int getIdGame() {
        return idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
