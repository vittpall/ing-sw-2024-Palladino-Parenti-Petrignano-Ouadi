package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

/**
 * Server notification used to notify the players of a specific game that the turn has changed
 */
public class ChangeTurnNotification implements ServerNotification {
    private final String message;
    private final String username;

    /**
     * Constructor
     *
     * @param message  String representing the message that needs to be print on the client
     * @param username String representing the username of the player that needs to play next
     */
    public ChangeTurnNotification(String message, String username) {
        this.message = message;
        this.username = username;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onChangeTurn(message, username);
    }
}
