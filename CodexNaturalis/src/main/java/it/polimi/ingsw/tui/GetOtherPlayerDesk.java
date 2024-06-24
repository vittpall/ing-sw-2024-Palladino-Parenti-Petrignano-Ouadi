package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This client state is used when the player wants to see his or the other players' desks
 */
public class GetOtherPlayerDesk implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the BaseClient class that can call the methods in the server
     * @param scanner is a reference to the Scanner class that handles and returns the input of the user
     */
    public GetOtherPlayerDesk(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void promptForInput() {
    }

    @Override
    public void display() {
        System.out.println("\nSelect a Player: ");
        try {
            ArrayList<Player> allPlayers = client.getAllPlayers();
            for (int i = 0; i < allPlayers.size(); i++) {
                System.out.println((i + 1) + ". Player: " + allPlayers.get(i).getUsername());
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        Player selectedPlayer = getPlayerFromInput(input - 1);
        if (selectedPlayer != null) {
            CardPrinter printer = new CardPrinter();
            PlayerDesk playerDesk = selectedPlayer.getPlayerDesk();
            printer.printDesk(playerDesk.getDesk());
            client.setCurrentState(null);
        } else {
            System.out.println("Invalid input, please use a valid number:");
            promptForInput();
            inputHandler(scanner.nextInt());
        }
    }

    /**
     * Get the player that corresponds to the input sent
     *
     * @param input Integer that corresponds to the player chosen
     * @return the Player chosen
     */
    private Player getPlayerFromInput(int input) {
        try {
            if (input < client.getAllPlayers().size()) {
                return client.getAllPlayers().get(input);
            } else
                return null;
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "GetOtherPlayerDesk";
    }


}
