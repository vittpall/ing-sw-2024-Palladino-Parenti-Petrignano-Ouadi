package it.polimi.ingsw.tui;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainMenuState implements ClientState, Remote {

    VirtualView client;
    private final Scanner scanner;

    public MainMenuState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }


    @Override
    public void promptForInput() {
        System.out.print("Enter your choice (1 or 2): ");
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
        System.out.println("|   2. Exit 🚪                        |");
        System.out.println("|_____________________________________|\n");
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

        switch (input) {
            case 1:
                requestUsername();
                break;
            case 2:
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


    private void requestUsername() throws IOException, ClassNotFoundException, InterruptedException {
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
}
