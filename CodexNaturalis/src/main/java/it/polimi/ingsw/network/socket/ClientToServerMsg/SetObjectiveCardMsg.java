package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class SetObjectiveCardMsg extends ClientToServerMsg{
    private int idGame;
    private int idPlayer;
    private int idObjectiveCard;

    public SetObjectiveCardMsg(int idGame, int idPlayer, int idObjectiveCard){
        this.idGame = idGame;
        this.idPlayer = idPlayer;
        this.idObjectiveCard = idObjectiveCard;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException, CardNotFoundException {
        ReturnableObject response = new ReturnableObject();
        controller.setObjectiveCard(idGame, idPlayer, idObjectiveCard);
        response.setIntResponse(-1);
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
