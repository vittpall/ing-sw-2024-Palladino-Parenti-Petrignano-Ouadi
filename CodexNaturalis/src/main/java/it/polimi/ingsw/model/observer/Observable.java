package it.polimi.ingsw.model.observer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Observable {
    private ArrayList<GameListener> listeners;

    public Observable() {
        listeners = new ArrayList<>();
    }

    public void subscribeListener(GameListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void unSubscribeListener(GameListener listener) {
        listeners.remove(listener);
    }

    public void notifyColorSelection() {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    listener.onTokenColorSelected();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void notifyJoinedGame() throws RemoteException {
        for (GameListener listener : listeners) {
            listener.onGameJoined();
        }
    }

    public void notifyCreatedGame() {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    listener.onGameCreated();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    //TODO define all the notify methods

}
