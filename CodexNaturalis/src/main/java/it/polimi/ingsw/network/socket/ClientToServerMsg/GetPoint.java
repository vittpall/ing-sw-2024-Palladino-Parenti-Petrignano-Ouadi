package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.GameIdMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetPoint extends ClientToServerMsg{

    private int idGame;
    private int idClientIntoGame;

    public GetPoint(String username, int idGame, int idClientIntoGame) {
        this.username = username;
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject response = new ReturnableObject();
        response.setIntResponse(controller.getPoints(idGame, idClientIntoGame));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new GameIdMsg();
    }
}
