package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class IsLastRoundStartedMsg extends ClientToServerMsg{
    private int idGame;

    public IsLastRoundStartedMsg(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject response = new ReturnableObject();
        response.setBooleanResponse(controller.getIsLastRoundStarted(idGame));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
