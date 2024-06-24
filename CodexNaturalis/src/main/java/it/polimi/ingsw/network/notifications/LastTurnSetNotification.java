package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

/**
 * Server notification used to notify the players of a specific game that one player reached 20 points and the last turn has been set
 */
public class LastTurnSetNotification implements ServerNotification {
    String username;

    /**
     * Constructor
     *
     * @param username String representing the username of the player that reached 20 points
     */
    public LastTurnSetNotification(String username) {
        this.username = username;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onLastTurnSet(username);
    }
}
