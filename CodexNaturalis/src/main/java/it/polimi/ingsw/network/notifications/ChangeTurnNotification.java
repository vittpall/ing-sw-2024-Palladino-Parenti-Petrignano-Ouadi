package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

public class ChangeTurnNotification implements ServerNotification {
    String message;
    String username;
    public ChangeTurnNotification(String message, String username) {
        this.message = message;
        this.username = username;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onChangeTurn(message, username);
    }
}
