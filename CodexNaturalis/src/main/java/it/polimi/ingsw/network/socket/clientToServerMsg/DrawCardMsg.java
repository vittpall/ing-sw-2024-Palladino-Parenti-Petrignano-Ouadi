package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

public class DrawCardMsg extends ClientToServerMsg {

    private final int idGame;
    private final int input;
    private final int inVisible;

    public DrawCardMsg(int idGame, int input, int inVisible, String broadCastMessage) {
        this.idGame = idGame;
        this.input = input;
        this.inVisible = inVisible;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        controller.drawCard(idGame, input, inVisible);
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.DRAWN_CARD;
    }


    @Override
    public int getIdGame() {
        return this.idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }

}
