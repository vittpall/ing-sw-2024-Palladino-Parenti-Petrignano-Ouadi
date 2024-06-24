package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

public class SelectTokenColorMsg extends ClientToServerMsg {

    private final int idGame;
    private final int idClientIntoGame;
    public TokenColor tokenColor;

    public SelectTokenColorMsg(int idGame, int idClientIntoGame, TokenColor tokenColor) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.tokenColor = tokenColor;
    }

    @Override
    public ReturnableObject<TokenColor> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, IOException {
        ReturnableObject<TokenColor> response = new ReturnableObject<>();
        TokenColor token = controller.setTokenColor(idGame, idClientIntoGame, tokenColor, playerListener);
        response.setResponseReturnable(token);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.SELECTED_TOKEN_COLOR;
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
