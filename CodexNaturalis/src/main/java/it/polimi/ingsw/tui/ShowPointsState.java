package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ShowPointsState implements ClientStateTUI {
    BaseClient client;
    private final Scanner scanner;

    public ShowPointsState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }
    @Override
    public void display() {
        System.out.println("|-------- Show Points --------|");

        try{
            System.out.println("|---- Provisional Ranking:----|");
            ArrayList<Player> allPlayers = client.getAllPlayers();
            for (Player player : allPlayers) {
                int playerScore = player.getPoints();
                System.out.println("Player: " + player.getUsername() + " | Score: " + playerScore);
            }
            System.out.println("--------------------------------");
        }catch (IOException | InterruptedException e){
            System.out.println("Error while getting the points");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException{
        if(input!=1)
            System.out.println("Invalid input");
        client.setCurrentState(null);
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Return to main menu");
    }

    public String toString() {
        return "ShowPointsState";
    }

    
   
    public void refresh(HashMap<String, Integer> playersPoints){
        System.out.println("|---- Provisional Ranking:----|");
        for (String username : playersPoints.keySet()) {
            System.out.println("Player: " + username + " | Score: " + playersPoints.get(username));
        }
        System.out.println("--------------------------------");
    }
}
