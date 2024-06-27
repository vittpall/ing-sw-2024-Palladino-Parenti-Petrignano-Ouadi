package it.polimi.ingsw.model.observer;


import it.polimi.ingsw.network.notifications.ServerNotification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;


public class ObservableTest {


    @Test
    public void testSubscribeListener() {
        Observable observable = new Observable();
        GameListener gameListener = new GameListener() {
            @Override
            public void update(ServerNotification notification) {

            }

            @Override
            public String getUsername() {
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
            public void update(ServerNotification notification) {

            }

            @Override
            public String getUsername() {
                return "";
            }
        };

        GameListener gameListener2 = new GameListener() {
            @Override
            public void update(ServerNotification notification) {

            }

            @Override
            public String getUsername() {
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
            public void update(ServerNotification notification) {
                assertNull(notification);
            }

            @Override
            public String getUsername() {
                return "";
            }
        };

        observable.notifyColorSelection("msg", null);
        observable.subscribeListener(gameListener);

        observable.unSubscribeListener(gameListener);

    }

}
