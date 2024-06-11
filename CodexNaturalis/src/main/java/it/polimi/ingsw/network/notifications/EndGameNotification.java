package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

public class EndGameNotification implements ServerNotification {
    String message;
    public EndGameNotification(String message) {
        this.message = message;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onEndGame(message);
    }
}
