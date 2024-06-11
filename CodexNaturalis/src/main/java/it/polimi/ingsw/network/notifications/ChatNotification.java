package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.BaseClient;

public class ChatNotification implements ServerNotification{

    private final Message msg;

    public ChatNotification(Message msg) {
        this.msg = msg;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onChatMessageReceived(msg);
    }
}
