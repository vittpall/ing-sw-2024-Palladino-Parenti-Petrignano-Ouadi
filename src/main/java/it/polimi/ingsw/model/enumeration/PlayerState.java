package it.polimi.ingsw.model.enumeration;

/**
 * This enumeration contains all the possible states of the player
 */
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
     * @return the value of the string representing the PlayerState
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
