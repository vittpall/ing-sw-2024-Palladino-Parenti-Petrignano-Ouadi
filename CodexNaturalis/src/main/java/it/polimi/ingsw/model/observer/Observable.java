package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.model.chat.Message;
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
        notifyListeners(new TokenColorTakenNotification(msg, availableColors), null);
    }

    public void notifyJoinedGame(ArrayList<Player> players, int nOfMissingPlayers) {
        notifyListeners(new GameJoinedNotification(players, nOfMissingPlayers), null);
    }
    public void notifyJoinedGameToOutsider(String msg, HashMap<Integer, Integer[]> availableGames) {
        notifyListeners(new GameJoinedNotificationToOutsiders(msg, availableGames), null);
    }

    public void notifyChangeTurn(String message, String username, String usernameSender){
        notifyListeners(new ChangeTurnNotification(message, username), usernameSender);
    }

    public void notifyPlayedCard(String message,HashMap<String, Integer> playersPoints, String username){
        notifyListeners(new PlayedCardNotification(message,playersPoints, username), username);
    }

    public void notifyLastTurnSet(String message){
        notifyListeners(new LastTurnSetNotification(message), null);
    }

    public void notifyChat(Message msg)
    {
        notifyListeners(new ChatNotification(msg), null);
    }

    public void notifyEndGame(String message){
        notifyListeners(new EndGameNotification(message), null);
    }

    public void notifyCloseGame(String msg) {
        notifyListeners(new CloseGameNotification(msg), null);
    }

    private void notifyListeners(ServerNotification notification, String usernameSender) {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    //usernameSender is equal to null just in CloseGameNotification to notify also the sender
                    if(usernameSender==null||!listener.getUsername().equals(usernameSender))
                        listener.update(notification);
                } catch (IOException e) {
                    System.out.println("This listener cannot be reached");
                }
            }).start();
        }
    }


}
