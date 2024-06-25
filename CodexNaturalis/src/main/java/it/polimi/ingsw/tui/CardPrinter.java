package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.awt.*;
import java.util.HashMap;

/**
 * This class is used for printing the card in the TUI
 */
public class CardPrinter {
    public static final String RESET = "\u001B[0m";

    /**
     * This enum contains the card's color
     */
    public enum Color {
        GREEN("\u001B[42m"), BLUE("\u001B[44m"), RED("\u001B[41m"),
        PURPLE("\u001B[45m"), GREY("\u001B[100m"), WHITE("\u001B[47m");

        private final String code;

        /**
         * Constructor
         *
         * @param code String representing the codified name of color
         */
        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }


    private final int cardWidth;
    private final int cardHeight;
    private final int cornerSize;


    /**
     * Constructor
     * It sets the size of the printed card in the TUI
     */
    public CardPrinter() {
        this.cardWidth = 20;
        this.cardHeight = 5;
        this.cornerSize = 3;
    }

    /**
     * This method gets the height of the card
     *
     * @return cardHeight, the height of the card
     */
    public int getCardHeight() {
        return cardHeight;
    }

    /**
     * This method gets the width of the card
     *
     * @return cardWidth, the width of the card
     */
    public int getCardWidth() {
        return cardWidth;
    }

    /**
     * This method gets the size of the card
     *
     * @return cornerSize, the size of the corner
     */
    public int getCornerSize() {
        return cornerSize;
    }

    /**
     * This method print the back of the card
     *
     * @param card     is a reference to the Card class
     * @param faceDown if it is true, then it will be placed the back of the card
     */
    public void printCard(Card card, boolean faceDown) {
        PrintContext context = new PrintContext(this);
        card.print(context, faceDown);
    }

    /**
     * This method print the Desk, which means the client can see all the cards in the desk and their positions
     *
     * @param desk the client's desk
     */
    public void printDesk(HashMap<Point, GameCard> desk) {
        PrintContext context = new PrintContext(this);
        context.printDesk(desk);
    }

    /**
     * This method associate a color to a resource and return that color
     *
     * @param backSideResource it indicates the Resource in the back of the card
     * @return the Color of the card
     */
    protected Color chooseColor(Resource backSideResource) {
        if (backSideResource == null) return Color.WHITE;
        return switch (backSideResource) {
            case PLANT_KINGDOM -> Color.GREEN;
            case ANIMAL_KINGDOM -> Color.BLUE;
            case FUNGI_KINGDOM -> Color.RED;
            case INSECT_KINGDOM -> Color.PURPLE;
        };
    }


}
