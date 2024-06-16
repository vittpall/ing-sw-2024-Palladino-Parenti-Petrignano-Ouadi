package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

public class LastTurnSetNotification implements ServerNotification{
    String username;
    public LastTurnSetNotification(String username) {
        this.username = username;
    }
    @Override
    public void notifyClient(BaseClient client) {
        client.onLastTurnSet(username);
    }
}
