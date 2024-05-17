package it.polimi.ingsw.model.enumeration;

public enum GameState {
    WAITING_FOR_PLAYERS("Waiting for players"), SETUP_GAME ("Setup game"),
    ROUNDS("Game rounds"), LASTROUND("Last round"), ENDGAME("End game");

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
     * Returns the value of the string representing the GameState
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
