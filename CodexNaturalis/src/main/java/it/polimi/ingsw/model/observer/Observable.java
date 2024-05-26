package it.polimi.ingsw.model.observer;

public interface Observable {
    void subscribeListener(GameListener listener, String eventToListen);

    void unSubscribeListener(GameListener listener, String eventToListen);

    void notifyObserver(String eventToListen);
}
