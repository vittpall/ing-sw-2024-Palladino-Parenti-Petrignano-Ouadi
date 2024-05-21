package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CreateGameState implements ClientState {

    VirtualView client;
    private final Scanner scanner;


    public CreateGameState(VirtualView client, Scanner scanner) {
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
    public void inputHandler(int input) throws RemoteException {

        if (input == 1) {
            createGame();
        } else {
            System.out.print("Invalid input");
            display();
        }
    }

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
            System.out.println("Error creating game. Please try again.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
