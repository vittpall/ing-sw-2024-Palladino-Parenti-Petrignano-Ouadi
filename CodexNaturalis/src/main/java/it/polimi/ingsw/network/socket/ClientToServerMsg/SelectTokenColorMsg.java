package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.GameIdMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.TokenColorMsg;

public class SelectTokenColorMsg extends ClientToServerMsg{

    private int idGame;
    private int idClientIntoGame;
    public TokenColor tokenColor;

    public SelectTokenColorMsg(int idGame, int idClientIntoGame, TokenColor tokenColor) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.tokenColor = tokenColor;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject response = new ReturnableObject();
        controller.setTokenColor(idGame, idClientIntoGame, tokenColor);
        response.setIntResponse(-1);
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new TokenColorMsg();
    }
}
