package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class GetNPlayer extends ClientToServerMsg{

    private final int idGame;

    public GetNPlayer(int idGame) {
        this.idGame = idGame;
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
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getnPlayer(idGame));
        return response;
    }

    /**
     * @return
     */
    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.ALL_PLAYERS;
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
        return 0;
    }
}
