package it.polimi.ingsw.model.observer;

import java.io.IOException;
import java.util.ArrayList;

public class Observable {
    private ArrayList<GameListener> listeners;

    public Observable()
    {
        listeners = new ArrayList<>();
    }

    public void subscribeListener(GameListener listener)
    {
        if(listeners == null)
        {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void unSubscribeListener(GameListener listener)
    {
        listeners.remove(listener);
    }

    public void notifyColorSelection() throws IOException {
        for(GameListener listener : listeners)
        {
            new Thread(() -> {
                try {
                    listener.update(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void notifyJoinedGame() throws IOException {
        for(GameListener listener : listeners)
        {
            listener.update(null);
        }
    }

    public void notifyCreatedGame() throws IOException {
        for(GameListener listener : listeners)
        {
            new Thread(() -> {
                try {
                    listener.update(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    //TODO define all the notify methods

}
