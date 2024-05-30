package it.polimi.ingsw.model.observer;

import java.io.IOException;
import java.rmi.RemoteException;
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

    public void notifyColorSelection(String msg) throws RemoteException {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    listener.onTokenColorSelected(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void notifyJoinedGame(String msg) throws RemoteException {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                      listener.onGameJoined(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }).start();
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
