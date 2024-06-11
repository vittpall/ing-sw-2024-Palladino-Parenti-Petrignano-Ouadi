package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.util.HashMap;

public class PlayedCardNotification implements ServerNotification{
    String message;
    HashMap<String, Integer> playersPoints;
    String username;
    public PlayedCardNotification(String message, HashMap<String, Integer> playersPoints, String username) {
        this.message = message;
        this.playersPoints = playersPoints;
        this.username = username;
    }
    @Override
    public void notifyClient(BaseClient client) {
        client.onPlayedCard(message, playersPoints, username);
    }
}
