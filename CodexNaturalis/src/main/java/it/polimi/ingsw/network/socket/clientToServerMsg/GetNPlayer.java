package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to get the number of players of a specified game
 */
public class GetNPlayer extends ClientToServerMsg {

    private final int idGame;

    /**
     * Constructor
     *
     * @param idGame Integer representing the id of the game
     */
    public GetNPlayer(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException, PlaceNotAvailableException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getnPlayer(idGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.ALL_PLAYERS;
    }


    @Override
    public int getIdGame() {
        return 0;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
