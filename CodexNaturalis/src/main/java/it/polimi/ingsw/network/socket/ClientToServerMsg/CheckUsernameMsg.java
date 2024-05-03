package it.polimi.ingsw.network.socket.ClientToServerMsg;


import it.polimi.ingsw.Controller.LobbyController;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.UserAlreadyTaken;

public class CheckUsernameMsg extends ClientToServerMsg{

    public CheckUsernameMsg(String username)
    {
        this.username = username;
        this.functionToCall = "checkUsername";
    }

    public String functionToCall(LobbyController controller)
    {
        return String.valueOf(controller.checkUsername(username));
    }

    @Override
    public ServerToClientMsg getTypeofResponse() {
        return new UserAlreadyTaken();
    }

    public String getUsername() {
        return username;
    }

}
