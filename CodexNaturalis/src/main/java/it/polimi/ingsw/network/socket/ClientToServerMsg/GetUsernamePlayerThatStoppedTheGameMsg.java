package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetUsernamePlayerThatStoppedTheGameMsg extends ClientToServerMsg{

    private int idGame;

    public GetUsernamePlayerThatStoppedTheGameMsg(int idGame) {
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
    public ReturnableObject<String> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<String> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getUsernamePlayerThatStoppedTheGame(idGame));
        return response;
    }


    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_USERNAME_PLAYER_THAT_STOPPED_THE_GAME;
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
