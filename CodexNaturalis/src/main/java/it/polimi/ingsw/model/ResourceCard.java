package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.util.ArrayList;

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
            if (!faceDown) {
                if (i == context.getCardHeight() / 2 - 1) {
                    if (getPoints() != 0) {
                        String pointsDetail = "Pts: " + getPoints();
                        System.out.println(colorBackground + " " + context.centerString(pointsDetail, context.getCardWidth() - 2) + " " + CardPrinter.RESET);
                    } else {
                        System.out.println(colorBackground + context.centerString("", context.getCardWidth()) + CardPrinter.RESET);
                    }
                }
            } else {
                System.out.println(colorBackground + context.repeat(context.getCardWidth()) + CardPrinter.RESET);
            }
        }
    }


}