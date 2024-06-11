package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.network.notifications.ServerNotification;

import java.io.IOException;
import java.rmi.Remote;

public interface GameListener extends Remote {

    void update(ServerNotification notification) throws IOException;

    String getUsername();
}
