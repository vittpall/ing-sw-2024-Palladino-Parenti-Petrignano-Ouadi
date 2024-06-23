package it.polimi.ingsw.model.enumeration;
/**
 * Enumeration of the possible actions that can be requested by the client
 */
public enum RequestedActions {
    DRAW("Draw"), PLAY_CARD("Game rounds"), SHOW_DESKS("Last round"),
    SHOW_OBJ_CARDS("End game"), SHOW_POINTS("Show points"), CHAT("Chat"), SHOW_WINNER("Show winner");

    private final String text;

    /**
     * Constructor
     *
     * @param text is the representation the state of the game
     */

    RequestedActions(String text)
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
