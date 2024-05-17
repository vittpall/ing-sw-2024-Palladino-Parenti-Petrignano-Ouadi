package it.polimi.ingsw.model.enumeration;

public enum RequestedActions {
    DRAW("Draw"), SET_OBJ_CARD ("Setup game"),
    PLAY_CARD("Game rounds"), SHOW_DESKS("Last round"), SHOW_OBJ_CARDS("End game"),
    SHOW_POINTS("Show points"), SET_TOKEN_COLOR("Set token color"),
    SET_STARTER_CARD("Set starter card"), CHAT("Chat");

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
