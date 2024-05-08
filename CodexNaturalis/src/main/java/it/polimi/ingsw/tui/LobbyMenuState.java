package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.Client.RMIClient;

import java.rmi.RemoteException;
import java.util.Scanner;

public class LobbyMenuState implements ClientState {


    VirtualView client;
    private final Scanner scanner;

    public LobbyMenuState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;

    }

    @Override
    public void promptForInput() {
        System.out.print("Enter your choice (1-3): ");
    }

    @Override
    public void display() {
        System.out.println("\n---------- Lobby Menu ----------");
        System.out.println("Please select an option:");
        System.out.println("1. Create a new game 🆕");
        System.out.println("2. Join a game 🚪");
        System.out.println("3. Exit 🚪");
        System.out.println("--------------------------------\n");
    }

    @Override
    public void inputHandler(int input) {
        switch (input) {
            case 1:
                try {
                    client.setCurrentState(new CreateGameState(client, scanner));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                try {
                    client.setCurrentState(new JoinGameMenuState(client, scanner));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                try {
                    client.close();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.print("Invalid input");
                display();
        }
    }
}
