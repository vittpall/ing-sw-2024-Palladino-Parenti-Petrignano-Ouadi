package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GlobalChatState implements ClientStateTUI {

    private final BaseClient client;
    private final Scanner scanner;
    private ChatState returnState;


    public GlobalChatState(BaseClient client, Scanner scanner, ChatState returnState) {
        this.client = client;
        this.scanner = scanner;
        this.returnState = returnState;
    }

    @Override
    public void display() {
        System.out.println("Global chat-------------------");
        try {
            //if the receiver is null it will return the null value
            ArrayList<Message> globalChat = client.getMessages(null);
            if(globalChat == null || globalChat.isEmpty()){
                System.out.println("No messages available");
            }
            else
            {
                for(int i = 0;i < globalChat.size(); i++){
                    if(globalChat.get(i).getSender().equals(client.getUsername()))
                        System.out.println("You: " + globalChat.get(i).getContent());
                    else
                        System.out.println(globalChat.get(i).getSender() + ": " + globalChat.get(i).getContent());
                }
            }

            inputHandler(scanner.nextLine());
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    public void inputHandler(String input) throws IOException, ClassNotFoundException, InterruptedException {
        while(!input.equals("exit")){
            client.sendMessage(null, input);
            input = scanner.nextLine();
        }
        client.setCurrentState(returnState);
    }

    @Override
    public void promptForInput() {
    }

    public String toString() {
        return "GlobalChatState";
    }

    
   
}
