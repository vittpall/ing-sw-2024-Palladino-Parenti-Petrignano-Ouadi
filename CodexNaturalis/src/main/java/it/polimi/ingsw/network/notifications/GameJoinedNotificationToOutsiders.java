package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.network.BaseClient;

import java.util.HashMap;

public class GameJoinedNotificationToOutsiders implements ServerNotification {
    String message;
    //alla posizione 0 c'è il numero totale di giocatori, alla posizione 1 c'è il numero di giocatori mancanti
    HashMap<Integer, Integer[]> availableGames;
    public GameJoinedNotificationToOutsiders(String msg, HashMap<Integer, Integer[]> availableGames) {
        this.message = msg;
        this.availableGames = availableGames;
    }
    @Override
    public void notifyClient(BaseClient client) {
        client.onGameJoinedAsOutsider(message, availableGames);
    }
}
