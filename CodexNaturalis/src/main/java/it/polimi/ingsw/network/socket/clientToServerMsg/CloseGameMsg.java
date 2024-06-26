package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

/**
 * Client to server message sent in socket connection to close the game after a client close the game
 */
public class CloseGameMsg extends ClientToServerMsg {
    private final int idGame;

    /**
     * Constructor
     *
     * @param idGame Integer representing the id of the game
     */
    public CloseGameMsg(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException, IOException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(-1);
        controller.closeGame(idGame, null);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.CLOSED_GAME;
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
