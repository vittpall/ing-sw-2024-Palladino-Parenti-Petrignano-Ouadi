package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

/**
 * Client to server message sent in socket connection to select the color of the token of the player
 */
public class SelectTokenColorMsg extends ClientToServerMsg {
    private final int idGame;
    private final int idClientIntoGame;
    private final TokenColor tokenColor;

    /**
     * Constructor
     *
     * @param idGame           Integer representing the id of the game
     * @param idClientIntoGame Integer representing the id of the client into the game
     * @param tokenColor       color chosen for the player's token
     */
    public SelectTokenColorMsg(int idGame, int idClientIntoGame, TokenColor tokenColor) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.tokenColor = tokenColor;
    }

    @Override
    public ReturnableObject<TokenColor> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, IOException {
        ReturnableObject<TokenColor> response = new ReturnableObject<>();
        TokenColor token = controller.setTokenColor(idGame, idClientIntoGame, tokenColor);
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
