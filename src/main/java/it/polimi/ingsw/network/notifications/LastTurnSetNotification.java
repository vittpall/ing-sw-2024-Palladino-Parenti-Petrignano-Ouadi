package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.network.BaseClient;

/**
 * Server notification used to notify the players of a specific game that one player reached 20 points and the last turn has been set
 */
public class LastTurnSetNotification implements ServerNotification {
    private final String username;
    private final GameState gameState;

    /**
     * Constructor
     *
     * @param username  String representing the username of the player that reached 20 points
     * @param gameState GameState representing the state of the game that caused the notification
     */
    public LastTurnSetNotification(String username, GameState gameState) {
        this.username = username;
        this.gameState = gameState;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onLastTurnSet(username, gameState);
    }
}
