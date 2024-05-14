package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.awt.*;
import java.util.HashMap;

public class GetPlayerDesk extends ClientToServerMsg{
    private int idGame;
    private int idPlayer;

    public GetPlayerDesk(int idGame, int idPlayer) {
        this.idGame = idGame;
        this.idPlayer = idPlayer;
    }

    @Override
    public ReturnableObject<HashMap<Point, GameCard>> functionToCall(LobbyController controller) throws InterruptedException {
        ReturnableObject<HashMap<Point, GameCard>> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getPlayerDesk(idGame, idPlayer));
        return response;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new ServerToClientMsg();
    }
}
