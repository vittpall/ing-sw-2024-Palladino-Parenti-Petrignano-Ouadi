package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.rmi.RMIClient;

import java.rmi.RemoteException;
import java.util.Scanner;

 public class CreateGameState implements ClientState{
    RMIClient client;
    private final Scanner scanner;


    public CreateGameState(RMIClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }
    @Override
    public void promptForInput() {
        System.out.print("Enter your choice (1-2) : ");
    }
    @Override
    public void display() {
        System.out.println("\n---------- Create new game ----------");
        System.out.println("|   Please select an option:          |");
        System.out.println("|   1. Create Game 🎮                        |");
        System.out.println("|   2. Exit 🚪                        |");
    }
    @Override
    public void inputHandler(int input) throws RemoteException {

        switch (input) {
            case 1:
                createGame();
                break;
            case 2:
                System.exit(0);
                break;
            default:
                System.out.print("Invalid input");
                display();
        }
    }
    private void createGame() throws RemoteException{
        System.out.println("Enter the number of players (cannot be empty):");
        int nPlayers;
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());

        nPlayers = Integer.parseInt(input);
        //TODO catch exception if input is not a number
        if(nPlayers < 2 || nPlayers>4){
            System.out.println("Invalid number of players");
            createGame();
        }else{
            client.server.createGame(client.getUsername(), nPlayers);
            //client.setCurrentState(new WaitForPlayers(client));
        }

    }
}
