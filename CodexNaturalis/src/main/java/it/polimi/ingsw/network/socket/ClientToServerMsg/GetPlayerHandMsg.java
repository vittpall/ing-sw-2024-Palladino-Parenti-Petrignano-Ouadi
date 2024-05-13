package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetPlayerHandMsg extends ClientToServerMsg{
    private int idGame;
    private int idPlayer;

    public GetPlayerHandMsg(int idGame, int idPlayer) {
        this.idGame = idGame;
        this.idPlayer = idPlayer;
    }


    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ReturnableObject response = new ReturnableObject();
        response.setArrayListResponse(controller.getPlayerHand(idGame, idPlayer));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
