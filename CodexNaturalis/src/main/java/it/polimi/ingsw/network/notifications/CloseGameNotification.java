package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;

public class CloseGameNotification implements ServerNotification {
    private final String message;

    public CloseGameNotification(String message) {
        this.message = message;
    }

    @Override
    public void notifyClient(BaseClient client) throws IOException, ClassNotFoundException, InterruptedException {
        client.onGameClosed(message);
    }
}
