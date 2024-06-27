package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.util.HashMap;

/**
 * Server notification used to notify the user that needs to choose a game that a player has joined the game
 */
public class GameJoinedNotificationToOutsiders implements ServerNotification {
    private final String message;
    private final HashMap<Integer, Integer[]> availableGames;

    /**
     * Constructor
     *
     * @param msg            String representing the message that needs to be shown to the client
     * @param availableGames HashMap that contains the updated available games, the number of their players and the
     *                       number of players needed to start the game
     */
    public GameJoinedNotificationToOutsiders(String msg, HashMap<Integer, Integer[]> availableGames) {
        this.message = msg;
        this.availableGames = availableGames;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onGameJoinedAsOutsider(message, availableGames);
    }
}
