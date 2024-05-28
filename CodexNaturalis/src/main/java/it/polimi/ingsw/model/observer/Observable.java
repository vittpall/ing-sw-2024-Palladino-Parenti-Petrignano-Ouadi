package it.polimi.ingsw.model.observer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Observable {
    private final ArrayList<GameListener> listeners;

    public Observable() {
        listeners = new ArrayList<>();
    }

    public void subscribeListener(GameListener listener) {
        listeners.add(listener);
    }

    public void unSubscribeListener(GameListener listener) {
        listeners.remove(listener);
    }

    public void notifyColorSelection() throws RemoteException {
        for (GameListener listener : listeners) {
            listener.onTokenColorSelected();
        }
    }

    public void notifyJoinedGame(String msg) throws RemoteException {
        for (GameListener listener : listeners) {
            listener.onGameJoined(msg);
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
