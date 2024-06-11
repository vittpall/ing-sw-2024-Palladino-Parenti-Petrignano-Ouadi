package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class PlayStarterCard extends ClientToServerMsg {

    private final int idGame;
    private final int idClientIntoGame;
    private final boolean playedFacedDown;

    public PlayStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.playedFacedDown = playedFacedDown;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        controller.playStarterCard(idGame, idClientIntoGame, playedFacedDown, playerListener);
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.PLAY_STARTED_CARD;
    }


    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
