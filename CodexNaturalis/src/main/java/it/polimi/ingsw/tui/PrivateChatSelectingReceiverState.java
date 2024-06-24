package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This client state is used to choose with which client the user wants to chat
 */
public class PrivateChatSelectingReceiverState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;
    private int finalOption;
    private ArrayList<Player> availablePlayers;
    private final ChatState returnState;

    /**
     * Constructor
     *
     * @param client      is a reference to the BaseClient class that can call the methods in the server
     * @param scanner     is a reference to the Scanner class that handles and returns the input of the user
     * @param returnState is a reference to the ChatState class that created the instance
     */
    public PrivateChatSelectingReceiverState(BaseClient client, Scanner scanner, ChatState returnState) {
        this.client = client;
        this.scanner = scanner;
        this.finalOption = 0;
        this.returnState = returnState;
        this.availablePlayers = new ArrayList<>();
    }

    @Override
    public void display() {
        try {
            System.out.println("Private chat-------------------");
            System.out.println("Players to chat with: ---------");

            if (client.getAllPlayers() == null) {
                System.out.println("No players available");
            } else {
                this.finalOption++;
                availablePlayers = new ArrayList<>(client.getAllPlayers());
                removePlayerFromList(availablePlayers);

                for (int i = 0; i < availablePlayers.size(); i++) {
                    this.finalOption += i;
                    Player player = availablePlayers.get(i);
                    System.out.println(this.finalOption + ") " + player.getUsername());
                }
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Private method that removes the current player from the list of available players
     *
     * @param availablePlayers ArrayList of all the available players
     * @throws RemoteException when a communication-related problem occurs
     */
    private void removePlayerFromList(ArrayList<Player> availablePlayers) throws RemoteException {
        for (int i = 0; i < availablePlayers.size(); i++) {
            if (availablePlayers.get(i).getUsername().equals(client.getUsername())) {
                availablePlayers.remove(i);
            }
        }
    }

    @Override
    public void inputHandler(int input) {

        try {

            if (input == this.finalOption + 1) {
                client.setCurrentState(returnState);
            } else {
                if (client.getAllPlayers() != null) {
                    if (input > client.getAllPlayers().size()) {
                        System.out.println("Invalid input");
                        inputHandler(scanner.nextInt());
                    } else {
                        client.setCurrentState(new PrivateChatState(client, scanner, availablePlayers.get(input - 1).getUsername()));
                    }
                } else {
                    System.out.println("Invalid input");
                    inputHandler(scanner.nextInt());
                }
            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void promptForInput() {
        System.out.println(this.finalOption + 1 + ")Exit chat");
    }

    @Override
    public String toString() {
        return "PrivateChatSelectingReceiverState";
    }

}
