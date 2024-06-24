package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.BaseClient;

/**
 * Server notification used to notify the receivers of a message that it has been sent in the chat
 */
public class ChatNotification implements ServerNotification {

    private final Message msg;

    /**
     * Constructor
     *
     * @param msg is the Message sent to the client
     */
    public ChatNotification(Message msg) {
        this.msg = msg;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onChatMessageReceived(msg);
    }
}
