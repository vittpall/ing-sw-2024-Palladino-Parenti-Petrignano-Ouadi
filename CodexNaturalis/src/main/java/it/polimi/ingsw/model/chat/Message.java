package it.polimi.ingsw.model.chat;

import it.polimi.ingsw.model.enumeration.TokenColor;

import java.io.Serializable;

/**
 * This class represents a chat message.
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
     * @param gameId   the id of the game the message is related to
     * @param senderColor the color of the sender
     */
    public Message(String sender, String receiver, String content, int gameId, TokenColor senderColor) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.gameId = gameId;
        this.senderColor = senderColor;
    }

    /**
     * This method returns the color of the sender
     * @return the color of the sender
     */
    public TokenColor getSenderColor() {
        return senderColor;
    }

    /**
     * This method returns the sender of the message
     * @return the sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     * This method returns the receiver of the message
     * @return the receiver of the message
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * This method returns the content of the message
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * This method sets the receiver of the message
     * @param receiver the receiver of the message
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * This method returns the id of the game the message is related to
     * @return the id of the game the message is related to
     */
    public int getGameId() {
        return this.gameId;
    }
}
