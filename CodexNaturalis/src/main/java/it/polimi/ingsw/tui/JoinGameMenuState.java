package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.Client.RMIClient;
import java.util.HashMap;
import java.rmi.RemoteException;
import java.util.Scanner;

public class JoinGameMenuState implements ClientState{
    VirtualView client;
    Scanner scanner;
    //used to show the last option in the bullet list of the menu
    int lastOptionOutput = 1;

    public JoinGameMenuState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner=scanner;
    }
    @Override
    public void promptForInput() {
        System.out.print("Enter your choice : ");
    }

    @Override
    public void display() {

        System.out.println("\n---------- Join Game Menu ----------");
        System.out.println("These are the games to enter option:");

        try{
            HashMap<Integer, Game> games = client.getNotStartedGames();
            if(games.isEmpty()){
                System.out.println("No games available.\n1.Create new game 🆕");
                lastOptionOutput++;
            }else{
                System.out.println("Choose a game to enter 🚪:");
                for(int idGame:games.keySet()){
                    lastOptionOutput= idGame+1;
                    System.out.println(idGame+". This game has "+games.get(idGame).getnPlayer()+ " players and needs "+
                            (games.get(idGame).getnPlayer()-games.get(idGame).getPlayers().size())+" players to start");
                }
            }
            System.out.println(lastOptionOutput+". Exit 🚪");
        }catch(RemoteException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("--------------------------------\n");
    }
    @Override
    public void inputHandler(int input) {
        if(input == lastOptionOutput){
            System.exit(0);
        } else{
            try{
                if(client.getNotStartedGames().isEmpty() && input==1){
                    client.setCurrentState(new CreateGameState(client, scanner));
                    return;
                }
                if(client.getNotStartedGames().get(input)==null){
                    System.out.println("Invalid input");
                    return;
                }
                System.out.println("Waiting for the game to start...");
                client.joinGame(input, client.getUsername());
            }catch(RemoteException | InterruptedException ex){
                System.out.println("Error while joining the game");
            }
            //client.setCurrentState(new aitForPlayers());
        }
    }


}
