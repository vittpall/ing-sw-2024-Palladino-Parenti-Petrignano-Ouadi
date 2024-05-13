package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class WaitForYourLastTurnState implements ClientState{
    VirtualView client;
    private final Scanner scanner;

    public WaitForYourLastTurnState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("|------Wait for your turn------|");
        try{
            String playerWhoStopped = client.getUsernamePlayerThatStoppedTheGame();
            System.out.println("The player "+playerWhoStopped+" has reached 20 points!");
        }catch(RemoteException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("The next one will be your last turn\n");
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        switch(input){
            case 1:
                client.waitForYourTurn();
                client.setCurrentState(new PlayLastCardState(client, scanner));
                break;
            case 2:
                client.setCurrentState(new ChatState(client, scanner));
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
