package it.polimi.ingsw.model.observer;


import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.notifications.ServerNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;


public class ObservableTest {


    @Test
    public void testSubscribeListener() {
        Observable observable = new Observable();
        GameListener gameListener = new GameListener() {
            @Override
            public void update(ServerNotification notification) throws IOException {

            }

            @Override
            public String getUsername() throws IOException {
                return "";
            }
        };
        observable.subscribeListener(gameListener);

        observable.unSubscribeListener(gameListener);

    }

    @Test
    public void testSubscribeMultipleListeners() {
        Observable observable = new Observable();
        GameListener gameListener = new GameListener() {
            @Override
            public void update(ServerNotification notification) throws IOException {

            }

            @Override
            public String getUsername() throws IOException {
                return "";
            }
        };

        GameListener gameListener2 = new GameListener() {
            @Override
            public void update(ServerNotification notification) throws IOException {

            }

            @Override
            public String getUsername() throws IOException {
                return "";
            }
        };

        observable.subscribeListener(gameListener);
        observable.subscribeListener(gameListener2);

        observable.unSubscribeListener(gameListener);

    }


    @Test
    public void testUpdateListener() {
        Observable observable = new Observable();
        GameListener gameListener = new GameListener() {
            @Override
            public void update(ServerNotification notification) throws IOException {
                assertNull(notification);
            }

            @Override
            public String getUsername() throws IOException {
                return "";
            }
        };

        observable.notifyColorSelection("msg", null);
        observable.subscribeListener(gameListener);

        observable.unSubscribeListener(gameListener);

    }

}
