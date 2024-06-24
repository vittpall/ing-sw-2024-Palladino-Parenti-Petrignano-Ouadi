package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.UsefulData;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This client state is used when the user wants to chat in the global chat
 */
public class GlobalChatState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;
    private final ChatState returnState;

    /**
     * Constructor
     *
     * @param client      is a reference to the client class that can call the methods in the server
     * @param scanner     is a reference to the class that handles and returns the input of the user
     * @param returnState is a reference to the class that created the instance
     */
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
            if (globalChat == null || globalChat.isEmpty()) {
                System.out.println("No messages available");
            } else {
                for (Message message : globalChat) {
                    if (message.getSender().equals(client.getUsername()))
                        System.out.println(message.getSenderColor().getColorValueANSII() + "You: " + UsefulData.RESET + message.getContent());
                    else
                        System.out.println(message.getSenderColor().getColorValueANSII() + message.getSender() + UsefulData.RESET + ": " + message.getContent());
                }
            }
            inputHandler(scanner.nextLine());
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {

    }

    /**
     * Defines how to handle the user's input in a specific way for this class
     *
     * @param input The user's input
     * @throws IOException            when an I/O operation fails
     * @throws ClassNotFoundException when the class loaded can not be found
     * @throws InterruptedException   when the thread running is interrupted
     */
    public void inputHandler(String input) throws IOException, ClassNotFoundException, InterruptedException {
        while (!input.equals("exit chat")) {
            client.sendMessage(null, input);
            input = scanner.nextLine();
        }
        client.setCurrentState(returnState);
        client.getClientCurrentState().display();
    }

    @Override
    public void promptForInput() {
    }

    @Override
    public String toString() {
        return "GlobalChatState";
    }


}
