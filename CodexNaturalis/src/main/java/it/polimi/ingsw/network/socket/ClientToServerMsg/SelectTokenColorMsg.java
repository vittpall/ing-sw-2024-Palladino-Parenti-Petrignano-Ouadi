package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class SelectTokenColorMsg extends ClientToServerMsg{

    private final int idGame;
    private final int idClientIntoGame;
    public TokenColor tokenColor;

    public SelectTokenColorMsg(int idGame, int idClientIntoGame, TokenColor tokenColor) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.tokenColor = tokenColor;
        this.broadCastMessage = tokenColor + " has been selected!";
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        controller.setTokenColor(idGame, idClientIntoGame, tokenColor);
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.SELECTED_TOKEN_COLOR;
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
