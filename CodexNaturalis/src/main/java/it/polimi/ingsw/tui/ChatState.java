package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.rmi.Client.RMIClient;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ChatState implements ClientState {
    private RMIClient client;
    private final Scanner scanner;

    @Override
    public void display() {
        System.out.println("Chat-------------------");

    }

    public ChatState(RMIClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void inputHandler(int input) throws RemoteException {
        switch (input) {
            case 1:
                //send message
                //i need the user to pass in another state to chose the receiver
                //client.server.sendMessage(client.getUsername(), receiver, scanner.nextLine());
                break;
            case 2:
                //return to the game
                returnToGame();
                break;
            default:
                System.out.print("Invalid input");
                display();
        }
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Private chat");
        System.out.println("2. Global chat");
        System.out.println("3. Return to the game");
    }

    private void returnToGame() {
        //client.setCurrentState(new GamePlayState(client, scanner));
    }
}
