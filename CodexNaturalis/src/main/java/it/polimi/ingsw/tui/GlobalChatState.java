package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.Client.RMIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GlobalChatState implements ClientState{

    private final VirtualView client;
    private final Scanner scanner;

    public GlobalChatState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("Global chat-------------------");
        try {
            //if the receiver is null it will return the null value
            ArrayList<Message> globalChat = client.getMessages(null);
            for(int i = 0; globalChat != null && i < globalChat.size(); i++){
                if(globalChat.get(i).getSender().equals(client.getUsername()))
                    System.out.println("You: " + globalChat.get(i).getContent());
                else
                    System.out.println(globalChat.get(i).getSender() + ": " + globalChat.get(i).getContent());
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
        client.setCurrentState(new ChatState(client, scanner));
    }

    @Override
    public void promptForInput() {
    }
}
