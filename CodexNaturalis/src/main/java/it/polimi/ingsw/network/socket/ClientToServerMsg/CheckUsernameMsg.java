package it.polimi.ingsw.network.socket.ClientToServerMsg;


import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.UserAlreadyTaken;

public class CheckUsernameMsg extends ClientToServerMsg{

    public CheckUsernameMsg(String username)
    {
        this.username = username;
    }

    public ReturnableObject<Boolean> functionToCall(LobbyController controller)
    {
        ReturnableObject<Boolean> toReturn = new ReturnableObject<>();
        toReturn.setResponseReturnable(controller.checkUsername(username));
        return toReturn;
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new UserAlreadyTaken();
    }


}
