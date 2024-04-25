package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

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
        this.corners = corners;
        this.playedFaceDown = false;
        this.pointType = pointType;
    }

    public void setPlayedFaceDown(boolean playedFaceDown) {
        this.playedFaceDown = playedFaceDown;
    }

    public Resource getbackSideResource() {
        return backSideResource;
    }

    public PointType getPointType() {
        return pointType;
    }

    public boolean isPlayedFaceDown() {
        return playedFaceDown;
    }

    public ArrayList<Resource> getfrontSideResources() {
        return frontSideResources;
    }

    public void flipCard() {
        playedFaceDown = !playedFaceDown;
    }


    public Corner getCorner(int index) {
        return this.corners[index];
    }

    public Corner[] getCorners() {
        Corner[] corners;
        corners = this.corners;
        return corners;
    }
}