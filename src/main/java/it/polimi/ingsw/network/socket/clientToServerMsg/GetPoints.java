package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to get the points of a specified player
 */
public class GetPoints extends ClientToServerMsg {

    private final int idGame;
    private final int idClientIntoGame;

    /**
     * Constructor
     *
     * @param username         String representing the username of the user
     * @param idGame           Integer representing the id of the game
     * @param idClientIntoGame Integer representing the id of the client into the game
     */
    public GetPoints(String username, int idGame, int idClientIntoGame) {
        this.username = username;
        this.idGame = idGame;
        this.idClientIntoGame = idClientIntoGame;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        response.setResponseReturnable(controller.getPoints(idGame, idClientIntoGame));
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.GET_POINTS;
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
