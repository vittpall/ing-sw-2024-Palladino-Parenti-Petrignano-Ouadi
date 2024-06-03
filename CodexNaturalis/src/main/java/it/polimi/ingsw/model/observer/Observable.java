package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.network.notifications.GameCreatedNotification;
import it.polimi.ingsw.network.notifications.GameJoinedNotification;
import it.polimi.ingsw.network.notifications.ServerNotification;
import it.polimi.ingsw.network.notifications.TokenColorTakenNotification;

import java.io.IOException;
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

    public void notifyColorSelection(String msg) {
        notifyListeners(new TokenColorTakenNotification(msg));
    }

    public void notifyJoinedGame(String msg) {
        notifyListeners(new GameJoinedNotification(msg));
    }

    public void notifyCreatedGame(String msg) {
        notifyListeners(new GameCreatedNotification(msg));
    }

    private void notifyListeners(ServerNotification notification) {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    listener.update(notification);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    //TODO define all the notify methods

}
