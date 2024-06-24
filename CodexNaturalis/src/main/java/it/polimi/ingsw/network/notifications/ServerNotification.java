package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.io.Serializable;

/**
 * Interface that represents a notification sent to the client by the server
 */
public interface ServerNotification extends Serializable {
    /**
     * Method used by the subclasses to notify the client of the specific notification
     * It is called by the client to handle the specific notification created and sent by thr server
     *
     * @param client is a reference to the client to which the notification is sent
     * @throws IOException            when a communication-related problem occurs
     * @throws ClassNotFoundException when a class of an instance called is not found
     * @throws InterruptedException   when the thread running is interrupted
     */
    void notifyClient(BaseClient client) throws IOException, ClassNotFoundException, InterruptedException;
}
