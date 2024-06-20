package it.polimi.ingsw.model.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Chat implements Serializable {
    private final ArrayList<Message> globalChat;
    private final HashMap<String, ArrayList<Message>> privateChats;

    public Chat() {
        globalChat = new ArrayList<>();
        privateChats = new HashMap<>();
    }

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


    public ArrayList<Message> getGlobalChat() {
        return globalChat;
    }

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
