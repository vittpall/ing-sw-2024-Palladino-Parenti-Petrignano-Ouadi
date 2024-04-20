package it.polimi.ingsw.model;

/**
 * This is an abstract class named Card.
 * It has a protected integer variable named points.
 * This class can be extended by other classes to represent different types of cards.
 */
public abstract class Card {
    // The points associated with the card
    protected int points;
    protected String frontImagePath;
    protected String backImagePath;

    /**
     * This method is used to get the points of the card.
     *
     * @return int This returns the points of the card.
     */
    public int getPoints() {
        return this.points;
    }

    public String getImageFrontPath() {
        return frontImagePath;
    }

    public String getImageBackPath() {
        return backImagePath;
    }
}