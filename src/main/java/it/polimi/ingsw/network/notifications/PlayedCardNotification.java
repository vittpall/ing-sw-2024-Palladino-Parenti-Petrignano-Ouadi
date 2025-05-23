package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Server notification used to notify the players of a specific game that a card has been played by one user
 */
public class PlayedCardNotification implements ServerNotification {
    private final String message;
    private final HashMap<String, Integer> playersPoints;
    private final String username;

    /**
     * Constructor
     *
     * @param message       String representing the message that needs to be shown to the user
     * @param playersPoints HashMap that contains the updated points of the players
     * @param username      String representing the username of the player that played the card
     */
    public PlayedCardNotification(String message, HashMap<String, Integer> playersPoints, String username) {
        this.message = message;
        this.playersPoints = playersPoints;
        this.username = username;
    }

    @Override
    public void notifyClient(BaseClient client) throws RemoteException {
        client.onPlayedCard(message, playersPoints, username);
    }
}
