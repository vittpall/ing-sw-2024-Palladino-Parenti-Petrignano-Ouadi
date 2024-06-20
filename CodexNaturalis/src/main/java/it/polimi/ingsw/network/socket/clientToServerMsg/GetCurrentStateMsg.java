package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

public class GetCurrentStateMsg extends ClientToServerMsg {

    private final int idGame;
    private final int idClientIntoGame;

    public GetCurrentStateMsg(int idGae, int idClientIntoGame) {
        this.idGame = idGae;
        this.idClientIntoGame = idClientIntoGame;
    }

    /**
     * @param controller
     * @return
     * @throws InterruptedException
     * @throws PlaceNotAvailableException
     * @throws RequirementsNotMetException
     */
    @Override
    public ReturnableObject<String> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
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
    public int getIdGame() {
        return idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
