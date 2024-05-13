package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.PlayerObjectiveCardsMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetPlayerObjectiveCardsMsg extends ClientToServerMsg{

    private int idGame;
    private int idPlayer;

    public GetPlayerObjectiveCardsMsg(int idGame, int idClientIntoGame){
        this.idGame = idGame;
        this.idPlayer = idClientIntoGame;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
        ReturnableObject response = new ReturnableObject();
        response.setArrayListResponse(controller.getObjectiveCards(idGame, idPlayer));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new PlayerObjectiveCardsMsg();
    }
}
