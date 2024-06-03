package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

public class TokenColorTakenNotification implements ServerNotification {

    String message;

    public TokenColorTakenNotification(String message) {
        this.message = message;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onTokenColorSelected(message);
    }
}
