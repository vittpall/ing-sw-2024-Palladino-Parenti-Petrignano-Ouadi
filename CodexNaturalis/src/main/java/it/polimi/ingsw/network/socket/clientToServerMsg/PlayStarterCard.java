package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.ErrorCodes;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to make the user play the starter card
 */
public class PlayStarterCard extends ClientToServerMsg {
    private final int idGame;
    private final int idClientIntoGame;
    private final boolean playedFacedDown;

    /**
     * Constructor
     *
     * @param idGame           Integer representing the id of the game
     * @param idClientIntoGame Integer representing the id of the client into the game
     * @param playedFacedDown  true if the card has to be played faced down, false otherwise
     */
    public PlayStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.playedFacedDown = playedFacedDown;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        try {
            controller.playStarterCard(idGame, idClientIntoGame, playedFacedDown, playerListener);
        } catch (RequirementsNotMetException e) {
            response.setErrorCode(ErrorCodes.REQUIREMENTS_NOT_MET);
            response.setErrorMessage(e.getMessage());
        }
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.PLAY_STARTED_CARD;
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
