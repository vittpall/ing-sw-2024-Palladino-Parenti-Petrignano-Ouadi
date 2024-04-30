package it.polimi.ingsw.model.chat;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat {
    private ArrayList<Message> globalChat;
    private HashMap<String, ArrayList<Message>> privateChats;

    public Chat(){
        globalChat = new ArrayList<>();
        privateChats = new HashMap<>();
    }

    public void addMessage(Message message)
    {

        if(message.getReceiver() == null)
        {
            //global message
            globalChat.add(message);
        }
        else
        {
            //private message
            if(privateChats.containsKey(message.getReceiver() + "_" + message.getSender()))
            {
                privateChats.get(message.getReceiver() + "_" + message.getSender()).add(message);
            }
            else if(privateChats.containsKey(message.getSender() + "_" + message.getReceiver()))
            {
                privateChats.get(message.getReceiver() + "_" + message.getSender()).add(message);
            }

        }
    }


}
