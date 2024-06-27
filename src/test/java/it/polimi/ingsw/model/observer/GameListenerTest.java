package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.controller.observer.GameListener;
import it.polimi.ingsw.network.notifications.ServerNotification;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameListenerTest {

    @Test
    public void testUpdate() throws IOException {
        final int[] value = new int[1];
        GameListener gameListener = new GameListener() {
            @Override
            public void update(ServerNotification notification) {
                value[0] = 1;
            }

            @Override
            public String getUsername() {
                return "";
            }
        };

        gameListener.update(null);
        assertEquals(1, value[0]);

    }

    @Test
    public void testGetUsername() throws IOException {
        GameListener gameListener = new GameListener() {
            @Override
            public void update(ServerNotification notification) {
            }

            @Override
            public String getUsername() {
                return "username";
            }
        };

        assertEquals("username", gameListener.getUsername());
    }

    @Test
    public void testGetUsernameException() {
        GameListener gameListener = new GameListener() {
            @Override
            public void update(ServerNotification notification) {
            }

            @Override
            public String getUsername() throws IOException {
                throw new IOException();
            }
        };

        assertThrows(IOException.class, gameListener::getUsername);
    }


}

