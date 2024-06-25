package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This abstract class represents a generic game card.
 * It contains the resources on the front side and the back side of the card,
 * the points that the card gives if played, the type of the pattern that is used to calculate the points,
 * the corners of the card and a boolean that indicates if the card is played face down or not.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResourceCard.class, name = "resource"),
        @JsonSubTypes.Type(value = GoldCard.class, name = "gold"),
        @JsonSubTypes.Type(value = StarterCard.class, name = "starter")
})
public abstract class GameCard extends Card {

    private final ArrayList<Resource> backSideResources;
    private final Resource backSideResource;
    private boolean playedFaceDown;
    private final PointType pointType;

    private final Corner[] corners;

    /**
     * Constructor for GameCard class.
     *
     * @param backSideResource   resource on the back side of the card
     * @param frontImagePath     path to the image of the front of the card
     * @param backImagePath      path to the image of the back of the card
     * @param points             number of points that the card gives if played
     * @param pointType          type of the pattern that is used to calculate the points
     * @param backSideResources  resources on the back side of the card
     * @param corners            array of corners of the card
     */
    public GameCard(Resource backSideResource, String frontImagePath, String backImagePath, int points, PointType pointType, ArrayList<Resource> backSideResources, Corner[] corners) {
        super(points, frontImagePath, backImagePath);
        this.backSideResources = backSideResources;
        this.backSideResource = backSideResource;
        this.corners = new Corner[8];
        for (int i = 0; i < corners.length; i++) {
            this.corners[i] = new Corner(corners[i]);
        }
        this.playedFaceDown = false;
        this.pointType = pointType;
    }

    /**
     * Set the parameter playedFaceDown
     *
     * @param playedFaceDown true if the card is played face down, false otherwise
     */
    public void setPlayedFaceDown(boolean playedFaceDown) {
        this.playedFaceDown = playedFaceDown;
    }

    /**
     * @return the resource on the back side of the card
     */
    public Resource getBackSideResource() {
        return backSideResource;
    }

    /**
     * @return the type of the pattern that is used to calculate the points
     */
    public PointType getPointType() {
        return pointType;
    }

    /**
     * @return true if the card is played face down, false otherwise
     */
    public boolean isPlayedFaceDown() {
        return playedFaceDown;
    }

    /**
     * @return the resources on the back side of the card
     */
    public ArrayList<Resource> getBackSideResources() {
        return backSideResources;
    }

    /**
     * Flips the card
     */
    public void flipCard() {
        playedFaceDown = !playedFaceDown;
    }

    /**
     * @param index index of the corner requested
     * @return the corner of the card at the specified index
     */
    public Corner getCorner(int index) {
        return this.corners[index];
    }

    /**
     * @return the array of corners of the card
     */
    public Corner[] getCorners() {
        return Arrays.copyOf(corners, corners.length);
    }


    @Override
    public void print(PrintContext context, boolean faceDown) {
        Corner[] corners = getCorners();
        CardPrinter.Color colorBackground = context.chooseColor(getBackSideResource());
        if (faceDown) {
            context.printColorBorder(corners[4], corners[5], colorBackground);
        } else {
            context.printColorBorder(corners[0], corners[1], colorBackground);
        }

        printCardDetails(context, colorBackground, faceDown);

        if (faceDown) {
            context.printColorBorder(corners[7], corners[6], colorBackground);
        } else {
            context.printColorBorder(corners[3], corners[2], colorBackground);

        }
        System.out.println(); // For spacing between cards

    }

    /**
     * Prints the card specified details
     *
     * @param context         print context
     * @param colorBackground background color of the card
     * @param faceDown        true if the card is face down, false otherwise
     */
    protected abstract void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown);

}