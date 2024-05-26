package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class DrawCardMsg extends ClientToServerMsg {

    private final int idGame;
    private final int input;
    private final int inVisible;

    public DrawCardMsg(int idGame, int input, int inVisible, String broadCastMessage) {
        this.idGame = idGame;
        this.input = input;
        this.inVisible = inVisible;
        this.broadCastMessage = broadCastMessage;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        controller.drawCard(idGame, input, inVisible);
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.DRAWN_CARD;
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
        return this.idGame;
    }

}
