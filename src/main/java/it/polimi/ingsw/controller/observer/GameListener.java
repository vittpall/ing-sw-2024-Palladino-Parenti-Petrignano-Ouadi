package it.polimi.ingsw.controller.observer;

import it.polimi.ingsw.network.notifications.ServerNotification;

import java.io.IOException;
import java.rmi.Remote;

/**
 * This interface is used to define the methods that a listener must implement to be notified by the observable
 */
public interface GameListener extends Remote {

    /**
     * This method is used to update the listener with a notification
     *
     * @param notification the notification to send to the listener
     * @throws IOException if there is an error in the connection
     */
    void update(ServerNotification notification) throws IOException;

    /**
     * This method gets the username of the player
     *
     * @return the username of the player
     * @throws IOException if there is an error in the connection
     */
    String getUsername() throws IOException;
}
