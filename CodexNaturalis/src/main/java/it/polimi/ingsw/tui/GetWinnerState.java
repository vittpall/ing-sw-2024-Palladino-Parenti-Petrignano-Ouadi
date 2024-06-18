package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class GetWinnerState implements ClientStateTUI {
    BaseClient client;
    private final Scanner scanner;

    public GetWinnerState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;

    }

    @Override
    public void display() throws RemoteException {
        System.out.println("\n---------- Game ended----------");
        try {
            String winnerUsername = client.getWinner();
            System.out.println("The winner is: " + winnerUsername);
            System.out.println("These are the points of every player :");
            client.getAllPlayers().stream().map(player -> "Player " + player.getUsername() + " has " + player.getPoints() + " points").forEach(System.out::println);
        } catch (RemoteException | InterruptedException e) {
            throw new RemoteException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        switch (input) {
            case 1:
                client.closeGame();
                break;
            case 2:
                client.close();
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

    public String toString() {
        return "GetWinnerState";
    }


}
