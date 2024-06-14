package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;

import java.util.ArrayList;

public class GameJoinedNotification implements ServerNotification {
    int nOfMissingPlayers;
    ArrayList<Player> players;

    public GameJoinedNotification(ArrayList<Player> players, int nOfMissingPlayers) {
        this.players = players;
        this.nOfMissingPlayers = nOfMissingPlayers;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onGameJoined( players, nOfMissingPlayers);
    }
}
