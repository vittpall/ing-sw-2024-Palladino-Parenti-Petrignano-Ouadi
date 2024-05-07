package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.util.ArrayList;
import java.util.Arrays;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResourceCard.class, name = "resource"),
        @JsonSubTypes.Type(value = GoldCard.class, name = "gold"),
        @JsonSubTypes.Type(value = StarterCard.class, name = "starter")
})
abstract public class GameCard extends Card {

    private final ArrayList<Resource> frontSideResources;
    private final Resource backSideResource;
    private boolean playedFaceDown;
    private final PointType pointType;

    private final Corner[] corners;

    public GameCard(Resource backSideResource, String frontImagePath, String backImagePath, int points, PointType pointType, ArrayList<Resource> frontSideResources, Corner[] corners) {
        super(points, frontImagePath, backImagePath);
        this.frontSideResources = frontSideResources;
        this.backSideResource = backSideResource;
        this.corners = new Corner[8];
        for (int i = 0; i < corners.length; i++) {
            this.corners[i] = new Corner(corners[i]);
        }
        this.playedFaceDown = false;
        this.pointType = pointType;
    }

    public void setPlayedFaceDown(boolean playedFaceDown) {
        this.playedFaceDown = playedFaceDown;
    }

    public Resource getBackSideResource() {
        return backSideResource;
    }

    public PointType getPointType() {
        return pointType;
    }

    public boolean isPlayedFaceDown() {
        return playedFaceDown;
    }

    public ArrayList<Resource> getFrontSideResources() {
        return frontSideResources;
    }

    public void flipCard() {
        playedFaceDown = !playedFaceDown;
    }


    public Corner getCorner(int index) {
        return this.corners[index];
    }

    public Corner[] getCorners() {
        return Arrays.copyOf(corners, corners.length);
    }

    @Override
    public void print(PrintContext context) {
        Corner[] corners = getCorners();
        CardPrinter.Color colorBackground = context.chooseColor(getBackSideResource());
        context.printColorBorder(corners[0], corners[1], colorBackground);

        printCardDetails(context, colorBackground);

        context.printColorBorder(corners[2], corners[3], colorBackground);
        System.out.println(); // For spacing between cards
    }

    protected abstract void printCardDetails(PrintContext context, CardPrinter.Color colorBackground);

}