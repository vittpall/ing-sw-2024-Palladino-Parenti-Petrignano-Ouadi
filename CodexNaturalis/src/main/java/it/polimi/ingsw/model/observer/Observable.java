package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.io.Serializable;

public interface Observable {
    void subscribeListener(GameListener listener, String eventToListen);

    void unSubscribeListener(GameListener listener, String eventToListen);

    void notifyObserver(String eventToListen, ReturnableObject messageToShow);
}
