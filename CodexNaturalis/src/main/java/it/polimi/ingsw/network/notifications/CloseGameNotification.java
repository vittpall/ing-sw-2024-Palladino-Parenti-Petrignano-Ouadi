package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

public class CloseGameNotification implements ServerNotification {
    String message;

    public CloseGameNotification(String message) {
        this.message = message;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onGameClosed(message);
    }
}
