package it.polimi.ingsw.network.socket.ClientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class CreateGameMsg extends ClientToServerMsg{

    private final int numberOfPlayers;
    public CreateGameMsg(String username, int numberOfPlayers) {
        this.username = username;
        this.numberOfPlayers = numberOfPlayers;
        this.broadCastMessage = "User " + username + " has created a game with " + numberOfPlayers + " players";
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, IOException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.createGame(this.username, this.numberOfPlayers, playerListener));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.CREATED_GAME;
    }

    /**
     * @return
     */
    @Override
    public boolean getDoItNeedToBeBroadcasted() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return this.broadCastMessage;
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return -1;
    }
}
