package it.polimi.ingsw.tui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.model.UsefulData;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class PrivateChatState implements ClientStateTUI {
    private BaseClient client;
    private final Scanner scanner;
    private final String receiver;
    private int lastMessageReceived;
    private ClientState returnState;

    public PrivateChatState(BaseClient client, Scanner scanner, String receiver, ChatState returnState) {
        this.client = client;
        this.scanner = scanner;
        this.receiver = receiver;
        this.lastMessageReceived = 0;
        this.returnState = new ChatState(client, scanner);
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public void display() throws RemoteException {
        try {
            System.out.println("Private chat with " + receiver);
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

    public String toString() {
        return "PrivateChatState";
    }


}
