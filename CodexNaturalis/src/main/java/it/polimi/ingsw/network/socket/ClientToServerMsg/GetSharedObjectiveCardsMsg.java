package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetSharedObjectiveCardsMsg extends ClientToServerMsg{
    private int idGame;

    public GetSharedObjectiveCardsMsg(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject response = new ReturnableObject();
        response.setObjectiveCardsResponse(controller.getSharedObjectiveCards(idGame));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
