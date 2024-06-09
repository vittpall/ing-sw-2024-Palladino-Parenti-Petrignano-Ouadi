package it.polimi.ingsw.network.notifications;

import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;

import java.util.ArrayList;

public class TokenColorTakenNotification implements ServerNotification {

    String message;
    ArrayList<TokenColor> availableColors;
    public TokenColorTakenNotification(String message, ArrayList<TokenColor> availableColors) {
        this.message = message;
        this.availableColors = availableColors;
    }

    @Override
    public void notifyClient(BaseClient client) {
        client.onTokenColorSelected(message, availableColors);
    }
}
