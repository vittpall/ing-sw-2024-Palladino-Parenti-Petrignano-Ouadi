package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.rmi.RMIClient;

public class LobbyMenuState implements ClientState {


    RMIClient client;

    public LobbyMenuState(RMIClient client) {
        this.client = client;
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
                //  client.setCurrentState(new CreateGameMenuState(client));
                break;
            case 2:
                // client.setCurrentState(new JoinGameMenuState(client));
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.print("Invalid input");
                display();
        }
    }
}
