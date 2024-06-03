package it.polimi.ingsw.network.socket.ServerToClientMsg;

import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.BaseClient;

import java.rmi.RemoteException;

public class GameJoined extends ServerToClientMsg{
    public GameJoined(TypeServerToClientMsg type) {
        super(type);
    }

    @Override
    public void functionToCall(BaseClient client) throws RemoteException {
        client.onGameJoined((String)response.getResponseReturnable());
    }
}
