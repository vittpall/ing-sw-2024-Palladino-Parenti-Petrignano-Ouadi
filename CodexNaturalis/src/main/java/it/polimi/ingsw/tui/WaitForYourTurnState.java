package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class WaitForYourTurnState implements ClientState{
    VirtualView client;
    private final Scanner scanner;

    public WaitForYourTurnState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("|------Wait for your turn state------|");
        //mostrare magari il proprio desk e quello degli altri giocatori
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        //il giocatore schiaccia 1 se vuole aspettare il suo turno per poi continuare
        //schiaccia 2 se vuole abbandonare
        switch (input){
            case 1:
                client.waitForYourTurn();
                client.setCurrentState(new PlayCardState(client, scanner));
                break;
            case 2:
                client.setCurrentState(new ChatState(client, scanner, this));
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Wait for your turn");
        System.out.println("2. Chat");
    }
}
