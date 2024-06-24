package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.BaseClient;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * This client state is used when the user wants to chat in TUI mode
 * It makes the user decide if he wants to chat with a single player or the whole group
 */
public class ChatState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the BaseClient class that can call the methods in the server
     * @param scanner is a reference to the Scanner class that handles and returns the input of the user
     */
    public ChatState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("Chat-------------------");
    }

    @Override
    public void inputHandler(int input) throws RemoteException {
        switch (input) {
            case 1:
                //send message to a player
                client.setCurrentState(new PrivateChatSelectingReceiverState(client, scanner, this));
                break;
            case 2:
                //send message to all players
                client.setCurrentState(new GlobalChatState(client, scanner, this));
                break;
            case 3:
                //return to the game
                client.setCurrentState(null);
                break;
            default:
                System.out.print("Invalid input");
        }
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Private chat");
        System.out.println("2. Global chat");
        System.out.println("3. Return to the game");
    }

}
