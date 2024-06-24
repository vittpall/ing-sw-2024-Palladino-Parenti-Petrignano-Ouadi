package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.BaseClient;

import java.util.Scanner;

/**
 * This client state is used when the user has to choose if he wants to create a new game or enter an existing one
 */
public class LobbyMenuState implements ClientStateTUI {

    BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the client class that can call the methods in the server
     * @param scanner is a reference to the class that handles and returns the input of the user
     */
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

    @Override
    public String toString() {
        return "LobbyMenuState";
    }

}
