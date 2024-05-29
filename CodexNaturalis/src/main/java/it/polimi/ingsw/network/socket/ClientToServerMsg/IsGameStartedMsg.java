package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.IOException;

public class IsGameStartedMsg extends ClientToServerMsg{
    private final int idGame;
    public IsGameStartedMsg(int idGame) {
        this.idGame = idGame;
    }
    @Override
    public ReturnableObject<String> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException, IOException {
        ReturnableObject<String> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getCurrentGameState(idGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.IS_GAME_STARTED;
    }

    @Override
    public int getIdGame() {
        return idGame;
    }
}
