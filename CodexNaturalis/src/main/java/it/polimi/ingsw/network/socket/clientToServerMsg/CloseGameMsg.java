package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

import java.io.IOException;

public class CloseGameMsg extends ClientToServerMsg {
    private final int idGame;

    public CloseGameMsg(int idGame) {
        this.idGame = idGame;
    }

    /**
     * @param controller the controller on which the method has to be called
     * @return the response of the method
     * @throws InterruptedException        if the thread is interrupted
     * @throws PlaceNotAvailableException  if the place is not available
     * @throws RequirementsNotMetException if the requirements are not met
     */
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


    /**
     * @return the id of the game
     */
    @Override
    public int getIdGame() {
        return this.idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }


}
