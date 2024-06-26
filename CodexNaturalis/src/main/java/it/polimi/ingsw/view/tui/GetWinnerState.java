package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class GetWinnerState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the BaseClient class that can call the methods in the server
     * @param scanner is a reference to the Scanner class that handles and returns the input of the user
     */
    public GetWinnerState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;

    }

    @Override
    public void display() throws RemoteException {
        System.out.println("\n---------- Game ended----------");
        System.out.println("The winner is: " + client.getWinnerForTui());
        System.out.println("These are the points of every player :");
        client.getScoresForTui().forEach((k, v) -> System.out.println("Player " + k + " has " + v + " points"));
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        //TODO gestire la chiusura del gioco
        switch (input) {
            case 1:
                client.returnToLobby();
                client.setCurrentState(new LobbyMenuState(client, scanner));
                client.inputHandler();
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
        System.out.println("1. Return to lobby");
        System.out.println("2. Exit");

    }
    @Override
    public String toString() {
        return "GetWinnerState";
    }

}
