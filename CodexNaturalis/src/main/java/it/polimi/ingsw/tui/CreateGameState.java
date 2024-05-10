package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

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
        System.out.println("Enter the number of players (cannot be empty):");
        int nPlayers;
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());

        nPlayers = Integer.parseInt(input);
        //TODO catch exception if input is not a number
        if (nPlayers < 2 || nPlayers > 4) {
            System.out.println("Invalid number of players");
            createGame();
        } else {
            try {
                System.out.println("Creating game and waiting for the players...");
                client.createGame(client.getUsername(), nPlayers);
                System.out.println("The game " + client.getIdGame() + " has started.\nYou are the player number " + client.getIdClientIntoGame() + "\n");
            } catch (InterruptedException | RemoteException e) {
                System.out.println("Error creating game. Please try again.");
            }
            //  client.setCurrentState(new InitializeObjectiveCardState(client, scanner));
            client.setCurrentState(new ColorSelection(client, scanner));
        }

    }
}
