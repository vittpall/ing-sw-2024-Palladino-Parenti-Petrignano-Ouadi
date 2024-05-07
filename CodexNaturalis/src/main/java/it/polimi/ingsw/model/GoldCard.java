package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * This class represents a GoldCard in the game.
 * It extends the GameCard class and has an additional attribute, requirements.
 * The requirements attribute represents the resources required to play this card.
 */
public class GoldCard extends GameCard {

    // The resources required to obtain this card
    private final EnumMap<Resource, Integer> requirements;

    /**
     * This is the constructor for the GoldCard class.
     * It initializes the cardResourceFront, backSideResource, pointType, points, frontImagePath, backImagePath from the superclass,
     * and also initializes the requirements attribute.
     *
     * @param frontSideResources The resources on the front of the card
     * @param backSideResource   The resource on the back of the card
     * @param pointType          The type that will be used to calculate points
     * @param points             The number of points the card gives
     * @param frontImagePath     The path to the image for the front of the card
     * @param backImagePath      The path to the image for the back of the card
     * @param requirements       The resources required to play this card
     */
    @JsonCreator
    public GoldCard(
            @JsonProperty("backSideResource") Resource backSideResource,
            @JsonProperty("frontImagePath") String frontImagePath,
            @JsonProperty("backImagePath") String backImagePath,
            @JsonProperty("points") int points,
            @JsonProperty("pointType") PointType pointType,
            @JsonProperty("frontSideResources") ArrayList<Resource> frontSideResources,
            @JsonProperty("corners") Corner[] corners,
            @JsonProperty("requirements") EnumMap<Resource, Integer> requirements) {
        super(backSideResource, frontImagePath, backImagePath, points, pointType, frontSideResources, corners);
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

    @Override
    protected void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {
        for (int i = 0; i < context.getCardHeight() - 2; i++) {
            if (faceDown) {
                if (i == context.getCardHeight() / 2 - 1) {
                    String pointsDetail = "Pts: " + getPoints() + " " + getPointType();
                    System.out.println(colorBackground + " " + context.centerString(pointsDetail, context.getCardWidth() - 2) + " " + CardPrinter.RESET);
                    // Handling requirements, format them to show only the first character of each resource name
                    if (!getRequirements().isEmpty()) {
                        StringBuilder reqDetails = new StringBuilder("Req: ");
                        getRequirements().forEach((resource, quantity) -> {
                            String resourceInitial = resource.name().substring(0, 1); // Get first character of the resource name
                            reqDetails.append(resourceInitial).append("=").append(quantity).append(", ");
                        });
                        // Remove the last comma and space
                        reqDetails.setLength(reqDetails.length() - 2);
                        System.out.println(colorBackground + " " + context.centerString(reqDetails.toString(), context.getCardWidth() - 2) + " " + CardPrinter.RESET);
                    }

                }
            } else {
                System.out.println(colorBackground + context.repeat(context.getCardWidth()) + CardPrinter.RESET);
            }
        }
    }
}