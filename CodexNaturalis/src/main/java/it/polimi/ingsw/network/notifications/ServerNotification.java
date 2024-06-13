package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.io.Serializable;

public interface ServerNotification extends Serializable {
    void notifyClient(BaseClient client) throws IOException, ClassNotFoundException, InterruptedException;
}
