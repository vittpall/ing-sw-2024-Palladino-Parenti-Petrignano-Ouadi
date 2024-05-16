package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class DrawCardMsg extends ClientToServerMsg{

    private int idGame;
    private int idClientIntoGame;
    private int input;
    private int inVisible;

    public DrawCardMsg(int idGame, int idClientIntoGame, int input, int inVisible) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.input = input;
        this.inVisible = inVisible;
    }
    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        controller.drawCard(idGame, idClientIntoGame, input, inVisible);
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.DRAWN_CARD;
    }

}
