package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.AvailablePlacesMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetAvailablePlacesMsg extends ClientToServerMsg{

    private int idGame;
    private int idClientIntoGame;

    public GetAvailablePlacesMsg(int idGame, int idClientIntoGame) {
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
        ReturnableObject response = new ReturnableObject();
        response.setHashSetResponse(controller.getAvailablePlaces(idGame, idClientIntoGame));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new AvailablePlacesMsg();
    }
}
