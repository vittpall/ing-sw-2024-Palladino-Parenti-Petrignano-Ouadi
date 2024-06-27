package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;

/**
 * Server notification used to notify the players of a specific game that the game will be closed
 */
public class CloseGameNotification implements ServerNotification {
    private final String message;

    /**
     * Constructor
     *
     * @param message String representing the message that needs to be print on the client in order to notify that the game will be closed
     */
    public CloseGameNotification(String message) {
        this.message = message;
    }

    @Override
    public void notifyClient(BaseClient client) throws IOException, ClassNotFoundException, InterruptedException {
        client.onGameClosed(message);
    }
}
