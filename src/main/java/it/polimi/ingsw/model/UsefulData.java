package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class contains useful data that is used in the game, and it is shared among the clients using the Singleton pattern
 */
public class UsefulData {
    public static final String RESET = "\033[0m";
    public static String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

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
