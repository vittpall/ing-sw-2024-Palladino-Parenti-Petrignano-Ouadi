package it.polimi.ingsw.tui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.network.BaseClient;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ChatState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;
    private ClientState returnState;

    @Override
    public void display() {
        System.out.println("Chat-------------------");
    }

    public ChatState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    public ChatState(BaseClient client, Scanner scanner, ClientState returnState) {
        this.client = client;
        this.scanner = scanner;
        this.returnState = returnState;
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

    
   

    private void sendGlobalMessage() {
        //client.server.sendMessage(client.getUsername(), null, scanner.nextLine());
    }

    private void returnToGame() {
        //client.setCurrentState(new GamePlayState(client, scanner));
    }
}
