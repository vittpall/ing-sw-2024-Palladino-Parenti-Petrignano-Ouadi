package it.polimi.ingsw.model.chat;

import java.io.Serializable;

/**
 * This class represents a message that can be sent in the chat.

 */

public class Message implements Serializable {
    private String sender;
    private String receiver;
    private String content;
    private int gameId;

    /**
     * Default constructor
     * @param sender
     * @param receiver
     * @param content
     */
    public Message(String sender, String receiver, String content, int gameId) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.gameId = gameId;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGameId() {
        return this.gameId;
    }
}
