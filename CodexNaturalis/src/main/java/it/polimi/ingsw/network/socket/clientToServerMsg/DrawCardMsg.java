package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to draw a card in the specified game
 */
public class DrawCardMsg extends ClientToServerMsg {

    private final int idGame;
    private final int input;
    private final int inVisible;

    /**
     * Constructor
     *
     * @param idGame    Integer representing the id of the game
     * @param input     Integer representing the desk from which to draw
     * @param inVisible Integer representing the visible or random card to draw
     */
    public DrawCardMsg(int idGame, int input, int inVisible) {
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
