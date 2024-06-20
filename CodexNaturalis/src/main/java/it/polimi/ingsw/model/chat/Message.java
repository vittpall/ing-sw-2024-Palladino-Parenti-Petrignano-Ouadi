package it.polimi.ingsw.model.chat;

import it.polimi.ingsw.model.enumeration.TokenColor;

import java.io.Serializable;

/**
 * This class represents a message that can be sent in the chat.
 */

public class Message implements Serializable {
    private final String sender;
    private String receiver;
    private final String content;
    private final int gameId;
    private final TokenColor senderColor;

    /**
     * Default constructor
     *
     * @param sender   the sender of the message
     * @param receiver the receiver of the message
     * @param content  the content of the message
     */
    public Message(String sender, String receiver, String content, int gameId, TokenColor senderColor) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.gameId = gameId;
        this.senderColor = senderColor;
    }

    public TokenColor getSenderColor() {
        return senderColor;
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

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getGameId() {
        return this.gameId;
    }
}
