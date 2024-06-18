package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.util.HashMap;

public class EndGameNotification implements ServerNotification {
    String winner;
    HashMap<String, Integer> scores;
    public EndGameNotification(String winner, HashMap<String, Integer> scores) {
        this.winner = winner;
        this.scores = scores;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onEndGame(winner, scores);
    }
}
