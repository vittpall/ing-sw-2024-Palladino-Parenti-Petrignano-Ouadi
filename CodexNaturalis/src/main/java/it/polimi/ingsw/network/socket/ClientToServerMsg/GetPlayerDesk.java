package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

public class GetPlayerDesk extends ClientToServerMsg{
    private int idGame;
    private int idPlayer;

    public GetPlayerDesk(int idGame, int idPlayer) {
        this.idGame = idGame;
        this.idPlayer = idPlayer;
    }

    @Override
    public ReturnableObject functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject response = new ReturnableObject();
        response.setGameCardHashMapResponse(controller.getPlayerDesk(idGame, idPlayer));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
