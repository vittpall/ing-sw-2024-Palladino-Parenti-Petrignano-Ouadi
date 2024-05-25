package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class CheckStateMsg extends ClientToServerMsg{


    private final int idGame;
    private final int idClientIntoGame;
    private final RequestedActions requestedActions;

    public CheckStateMsg(int idGame, int idClientIntoGame, RequestedActions requestedActions) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
        this.requestedActions = requestedActions;
    }
    /**
     * @param controller
     * @return
     * @throws InterruptedException
     * @throws CardNotFoundException
     * @throws PlaceNotAvailableException
     * @throws RequirementsNotMetException
     */
    @Override
    public ReturnableObject<Boolean> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<Boolean> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.checkState(idGame, idClientIntoGame, requestedActions));
        return response;
    }

    /**
     * @return
     */
    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.CHECK_STATE;
    }

    /**
     * @return
     */
    @Override
    public boolean getDoItNeedToBeBroadcasted() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return this.idGame;
    }
}
