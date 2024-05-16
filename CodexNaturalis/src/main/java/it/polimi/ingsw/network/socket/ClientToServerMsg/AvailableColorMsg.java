package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.util.ArrayList;

public class AvailableColorMsg extends ClientToServerMsg{

    private int idGame;
    public AvailableColorMsg(String username, int idGame) {
        this.username = username;
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<ArrayList<TokenColor>> functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject<ArrayList<TokenColor>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getAvailableColors(idGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.AVAILABLE_COLORS;
    }
}
