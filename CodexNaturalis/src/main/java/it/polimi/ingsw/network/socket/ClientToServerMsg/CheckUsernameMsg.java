package it.polimi.ingsw.network.socket.ClientToServerMsg;


import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class CheckUsernameMsg extends ClientToServerMsg {

    public CheckUsernameMsg(String username) {
        this.username = username;
    }

    public ReturnableObject<Boolean> functionToCall(LobbyController controller, GameListener playerListener) {
        ReturnableObject<Boolean> toReturn = new ReturnableObject<>();
        toReturn.setResponseReturnable(controller.checkUsername(username, playerListener));
        return toReturn;
    }

    @Override
    public TypeServerToClientMsg getType() {
        return TypeServerToClientMsg.USER_ALREADY_TAKEN;
    }

    /**
     * @return
     */
    @Override
    public boolean getDoItNeedToBeBroadcasted() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String getBroadCastMessage() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public int getIdGame() {
        return -1;
    }


}
