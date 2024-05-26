package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.gui.GameState;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.Client.RMIClient;
import it.polimi.ingsw.network.socket.Server.ClientHandler;
import it.polimi.ingsw.tui.ClientState;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public interface GameListener extends Remote {

    void update() throws RemoteException;
}
