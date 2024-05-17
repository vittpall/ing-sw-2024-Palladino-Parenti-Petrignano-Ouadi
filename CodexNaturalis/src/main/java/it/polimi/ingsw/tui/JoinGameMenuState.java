package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class JoinGameMenuState implements ClientState {
    VirtualView client;
    Scanner scanner;

    public JoinGameMenuState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void promptForInput() {
    }

    @Override
    public void display() {

        System.out.println("\n---------- Join Game Menu ----------");
        System.out.println("These are the games to enter option:");

        try {
            ArrayList<Integer> gameIds = client.getNotStartedGames();
            if (gameIds == null || gameIds.isEmpty()) {
                System.out.println("No games available.\n1.Create new game 🆕");
            } else {
                System.out.println("Choose a game to enter 🚪:");
                for (int idGame : gameIds) {
                    System.out.println(idGame + ". This game has " + client.getnPlayer(idGame) + " players and needs " +
                            (client.getnPlayer(idGame) - client.getPlayers(idGame).size()) + " players to start");
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
            if (client.getNotStartedGames().get(input) == null) {
                System.out.println("Invalid input");
                return;
            }
            System.out.println("Waiting for the game to start...");
            client.joinGame(input, client.getUsername());
            client.setCurrentState(new ColorSelection(client, scanner));
            System.out.println("The game " + client.getIdGame() + " has started.\nYou are the player number " + client.getIdClientIntoGame() + "\n");
        } catch (InterruptedException | IOException ex) {
            System.out.println("Error while joining the game");
        }
    }
}



