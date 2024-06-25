package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.util.ArrayList;

/**
 * This class represents a StarterCard in the game.
 * It extends the GameCard class.
 * A StarterCard does not have any additional attributes or methods.
 */
public class StarterCard extends GameCard {

    /**
     * This is the constructor for the StarterCard class.
     * It initializes the cardResourceFront, backSideResource, pointType, points, frontImagePath, backImagePath from the superclass.
     *
     * @param backSideResource   The resource on the back of the card
     * @param frontImagePath     The path to the image for the front of the card
     * @param backImagePath      The path to the image for the back of the card
     * @param points             The number of points the card gives
     * @param pointType          The type that will be used to calculate points
     * @param backSideResources The resources on the back of the card
     * @param corners            The corners of the card
     */
    public StarterCard(
            @JsonProperty("backSideResource") Resource backSideResource,
            @JsonProperty("frontImagePath") String frontImagePath,
            @JsonProperty("backImagePath") String backImagePath,
            @JsonProperty("points") int points,
            @JsonProperty("pointType") PointType pointType,
            @JsonProperty("backSideResources") ArrayList<Resource> backSideResources,
            @JsonProperty("corners") Corner[] corners) {
        super(backSideResource, frontImagePath, backImagePath, points, pointType, backSideResources, corners);
    }

    @Override
    protected void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {
        ArrayList<Resource> resources = new ArrayList<>();
        if (faceDown) {
            resources = getBackSideResources();
        }
        int y = 1;
        for (Resource resource : resources) {
            if (y >= context.getCardHeight()) break; // Prevents writing outside the card bounds
            String lineContent = resource.toString(); // Prepare line content
            y++;
            context.printCenteredLine(lineContent, colorBackground);
        }
        while (y <= context.getCardHeight() - 2) {
            y++;
            context.printCenteredLine("", colorBackground);
        }
    }


}