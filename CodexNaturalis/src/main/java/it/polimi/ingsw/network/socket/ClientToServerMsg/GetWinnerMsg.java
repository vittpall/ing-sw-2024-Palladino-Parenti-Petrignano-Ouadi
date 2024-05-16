package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetWinnerMsg extends ClientToServerMsg{

    private final int idGame;
    private final int idClientIntoGame;

    public GetWinnerMsg(int idGame, int idClientIntoGame) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }
    @Override
    public ReturnableObject<String> functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject<String> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getWinner(idGame, idClientIntoGame));
        return response;
    }


    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_WINNER;
    }
}
