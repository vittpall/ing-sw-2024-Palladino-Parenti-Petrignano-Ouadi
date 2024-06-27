package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;

import java.util.ArrayList;

/**
 * Server notification used to notify the players of a specific game that has not started yet that a player joined the game
 */
public class GameJoinedNotification implements ServerNotification {
    private final int nOfMissingPlayers;
    private final ArrayList<Player> players;

    /**
     * Constructor
     *
     * @param players           ArrayList representing the players that joined the game
     * @param nOfMissingPlayers Integer representing the number of missing players in the specific game
     */
    public GameJoinedNotification(ArrayList<Player> players, int nOfMissingPlayers) {
        this.players = players;
        this.nOfMissingPlayers = nOfMissingPlayers;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onGameJoined(players, nOfMissingPlayers);
    }
}
