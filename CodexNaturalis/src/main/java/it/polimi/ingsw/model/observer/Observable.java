package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

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

    public void notifyColorSelection(String msg) {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    listener.update(new ServerToClientMsg(null));
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public void notifyJoinedGame(String msg) throws RemoteException {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    listener.update(new ServerToClientMsg(null));
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public void notifyCreatedGame() {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    listener.update(new ServerToClientMsg(null));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    //TODO define all the notify methods

}
