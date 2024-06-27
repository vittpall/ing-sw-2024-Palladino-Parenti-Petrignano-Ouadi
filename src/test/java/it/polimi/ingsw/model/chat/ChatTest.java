package it.polimi.ingsw.model.chat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    private Chat chat;

    @BeforeEach
    void setUp() {
        chat = new Chat();
    }

    @Test
    void addGlobalMessage() {
        Message message = new Message("sender", null, "Hello, world!", 0, null);
        chat.addMessage(message);
        ArrayList<Message> globalChat = chat.getGlobalChat();
        assertTrue(globalChat.contains(message));
    }

    @Test
    void addPrivateMessage() {
        Message message = new Message("sender", "receiver", "Hello, world!", 0, null);
        chat.addMessage(message);
        ArrayList<Message> privateChat = chat.getPrivateChat("sender", "receiver");
        assertTrue(privateChat.contains(message));
    }

    @Test
    void retrieveGlobalChat() {
        Message message = new Message("sender", null, "Hello, world!", 0, null);
        chat.addMessage(message);
        ArrayList<Message> globalChat = chat.getGlobalChat();
        assertTrue(globalChat.contains(message));
    }

    @Test
    void retrievePrivateChatSenderReceiver() {
        Message message = new Message("sender", "receiver", "Hello, world!", 0, null);
        chat.addMessage(message);
        ArrayList<Message> privateChat = chat.getPrivateChat("sender", "receiver");
        assertTrue(privateChat.contains(message));
    }

    @Test
    void retrievePrivateChatOppositeKeyReceiverSender()
    {
        Message message = new Message("sender", "receiver", "Hello, world!", 0, null);
        chat.addMessage(message);
        ArrayList<Message> privateChat = chat.getPrivateChat("receiver", "sender");
        assertTrue(privateChat.contains(message));
    }

    @Test
    void retrieveMultipleChatBetweenTwoPlayers()
    {
        Message message1 = new Message("sender", "receiver", "Hello, world!", 0, null);
        Message message2 = new Message("receiver", "sender", "Hello, world!", 0, null);
        chat.addMessage(message1);
        chat.addMessage(message2);
        ArrayList<Message> privateChat = chat.getPrivateChat("sender", "receiver");
        assertTrue(privateChat.contains(message1));
        assertTrue(privateChat.contains(message2));
    }

    @Test
    void returnEmptyListIfNoPrivateChatExists() {
        ArrayList<Message> privateChat = chat.getPrivateChat("sender", "receiver");
        assertTrue(privateChat.isEmpty());
    }

    @Test
    void shouldReturnPrivateChatRegardlessOfOrder() {
        Message message = new Message("sender", "receiver", "Hello, world!", 0, null);
        chat.addMessage(message);
        ArrayList<Message> privateChat = chat.getPrivateChat("receiver", "sender");
        assertTrue(privateChat.contains(message));
    }
}
