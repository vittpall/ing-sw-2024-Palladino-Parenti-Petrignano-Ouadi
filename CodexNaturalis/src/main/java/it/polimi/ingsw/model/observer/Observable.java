package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.notifications.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Observable {
    private final HashSet<GameListener> listeners;

    public Observable() {
        listeners = new HashSet<>();
    }

    public void subscribeListener(GameListener listener) {
        listeners.add(listener);
    }

    public void unSubscribeListener(GameListener listener) {
        listeners.remove(listener);
    }

    public void notifyColorSelection(String msg, ArrayList<TokenColor> availableColors) {
        notifyListeners(new TokenColorTakenNotification(msg, availableColors), null);
    }

    public void notifyJoinedGame(ArrayList<Player> players, int nOfMissingPlayers, String usernameSender) {
        notifyListeners(new GameJoinedNotification(players, nOfMissingPlayers), usernameSender);
    }

    public void notifyJoinedGameToOutsider(String msg, HashMap<Integer, Integer[]> availableGames) {
        notifyListeners(new GameJoinedNotificationToOutsiders(msg, availableGames), null);
    }

    public void notifyChangeTurn(String message, String username, String usernameSender) {
        notifyListeners(new ChangeTurnNotification(message, username), usernameSender);
    }

    public void notifyPlayedCard(String message, HashMap<String, Integer> playersPoints, String username) {
        notifyListeners(new PlayedCardNotification(message, playersPoints, username), username);
    }

    public void notifyLastTurnSet(String username, GameState gameState) {
        notifyListeners(new LastTurnSetNotification(username, gameState), null);
    }

    public void notifyChat(Message msg) {
        notifyListeners(new ChatNotification(msg), null);
    }

    public void notifyEndGame(String winner, HashMap<String, Integer> scores, HashMap<String, TokenColor> playersTokens) {
        notifyListeners(new EndGameNotification(winner, scores, playersTokens), null);
    }

    public void notifyCloseGame(String msg) {
        notifyListeners(new CloseGameNotification(msg), null);
    }

    private void notifyListeners(ServerNotification notification, String usernameSender) {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    if (usernameSender == null || !(listener.getUsername()).equals(usernameSender))
                        listener.update(notification);
                } catch (IOException e) {
                    System.out.println("This listener cannot be reached");
                }
            }).start();
        }
    }


}
