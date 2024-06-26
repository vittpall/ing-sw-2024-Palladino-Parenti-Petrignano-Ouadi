package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.model.UsefulData;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This client state is used when the user wants to chat in a private chat
 */
public class PrivateChatState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;
    private final String receiver;

    /**
     * Constructor
     *
     * @param client   is a reference to the BaseClient class that can call the methods in the server
     * @param scanner  is a reference to the Scanner class that handles and returns the input of the user
     * @param receiver String representing the player with which the user wants to chat
     */
    public PrivateChatState(BaseClient client, Scanner scanner, String receiver) {
        this.client = client;
        this.scanner = scanner;
        this.receiver = receiver;
    }

    /**
     * @return String representing the receiver of the message
     */
    public String getReceiver() {
        return receiver;
    }

    @Override
    public void display() throws RemoteException {
        try {
            System.out.println("Private chat with " + receiver + "-------------------");
            System.out.println("Type 'exit chat' to return to the chat menu");
            ArrayList<Message> chat = client.getMessages(receiver);
            if (chat == null || chat.isEmpty()) {
                System.out.println("No messages available");
            } else {
                for (Message message : chat) {
                    //show all the messages till the last one that has been received

                    if (message.getSender().equals(client.getUsername()))
                        System.out.println(message.getSenderColor().getColorValueANSII() + "You: " + UsefulData.RESET + message.getContent());
                    else
                        System.out.println(message.getSenderColor().getColorValueANSII() + message.getSender() + UsefulData.RESET + ": " + message.getContent());
                }
            }

            inputHandler(scanner.nextLine());
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            System.err.println("An error occurred: " + e.getMessage());
            throw new RemoteException();
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
    }

    /**
     * Defines how to handle the user's input in a specific way for this class
     *
     * @param input String representing the user's input
     * @throws IOException            when an I/O operation fails
     * @throws ClassNotFoundException when the class loaded can not be found
     * @throws InterruptedException   when the thread running is interrupted
     */
    public void inputHandler(String input) throws IOException, ClassNotFoundException, InterruptedException {
        while (!input.equals("exit chat")) {
            client.sendMessage(receiver, input);
            input = scanner.nextLine().trim();
        }
        client.setCurrentState(null);
    }

    @Override
    public void promptForInput() {

    }

    @Override
    public String toString() {
        return "PrivateChatState";
    }

}
