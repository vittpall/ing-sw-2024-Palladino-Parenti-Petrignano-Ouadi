package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This client state is used to show the points of every player in the game
 */
public class ShowPointsState implements ClientStateTUI {
    private final BaseClient client;

    /**
     * Constructor
     *
     * @param client is a reference to the BaseClient class that can call the methods in the server
     */
    public ShowPointsState(BaseClient client) {
        this.client = client;
    }

    @Override
    public void display() throws RemoteException {
        System.out.println("|-------- Show Points --------|");

        try {
            System.out.println("|---- Provisional Ranking:----|");
            ArrayList<Player> allPlayers = client.getAllPlayers();
            for (Player player : allPlayers) {
                int playerScore = player.getPoints();
                System.out.println("Player: " + player.getUsername() + " | Score: " + playerScore);
            }
            System.out.println("--------------------------------");
        } catch (RemoteException ex) {
            throw new RemoteException();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error while getting the points");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        if (input != 1)
            System.out.println("Invalid input");
        client.setCurrentState(null);
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Return to main menu");
    }

    @Override
    public String toString() {
        return "ShowPointsState";
    }

    /**
     * Notification method
     * It prints the updated ranking
     *
     * @param playersPoints HashMap that associates every Player to his points
     */
    public void refresh(HashMap<String, Integer> playersPoints) {
        System.out.println("|---- Provisional Ranking:----|");
        for (String username : playersPoints.keySet()) {
            System.out.println("Player: " + username + " | Score: " + playersPoints.get(username));
        }
        System.out.println("--------------------------------");
        System.out.println("1. Return to main menu");
    }
}
