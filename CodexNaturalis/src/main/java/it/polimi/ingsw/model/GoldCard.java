package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * This class represents a GoldCard in the game.
 * It extends the GameCard class and has an additional attribute, requirements.
 * The requirements attribute represents the resources required to play this card.
 */
class GoldCard extends GameCard {

    // The resources required to obtain this card
    private final EnumMap<Resource, Integer> requirements;

    /**
     * This is the constructor for the GoldCard class.
     * It initializes the cardResourceFront, cardResourceBack, pointType, points, imageFrontPath, imageBackPath from the superclass,
     * and also initializes the requirements attribute.
     *
     * @param cardResourcesFront The resources on the front of the card
     * @param cardResourceBack   The resource on the back of the card
     * @param pointType          The type that will be used to calculate points
     * @param points             The number of points the card gives
     * @param imageFrontPath     The path to the image for the front of the card
     * @param imageBackPath      The path to the image for the back of the card
     * @param requirements       The resources required to play this card
     */
    public GoldCard(ArrayList<Resource> cardResourcesFront, Resource cardResourceBack, PointType pointType, int points, String imageFrontPath, String imageBackPath, EnumMap<Resource, Integer> requirements) {
        super(cardResourcesFront, cardResourceBack, pointType, points, imageFrontPath, imageBackPath);
        this.requirements = requirements != null ? requirements : new EnumMap<>(Resource.class);
    }

    /**
     * This method is used to get the requirements of the GoldCard.
     *
     * @return ArrayList<Resource> This returns the requirements of the GoldCard.
     */
    public EnumMap<Resource, Integer> getRequirements() {
        return requirements;
    }
}