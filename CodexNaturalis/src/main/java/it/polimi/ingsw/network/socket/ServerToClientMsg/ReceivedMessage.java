package it.polimi.ingsw.network.socket.ServerToClientMsg;

import it.polimi.ingsw.network.socket.Client.ReturnableObject;

public class ReceivedMessage extends ServerToClientMsg{
    @Override
    public void setResponse(ReturnableObject response) {
        this.response = response;
    }

    @Override
    public ReturnableObject getResponse() {
        return this.response;
    }
}
