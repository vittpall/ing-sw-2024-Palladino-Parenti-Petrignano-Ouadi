package it.polimi.ingsw.network.socket.clientToServerMsg;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to set the secret objective card of a player
 */
public class SetObjectiveCardMsg extends ClientToServerMsg {
    private final int idGame;
    private final int idPlayer;
    private final int idObjectiveCard;

    /**
     * Constructor
     *
     * @param idGame          Integer representing the id of the game
     * @param idPlayer        Integer representing the id of the player into the game
     * @param idObjectiveCard Integer objective card chosen
     */
    public SetObjectiveCardMsg(int idGame, int idPlayer, int idObjectiveCard) {
        this.idGame = idGame;
        this.idPlayer = idPlayer;
        this.idObjectiveCard = idObjectiveCard;
    }

    @Override
    public ReturnableObject<Integer> functionToCall(LobbyController controller, GameListener playerListener) throws InterruptedException {
        ReturnableObject<Integer> response = new ReturnableObject<>();
        controller.setObjectiveCard(idGame, idPlayer, idObjectiveCard);
        response.setResponseReturnable(-1);
        return response;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.WAIT_FOR_YOUR_TURN;
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
