package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

public class GameCreatedNotification implements ServerNotification {
    String message;

    public GameCreatedNotification(String message) {
        this.message = message;
    }


    @Override
    public void notifyClient(BaseClient client) {
        client.onGameCreated(message);
    }
}
