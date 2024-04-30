package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.rmi.RMIClient;
import java.util.HashMap;
import java.rmi.RemoteException;

public class JoinGameMenuState implements ClientState{
    RMIClient client;
    //used to show the last option in the bullet list of the menu
    int lastOptionOutput = 1;

    public JoinGameMenuState(RMIClient client) {
        this.client = client;
    }
    @Override
    public void promptForInput() {
        System.out.print("Enter your choice : ");
    }

    @Override
    public void display() {

        System.out.println("\n---------- Lobby Menu ----------");
        System.out.println("These are the games to enter option:");

        try{
            HashMap<Integer, Game> games = client.server.getNotStartedGames();
            if(games.isEmpty()){
                System.out.println("No games available.\n 1.Create new game 🆕");
                lastOptionOutput++;
            }else{
                System.out.println("Choose a game to enter 🚪:");
                for(int idGame:games.keySet()){
                    lastOptionOutput++;
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
                if(client.server.getNotStartedGames().isEmpty() && input==1){
                    //client.setCurrentState(new CreateGameMenuState(client));
                    return;
                }
                if(client.server.getNotStartedGames().get(input)==null){
                    System.out.println("Invalid input");
                    return;
                }

                client.server.joinGame(input, client.getUsername());
            }catch(RemoteException ex){
                System.out.println("Error while joining the game");
            }
            //client.setCurrentState(new aitForPlayers());
        }
    }


}
