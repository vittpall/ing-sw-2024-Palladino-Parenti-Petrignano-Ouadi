package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class CloseGameMsg extends ClientToServerMsg{
    int idGame;

    public CloseGameMsg(int idGame) {
        this.idGame = idGame;
        broadCastMessage = "Game " + idGame + " has been closed";
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
        response.setResponseReturnable(-1);
        controller.closeGame(idGame);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.CLOSED_GAME;
    }

    /**
     * @return
     */
    @Override
    public boolean getDoItNeedToBeBroadcasted() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return this.broadCastMessage;
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return this.idGame;
    }


}
