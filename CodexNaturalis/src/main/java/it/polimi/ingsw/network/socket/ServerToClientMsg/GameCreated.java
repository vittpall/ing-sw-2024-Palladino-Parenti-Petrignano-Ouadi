package it.polimi.ingsw.network.socket.ServerToClientMsg;

import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.BaseClient;

public class GameCreated extends ServerToClientMsg{
    public GameCreated(TypeServerToClientMsg type) {
        super(type);
    }

    @Override
    public void functionToCall(BaseClient client) {
        client.onGameCreated((String)response.getResponseReturnable());
    }
}
