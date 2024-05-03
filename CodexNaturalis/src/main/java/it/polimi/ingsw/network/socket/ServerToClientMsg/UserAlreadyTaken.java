package it.polimi.ingsw.network.socket.ServerToClientMsg;

public class UserAlreadyTaken extends ServerToClientMsg {

    private String response;

    public UserAlreadyTaken() {
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
