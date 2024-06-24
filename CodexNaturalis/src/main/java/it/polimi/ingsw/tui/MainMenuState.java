package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.Remote;
import java.util.Scanner;

/**
 * This client state is used when the user enters the game and needs to choose the username
 */
public class MainMenuState implements ClientStateTUI, Remote {

    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the BaseClient class that can call the methods in the server
     * @param scanner is a reference to the Scanner class that handles and returns the input of the user
     */
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

    /**
     * Get the input and check if it is a valid username. If so, it saves it
     *
     * @throws IOException          when an I/O operation fails
     * @throws InterruptedException when the thread running is interrupted
     */
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

    @Override
    public String toString() {
        return "MainMenuState";
    }


}
