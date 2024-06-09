package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;

import java.util.ArrayList;

public class GameJoinedNotification implements ServerNotification {
    String message;
    int nOfMissingPlayers;
    ArrayList<Player> players;

    public GameJoinedNotification(String message, ArrayList<Player> players, int nOfMissingPlayers) {
        this.message = message;
        this.players = players;
        this.nOfMissingPlayers = nOfMissingPlayers;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onGameJoined(message, players, nOfMissingPlayers);
    }
}
