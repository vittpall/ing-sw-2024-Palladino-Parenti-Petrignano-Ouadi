package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.GameIdMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class WaitForYourTurnMsg extends ClientToServerMsg{

    private int idGame;
    private int idClientIntoGame;

    public WaitForYourTurnMsg(int idGame, int idClientIntoGame) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject response = new ReturnableObject();
        controller.waitForYourTurn(idGame, idClientIntoGame);
        response.setIntResponse(-1);
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new GameIdMsg();
    }
}
