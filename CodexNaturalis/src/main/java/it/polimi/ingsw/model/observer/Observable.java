package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.network.notifications.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.TokenColor;

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
        notifyListeners(new TokenColorTakenNotification(msg, availableColors));
    }

    public void notifyJoinedGame(String msg, ArrayList<Player> players, int nOfMissingPlayers) {
        notifyListeners(new GameJoinedNotification(msg, players, nOfMissingPlayers));
    }
    public void notifyJoinedGameToOutsider(String msg, HashMap<Integer, Integer[]> availableGames) {
        notifyListeners(new GameJoinedNotificationToOutsiders(msg, availableGames));
    }

    public void notifyChangeTurn(String message, String username){
        notifyListeners(new ChangeTurnNotification(message, username));
    }

    public void notifyPlayedCard(String message,HashMap<String, Integer> playersPoints, String username){
        notifyListeners(new PlayedCardNotification(message,playersPoints, username));
    }

    public void notifyLastTurnSet(String message){
        notifyListeners(new LastTurnSetNotification(message));
    }

    public void notifyEndGame(String message){
        notifyListeners(new EndGameNotification(message));
    }
    public void notifyCloseGame(String msg) {
        notifyListeners(new CloseGameNotification(msg));
    }

    private void notifyListeners(ServerNotification notification) {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    listener.update(notification);
                } catch (IOException e) {
                    System.out.println("This listener cannot be reached");
                }
            }).start();
        }
    }


    //TODO define all the notify methods

}
