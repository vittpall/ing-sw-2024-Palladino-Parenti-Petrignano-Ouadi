package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.util.Scanner;

public class WaitForYourTurnState implements ClientState {
    VirtualView client;
    private final Scanner scanner;

    public WaitForYourTurnState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("You are currently waiting for your turn. Please wait for the other players to finish their turn.");
        System.out.println("You can chat with the other players while you wait.");
        System.out.println("1. Chat");
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        if (input == 1) {
            client.setCurrentState(new ChatState(client, this));
        } else {
            System.out.println("Invalid input");
        }
    }

    @Override
    public void promptForInput() {
    }
}
