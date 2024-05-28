package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.BaseClient;

import java.util.Scanner;

public class LobbyMenuState implements ClientState {


    BaseClient client;
    private final Scanner scanner;

    public LobbyMenuState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;

    }

    @Override
    public void promptForInput() {
    }

    @Override
    public void display() {
        System.out.println("\n---------- Lobby Menu ----------");
        System.out.println("Please select an option:");
        System.out.println("1. Create a new game 🆕");
        System.out.println("2. Join a game 🚪");
        System.out.println("--------------------------------\n");
    }

    @Override
    public void inputHandler(int input) {
        switch (input) {
            case 1:
                client.setCurrentState(new CreateGameState(client, scanner));
                break;
            case 2:
                client.setCurrentState(new JoinGameMenuState(client, scanner));
                break;
            default:
                System.out.print("Invalid input");
        }
    }

    public String toString() {
        return "LobbyMenuState";
    }

    /**
     *
     */
    @Override
    public void refresh(String msg) {

    }
}
