package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.TokenColorMsg;

public class AvailableColorMsg extends ClientToServerMsg{

    private int idGame;
    public AvailableColorMsg(String username, int idGame) {
        this.username = username;
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject response = new ReturnableObject();
        response.setArrayListResponse(controller.getAvailableColors(idGame));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new TokenColorMsg();
    }
}
