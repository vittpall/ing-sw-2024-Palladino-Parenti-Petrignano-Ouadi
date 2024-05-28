package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class GetWinnerMsg extends ClientToServerMsg {

    private final int idGame;

    public GetWinnerMsg(int idGame) {
        this.idGame = idGame;

    }

    @Override
    public ReturnableObject<String> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<String> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getWinner(idGame));
        this.broadCastMessage = "The game is over!!!" + "The winner is:" + response.getResponseReturnable();
        return response;
    }


    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_WINNER;
    }


    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return idGame;
    }
}
