package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This client state is used when the user has to wait for other player to enter the game in order to start it
 */
public class WaitingForPlayersState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the BaseClient class that can call the methods in the server
     * @param scanner is a reference to the Scanner class that handles and returns the input of the user
     */
    public WaitingForPlayersState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        try {
            refresh(client.getPlayers(client.getIdGame()), client.getnPlayer(client.getIdGame()) - client.getPlayers(client.getIdGame()).size());
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Notification method
     * It prints the updated list of the Player's that entered the game
     *
     * @param players           is the ArrayList of the Player inside the game
     * @param nOfMissingPlayers Integer representing the number of missing players
     */
    public void refresh(ArrayList<Player> players, int nOfMissingPlayers) {
        System.out.println("\n⚔️  _________________________________________________  ⚔️");
        System.out.println("|   Current players:                                  |");
        int i = 0;
        for (Player player : players) {
            System.out.println("|   Player " + (i + 1) + " : " + player.getUsername() + "                                   |");
            i++;
        }
        if (nOfMissingPlayers == 0)
            System.out.println("|   Please select 1 to start the game.                |");
        else {
            System.out.println("|   Waiting for " + nOfMissingPlayers + " more players to join the game.      |");
            System.out.println("|   Please select 1 to start the game as soon as the  |");
            System.out.println("|   right number of players have joined.              |");
        }
        System.out.println("⚔️  _______________________________________________  ⚔️\n");
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        if (input == 1) {
            if (client.isGameStarted())
                client.setCurrentState(new ColorSelection(client, scanner));
            else
                System.out.println("The game has not started yet. Wait for the right number of players to join.");
        }
    }

    @Override
    public void promptForInput() {

    }

    @Override
    public String toString() {
        return "WaitingForPlayersState";
    }


}
