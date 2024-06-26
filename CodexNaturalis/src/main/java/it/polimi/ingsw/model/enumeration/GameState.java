package it.polimi.ingsw.model.enumeration;

/**
 * This numeration contains all the possible states of the game
 */

public enum GameState {
    WAITING_FOR_PLAYERS("Waiting for players"), SETUP_GAME ("Setup game"),
    ROUNDS("Game rounds"), FINISHING_ROUND_BEFORE_LAST("Finishing round before last"),
    NO_CARDS_LEFT("No cards left"), LAST_ROUND("Last round"), ENDGAME("End game");

    private final String text;

    /**
     * Constructor
     *
     * @param text is the representation the state of the game
     */

    GameState(String text)
    {
        this.text = text;
    }

    /**
     * @return the value of the string representing the GameState
     */
    public String get()
    {
        return text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
