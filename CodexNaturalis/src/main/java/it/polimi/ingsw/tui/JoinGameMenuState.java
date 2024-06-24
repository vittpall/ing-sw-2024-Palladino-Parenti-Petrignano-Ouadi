package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This client state is used when the user wants to enter an existing game
 */
public class JoinGameMenuState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;
    private final HashMap<Integer, Integer> availableGamesToShow;

    /**
     * Constructor
     *
     * @param client  is a reference to the client class that can call the methods in the server
     * @param scanner is a reference to the class that handles and returns the input of the user
     */
    public JoinGameMenuState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
        availableGamesToShow = new HashMap<>();
    }

    @Override
    public void promptForInput() {
    }

    @Override
    public void display() {

        int positionGameAvailable = 1;
        System.out.println("\n---------- Join Game Menu ----------");
        System.out.println("These are the games to enter option:");

        try {
            ArrayList<Integer> gameIds = client.getNotStartedGames();
            if (gameIds == null || gameIds.isEmpty()) {
                System.out.println("No games available.\n1.Create new game 🆕");
            } else {
                System.out.println("Choose a game to enter 🚪:");
                for (int idGame : gameIds) {
                    availableGamesToShow.put(positionGameAvailable, idGame);
                    System.out.println(positionGameAvailable + ". This game has " + client.getnPlayer(idGame) + " players and needs " +
                            (client.getnPlayer(idGame) - client.getPlayers(idGame).size()) + " players to start");
                    positionGameAvailable++;
                }
            }
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("--------------------------------\n");
    }

    @Override
    public void inputHandler(int input) throws RemoteException {
        try {
            if (client.getNotStartedGames().isEmpty() && input == 1) {
                client.setCurrentState(new CreateGameState(client, scanner));
                return;
            }
            if (!(client.getNotStartedGames().contains(availableGamesToShow.get(input)))) {
                System.out.println("Invalid input");
                return;
            }
            client.joinGame(availableGamesToShow.get(input), client.getUsername());
            client.setCurrentState(new WaitingForPlayersState(client, scanner));
        } catch (RemoteException ex) {
            throw new RemoteException();
        } catch (InterruptedException | IOException ex) {
            System.out.println("Error while joining the game");
        }
    }

    @Override
    public String toString() {
        return "JoinGameMenuState";
    }

    /**
     * Notification method
     * It prints the updated list of the available games
     *
     * @param availableGames HashMap that describes the available games, the total number of players and of the needed ones
     */
    public void refresh(HashMap<Integer, Integer[]> availableGames) {
        int positionGameAvailable = 1;
        System.out.println("These are the games to enter option:");
        if (availableGames == null || availableGames.isEmpty()) {
            System.out.println("No games available.\n1.Create new game 🆕");
        } else {
            System.out.println("Choose a game to enter 🚪:");
            for (int idGame : availableGames.keySet()) {
                availableGamesToShow.put(positionGameAvailable, idGame);
                System.out.println(idGame + ". This game has " + availableGames.get(idGame)[0] + " players and needs " +
                        availableGames.get(idGame)[1] + " players to start");
            }
        }
    }

}



