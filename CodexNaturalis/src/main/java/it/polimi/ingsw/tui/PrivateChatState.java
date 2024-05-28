package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.Client.RMIClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class PrivateChatState implements ClientState{
    private BaseClient client;
    private final Scanner scanner;
    private String receiver;
    private int lastMessageReceived;
    private ClientState returnState;

    public PrivateChatState(BaseClient client, Scanner scanner, String receiver, ChatState returnState) {
        this.client = client;
        this.scanner = scanner;
        this.receiver = receiver;
        this.lastMessageReceived = 0;
        this.returnState = returnState;
    }

    @Override
    public void display(){
        try {
        System.out.println("Private chat with " + receiver);
        ArrayList<Message> chat = client.getMessages(receiver);
        if(chat == null || chat.isEmpty()){
            System.out.println("No messages available");
        }
        else
        {
            for (Message message : chat) {
                //show all the messages till the last one that has been received

                if (message.getSender().equals(client.getUsername()))
                    System.out.println("You: " + message.getContent());
                else
                    System.out.println(message.getSender() + ": " + message.getContent());
            }
        }

        inputHandler(scanner.nextLine());
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            System.err.println("An error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
    }

    public void inputHandler(String input) throws IOException, ClassNotFoundException, InterruptedException {
        while(!input.equals("exit")){
            client.sendMessage(receiver, input);
            input = scanner.nextLine();
        }
        client.setCurrentState(this.returnState);
    }

    @Override
    public void promptForInput() {

    }

    public String toString() {
        return "PrivateChatState";
    }

    /**
     *
     */
    @Override
    public void refresh() {

    }
}
