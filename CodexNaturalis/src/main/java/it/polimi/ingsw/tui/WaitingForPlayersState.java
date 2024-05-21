package it.polimi.ingsw.tui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.util.Scanner;

public class WaitingForPlayersState implements ClientState{
    VirtualView client;
    private final Scanner scanner;

    public WaitingForPlayersState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }
    @Override
    public void display() {
        try{
            System.out.println("-------------------------------------------------------------------------------------");
            if (client.getnPlayer(client.getIdGame()) > client.getPlayers(client.getIdGame()).size()){
                System.out.println("Waiting for players to join the game...");
                System.out.println("You will be notified when the new players have joined the game.");
                System.out.println("Please select 1 to start the game as soon as the right number of players have joined.\n");

            }else
                System.out.println("Please select 1 to start the game.\n");

        }catch(IOException | InterruptedException  e){
           System.out.println(e.getMessage());
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        if(input == 1){
            if(client.isGameStarted())
                client.setCurrentState(new ColorSelection(client, scanner));
            else
                System.out.println("The game has not started yet. Wait for the right number of players to join.");
        }
    }

    @Override
    public void promptForInput() {

    }
}
