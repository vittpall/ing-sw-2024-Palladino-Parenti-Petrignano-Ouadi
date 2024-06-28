package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;

import java.util.ArrayList;

/**
 * Server notification used to notify the players of a specific game that a token color has been taken
 * and the updated available colors have changed
 */
public class TokenColorTakenNotification implements ServerNotification {

    private final String message;
    private final ArrayList<TokenColor> availableColors;

    /**
     * Constructor
     *
     * @param message         String representing a message that needs to be shown to the users
     * @param availableColors ArrayList representing the updated available TokenColor
     */
    public TokenColorTakenNotification(String message, ArrayList<TokenColor> availableColors) {
        this.message = message;
        this.availableColors = availableColors;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onTokenColorSelected(message, availableColors);
    }
}
