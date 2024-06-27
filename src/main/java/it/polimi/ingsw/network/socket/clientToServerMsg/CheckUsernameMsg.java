package it.polimi.ingsw.network.socket.clientToServerMsg;


import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

/**
 * Client to server message sent in socket connection to check if the username requested by the user is valid. If so, it sets it
 */
public class CheckUsernameMsg extends ClientToServerMsg {
    /**
     * Constructor
     *
     * @param username String representing the username requested
     */
    public CheckUsernameMsg(String username) {
        this.username = username;
    }

    @Override
    public ReturnableObject<Boolean> functionToCall(LobbyController controller, GameListener playerListener) {
        ReturnableObject<Boolean> toReturn = new ReturnableObject<>();
        toReturn.setResponseReturnable(controller.checkUsername(username));
        return toReturn;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.USER_ALREADY_TAKEN;
    }


    @Override
    public int getIdGame() {
        return -1;
    }

    public String getUsername() {
        return username;
    }
}
