package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.rmi.RMIClient;

public class MainMenuState implements ClientState {

    RMIClient client;

    public MainMenuState(RMIClient client) {
        this.client = client;
    }

    @Override
    public void display() {
        System.out.print("Welcome to Codex Naturalis!");
        System.out.print("Please select an option:");
        System.out.print("1. Play");
        System.out.print("2. Exit");
    }

    @Override
    public void inputHandler(int input) {

        switch (input) {
            case 1:
                client.setCurrentState(new LobbyMenuState(client));
                break;
            case 2:
                System.exit(0);
                break;
            default:
                System.out.print("Invalid input");
                display();
        }
    }
}
