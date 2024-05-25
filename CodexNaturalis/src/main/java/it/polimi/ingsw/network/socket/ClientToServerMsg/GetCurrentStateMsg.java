package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class GetCurrentStateMsg extends ClientToServerMsg{

    private int idGame;
    private int idClientIntoGame;

    public GetCurrentStateMsg(int idGae, int idClientIntoGame) {
        this.idGame = idGae;
        this.idClientIntoGame = idClientIntoGame;
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
    public ReturnableObject<String> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<String> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getCurrentState(idGame, idClientIntoGame));
        return response;
    }

    /**
     * @return
     */
    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_CURRENT_STATE;
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
        return idGame;
    }
}
