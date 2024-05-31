package it.polimi.ingsw.network.socket.ServerToClientMsg;

import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.rmi.RemoteException;

public class TokenColorTaken extends ServerToClientMsg{

    public TokenColorTaken(TypeServerToClientMsg type) {
        super(type);
    }

    @Override
    public void functionToCall(VirtualView client) throws RemoteException {
        client.onTokenColorSelected((String)response.getResponseReturnable());
    }
}
