package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class GetWinnerState implements ClientState {
    VirtualView client;
    private final Scanner scanner;

    public GetWinnerState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;

    }

    @Override
    public void display() {
        System.out.println("\n---------- Game ended----------");
        System.out.println("Wait for everyone to finish their turn. The winner will be displayed soon");
        try {
            String winnerUsername = client.getWinner();
            System.out.println("The winner is: " + winnerUsername);
            System.out.println("These are the points of every player :");
            client.getAllPlayers(client.getIdGame()).stream().map(player -> "Player " + player.getUsername() + " has " + player.getPoints() + " points").forEach(System.out::println);
        } catch (RemoteException | InterruptedException e) {
            System.out.println("Error while getting the winner");
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        switch (input) {
            case 1:
                client.closeGame();
                client.setCurrentState(new LobbyMenuState(client, scanner));
                break;
            case 2:
                try {
                    client.close();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            case 3:
                client.setCurrentState(new ChatState(client, scanner));
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    @Override
    public void promptForInput() {
        System.out.println("Choose an option:");
        System.out.println("1. Start a new game");
        System.out.println("2. Exit");
        System.out.println("3. Chat");

    }
}
