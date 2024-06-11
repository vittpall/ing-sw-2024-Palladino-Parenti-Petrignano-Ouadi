package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

public class LastTurnSetNotification implements ServerNotification{
    String message;
    public LastTurnSetNotification(String message) {
        this.message = message;
    }
    @Override
    public void notifyClient(BaseClient client) {
        client.onLastTurnSet(message);
    }
}
