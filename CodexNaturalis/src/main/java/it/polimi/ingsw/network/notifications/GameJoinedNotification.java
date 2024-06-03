package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

public class GameJoinedNotification implements ServerNotification {
    String message;

    public GameJoinedNotification(String message) {
        this.message = message;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onGameJoined(message);
    }
}
