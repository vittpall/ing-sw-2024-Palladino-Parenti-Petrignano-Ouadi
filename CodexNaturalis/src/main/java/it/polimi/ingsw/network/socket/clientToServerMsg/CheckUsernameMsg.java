package it.polimi.ingsw.network.socket.clientToServerMsg;


import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.client.ReturnableObject;

public class CheckUsernameMsg extends ClientToServerMsg {

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
