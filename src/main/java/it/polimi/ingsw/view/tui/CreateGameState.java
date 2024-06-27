package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * This client state is used when the user wants to create a new game.
 * In this state the user can choose the number of players of the new game
 */
public class CreateGameState implements ClientStateTUI {

    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the BaseClient class that can call the methods in the server
     * @param scanner is a reference to the Scanner class that handles and returns the input of the user
     */
    public CreateGameState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void promptForInput() {

    }

    @Override
    public void display() {
        System.out.println("\n---------- Create new game ----------");
        System.out.println("|   Please select an option:          |");
        System.out.println("|   1. Create Game 🎮                 |");
    }

    @Override
    public void inputHandler(int input) throws RemoteException, InterruptedException {

        if (input == 1) {
            createGame();
        } else {
            System.out.print("Invalid input");
            display();
        }
    }

    /**
     * Private method used to send a notification to create a game with the number of players chosen by the client
     *
     * @throws RemoteException when a communication-related problem occurs
     */
    private void createGame() throws RemoteException {
        System.out.println("Enter the number of players (2-4):");

        int nPlayers = 0;
        boolean validInput = false;

        while (!validInput) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number between 2 and 4:");
                continue;
            }

            try {
                nPlayers = Integer.parseInt(input);
                if (nPlayers < 2 || nPlayers > 4) {
                    System.out.println("Invalid number of players. Please enter a number between 2 and 4:");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number between 2 and 4:");
            }
        }

        try {
            client.createGame(client.getUsername(), nPlayers);
            client.setCurrentState(new WaitingForPlayersState(client, scanner));
        } catch (InterruptedException | RemoteException e) {
            throw new RemoteException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "CreateGameState";
    }


}
