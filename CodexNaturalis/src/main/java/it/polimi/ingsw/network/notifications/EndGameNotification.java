package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;

import java.util.HashMap;

/**
 * Server notification used to notify the players of a specific game that the game has finished and the winner is known
 */
public class EndGameNotification implements ServerNotification {
    private final String winner;
    private final HashMap<String, Integer> scores;
    private final HashMap<String, TokenColor> playersTokens;

    /**
     * Constructor
     *
     * @param winner        String representing the winner or winners of the specific game
     * @param scores        HashMap that contains the scores of every player in the game
     * @param playersTokens HashMap that contains the tokens of every player in the game
     */
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
