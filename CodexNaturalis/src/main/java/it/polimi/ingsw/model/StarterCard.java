package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

/**
 * This class represents a StarterCard in the game.
 * It extends the GameCard class.
 * A StarterCard does not have any additional attributes or methods.
 */
class StarterCard extends GameCard {

    /**
     * This is the constructor for the StarterCard class.
     * It initializes the cardResourceFront, cardResourceBack, pointType, points, imageFrontPath, imageBackPath from the superclass.
     *
     * @param cardResourcesFront The resources on the front of the card
     * @param cardResourceBack The resource on the back of the card
     * @param pointType The type that will be used to calculate points
     * @param points The number of points the card gives
     * @param imageFrontPath The path to the image for the front of the card
     * @param imageBackPath The path to the image for the back of the card
     */
    public StarterCard(ArrayList<Resource> cardResourcesFront, Resource cardResourceBack, PointType pointType, int points, String imageFrontPath, String imageBackPath) {
        super(cardResourcesFront, cardResourceBack, pointType, points, imageFrontPath, imageBackPath);
    }
}