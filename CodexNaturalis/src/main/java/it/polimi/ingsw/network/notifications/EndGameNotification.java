package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;

import java.util.HashMap;

public class EndGameNotification implements ServerNotification {
    private final String winner;
    private final HashMap<String, Integer> scores;
    private final HashMap<String, TokenColor> playersTokens;

    public EndGameNotification(String winner, HashMap<String, Integer> scores, HashMap<String, TokenColor> playersTokens) {
        this.winner = winner;
        this.scores = scores;
        this.playersTokens = playersTokens;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onEndGame(winner, scores, playersTokens);
    }
}
