package it.polimi.ingsw.model.enumeration;

public enum PlayerState {
    SETUP_GAME("Setup game"),WAITING("Waiting"), PLAY_CARD("Playing card"),
    DRAW("Draw"),ENDGAME("End game");
    private final String text;

    /**
     * Constructor
     *
     * @param text is the representation the state of the player
     */

    PlayerState(String text)
    {
        this.text = text;
    }

    /**
     * Returns the value of the string representing the PlayerState
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
