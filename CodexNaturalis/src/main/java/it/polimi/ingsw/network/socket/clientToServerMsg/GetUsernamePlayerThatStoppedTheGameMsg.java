package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

public class GetUsernamePlayerThatStoppedTheGameMsg extends ClientToServerMsg {

    private final int idGame;

    public GetUsernamePlayerThatStoppedTheGameMsg(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<String> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<String> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getUsernamePlayerThatStoppedTheGame(idGame));
        return response;
    }


    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_USERNAME_PLAYER_THAT_STOPPED_THE_GAME;
    }


    @Override
    public int getIdGame() {
        return idGame;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
