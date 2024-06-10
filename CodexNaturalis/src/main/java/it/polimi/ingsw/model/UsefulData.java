package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class contains useful data that is used in the game, and it is shared among the clients using the Singleton pattern
 */
public class UsefulData {
    public final int MAX_PLAYERS = 4;
    public final int MIN_PLAYERS = 2;
    public final int MAX_CARDS = 3;
    //...
    public ArrayList<String> quitableStates = new ArrayList<>();
    private static UsefulData instance = null;

    private UsefulData() {
        //in those states if the client select exit he will quit the game
        quitableStates.add("LobbyMenuState");
        quitableStates.add("MainMenuState");
        quitableStates.add("JoinGameMenuState");
        quitableStates.add("CreateGameState");
    }

    public static UsefulData getInstance() {
        if (instance == null) {
            instance = new UsefulData();
        }
        return instance;
    }
}
