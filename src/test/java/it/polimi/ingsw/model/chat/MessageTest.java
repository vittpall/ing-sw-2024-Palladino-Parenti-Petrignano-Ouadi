package it.polimi.ingsw.model.chat;

import it.polimi.ingsw.model.enumeration.TokenColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    private Message message;

    @BeforeEach
    void setUp() {
        message = new Message("sender", "receiver", "content", 1, TokenColor.BLUE);
    }

    @Test
    void getSenderColor_returnsCorrectColor() {
        assertEquals(TokenColor.BLUE, message.getSenderColor());
    }

    @Test
    void getSender_returnsCorrectSender() {
        assertEquals("sender", message.getSender());
    }

    @Test
    void getReceiver_returnsCorrectReceiver() {
        assertEquals("receiver", message.getReceiver());
    }

    @Test
    void getContent_returnsCorrectContent() {
        assertEquals("content", message.getContent());
    }

    @Test
    void setReceiver_changesReceiver() {
        message.setReceiver("newReceiver");
        assertEquals("newReceiver", message.getReceiver());
    }

    @Test
    void getGameId_returnsCorrectGameId() {
        assertEquals(1, message.getGameId());
    }
}