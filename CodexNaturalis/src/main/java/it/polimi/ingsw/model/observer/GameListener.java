package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.IOException;
import java.rmi.Remote;

public interface GameListener extends Remote {

    void update(ServerToClientMsg msg) throws IOException;
}
