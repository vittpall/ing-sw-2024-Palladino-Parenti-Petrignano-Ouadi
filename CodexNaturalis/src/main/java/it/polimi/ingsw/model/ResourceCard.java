package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a ResourceCard in the game.
 * It extends the GameCard class.
 * A ResourceCard does not have any additional attributes or methods.
 */
public class ResourceCard extends GameCard {

    /**
     * This is the constructor for the ResourceCard class.
     * It initializes the cardResourceFront, backSideResource, pointType, points, frontImagePath, backImagePath from the superclass.
     *
     * @param frontSideResources The resources on the front of the card
     * @param backSideResource   The resource on the back of the card
     * @param pointType          The type that will be used to calculate points
     * @param points             The number of points the card gives
     * @param frontImagePath     The path to the image for the front of the card
     * @param backImagePath      The path to the image for the back of the card
     * @param corners            The corners of the card
     */
    @JsonCreator
    public ResourceCard(
            @JsonProperty("backSideResource") Resource backSideResource,
            @JsonProperty("frontImagePath") String frontImagePath,
            @JsonProperty("backImagePath") String backImagePath,
            @JsonProperty("points") int points,
            @JsonProperty("pointType") PointType pointType,
            @JsonProperty("frontSideResources") ArrayList<Resource> frontSideResources,
            @JsonProperty("corners") Corner[] corners) {
        super(backSideResource, frontImagePath, backImagePath, points, pointType, frontSideResources, corners);
    }

    @Override
    public void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {

        for (int i = 0; i < context.getCardHeight() - 2; i++) {
            String printString = colorBackground + context.repeat(context.getCardWidth()) + CardPrinter.RESET;
            if (!faceDown && i == context.getCardHeight() / 2 - 1 && getPoints() != 0) {
                String pointsDetail = "Pts: " + getPoints();
                printString = colorBackground + " " + context.centerString(pointsDetail, context.getCardWidth() - 2) + " " + CardPrinter.RESET;
            }
            System.out.println(printString);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceCard that = (ResourceCard) o;
        return getPoints() == that.getPoints() &&
                Objects.equals(getBackSideResource(), that.getBackSideResource()) &&
                Objects.equals(getImageFrontPath(), that.getImageFrontPath()) &&
                Objects.equals(getImageBackPath(), that.getImageBackPath()) &&
                getPointType() == that.getPointType() &&
                Objects.equals(getFrontSideResources(), that.getFrontSideResources()) &&
                Arrays.equals(getCorners(), that.getCorners());
    }


}