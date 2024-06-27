package it.polimi.ingsw.model.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Chat class represents a chat system in the game.
 * It manages both global and private messages between players.
 */
public class Chat implements Serializable {
    private final ArrayList<Message> globalChat;
    private final HashMap<String, ArrayList<Message>> privateChats;

    /**
     * Default constructor
     */
    public Chat() {
        globalChat = new ArrayList<>();
        privateChats = new HashMap<>();
    }

    /**
     * Adds a message to the chat. If the receiver is null it'll be added to the global chat, otherwise to the private chat.
     *
     * @param message the message to be added
     */
    public void addMessage(Message message) {

        if (message.getReceiver() == null) {
            //global message
            globalChat.add(message);
        } else {
            //private message
            System.out.println("receiver: " + message.getReceiver() + " sender: " + message.getSender());
            if (privateChats.containsKey(message.getReceiver() + "_" + message.getSender())) {
                privateChats.get(message.getReceiver() + "_" + message.getSender()).add(message);
            } else if (privateChats.containsKey(message.getSender() + "_" + message.getReceiver())) {
                privateChats.get(message.getSender() + "_" + message.getReceiver()).add(message);
            } else {
                privateChats.put(message.getReceiver() + "_" + message.getSender(), new ArrayList<>());
                privateChats.get(message.getReceiver() + "_" + message.getSender()).add(message);
            }

        }
    }

    /**
     * Returns the global chat
     *
     * @return the global chat
     */
    public ArrayList<Message> getGlobalChat() {
        return globalChat;
    }

    /**
     * Returns the private chat between two players.
     * The chat between two player is saved as "player1_player2" or "player2_player1".
     *
     * @param receiver the receiver of the message
     * @param sender   the sender of the message
     * @return the private chat between the two players
     */
    public ArrayList<Message> getPrivateChat(String receiver, String sender) {
        if (!privateChats.containsKey(receiver + "_" + sender)) {
            if (privateChats.containsKey(sender + "_" + receiver)) {
                return privateChats.get(sender + "_" + receiver);
            }

            privateChats.put(receiver + "_" + sender, new ArrayList<>());

        }
        return privateChats.get(receiver + "_" + sender);
    }
}
