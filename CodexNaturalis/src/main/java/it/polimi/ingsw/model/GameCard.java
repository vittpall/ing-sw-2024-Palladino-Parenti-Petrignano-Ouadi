package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

abstract public class GameCard extends Card {

    private final ArrayList<Resource> cardResourcesFront;
    private final Resource cardResourceBack;
    private boolean playedFaceDown;
    private final String frontImagePath;
    private final String backImagePath;
    private final PointType pointType;

    private final Corner[] corners;

    public GameCard(ArrayList<Resource> cardResourceFront, Resource cardResourceBack, PointType pointType, int points, String frontImagePath, String backImagePath, Corner[] corners) {
        this.cardResourcesFront = cardResourceFront;
        this.cardResourceBack = cardResourceBack;
        this.frontImagePath = frontImagePath;
        this.backImagePath = backImagePath;
        this.corners = corners;
        this.playedFaceDown = false;
        this.pointType = pointType;
        this.points = points;
    }

    public String getImageFrontPath() {
        return frontImagePath;
    }

    public String getImageBackPath() {
        return backImagePath;
    }

    public void setPlayedFaceDown(boolean playedFaceDown) {
        this.playedFaceDown = playedFaceDown;
    }

    public Resource getCardResourceBack() {
        return cardResourceBack;
    }

    public PointType getPointType() {
        return pointType;
    }

    public boolean isPlayedFaceDown() {
        return playedFaceDown;
    }

    public ArrayList<Resource> getCardResourcesFront() {
        return cardResourcesFront;
    }

    public void flipCard() {
        playedFaceDown = !playedFaceDown;
    }

    public void setPoints(int points) {
        this.points = points;
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