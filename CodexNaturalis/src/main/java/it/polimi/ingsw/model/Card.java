package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This is an abstract class named Card.
 * It has private integer variables for points and strings for front and back image paths.
 * This class can be extended by other classes to represent different types of cards.
 */
public abstract class Card implements Serializable {
    private int points;
    private final String frontImagePath;
    private final String backImagePath;

    /**
     * Constructor for abstract class Card.
     *
     * @param points         the point value of the card
     * @param frontImagePath the path to the front image of the card
     * @param backImagePath  the path to the back image of the card
     * @throws IllegalArgumentException if points are negative
     */
    public Card(int points, String frontImagePath, String backImagePath) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.points = points;
        this.frontImagePath = frontImagePath;
        this.backImagePath = backImagePath;
    }

    /**
     * This method is used to get the points of the card.
     *
     * @return int This returns the points of the card.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * This method is used to set the points of the card.
     *
     * @param points The points to be set for the card.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * This method is used to get the front image path of the card.
     *
     * @return String This returns the front image path of the card.
     */
    public String getImageFrontPath() {
        return this.frontImagePath;
    }

    /**
     * This method is used to get the back image path of the card.
     *
     * @return String This returns the back image path of the card.
     */
    public String getImageBackPath() {
        return this.backImagePath;
    }


}