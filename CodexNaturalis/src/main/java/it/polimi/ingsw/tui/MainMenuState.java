package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.Remote;
import java.util.Scanner;

public class MainMenuState implements ClientStateTUI, Remote {

    BaseClient client;
    private final Scanner scanner;

    public MainMenuState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }


    @Override
    public void promptForInput() {
    }

    @Override
    public void display() {
        System.out.println("""
                 ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗                             \s
                ██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝                             \s
                ██║     ██║   ██║██║  ██║█████╗   ╚███╔╝                              \s
                ██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗                              \s
                ╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗                             \s
                 ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝                             \s
                                                                                      \s
                ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗
                ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝
                ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗
                ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║
                ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║
                ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝""");
        System.out.println("\nWelcome to Codex Naturalis!");
        System.out.println("⚔️  _________________________________  ⚔️");
        System.out.println("|                                     |");
        System.out.println("|   Please select an option:          |");
        System.out.println("|   1. Play 🎮                        |");
        System.out.println("|_____________________________________|\n");
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {

        if (input == 1)
            requestUsername();
        else
            System.out.print("Invalid input");
    }


    private void requestUsername() throws IOException, InterruptedException {
        String username;
        do {
            System.out.println("Enter your username (cannot be empty):");
            username = scanner.nextLine();
        } while (username.isEmpty());

        if (client.checkUsername(username)) {
            client.setUsername(username);
            client.setCurrentState(new LobbyMenuState(client, scanner));
        } else {
            System.out.println("Username already taken");
            requestUsername();
        }
    }

    public String toString() {
        return "MainMenuState";
    }


}
