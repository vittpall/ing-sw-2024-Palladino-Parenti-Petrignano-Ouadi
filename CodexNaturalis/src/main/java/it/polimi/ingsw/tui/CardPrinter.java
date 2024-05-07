package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.enumeration.Resource;

public class CardPrinter {
    public static final String RESET = "\u001B[0m";

    public enum Color {
        GREEN("\u001B[42m"), BLUE("\u001B[44m"), RED("\u001B[41m"),
        PURPLE("\u001B[45m"), GREY("\u001B[100m"), WHITE("\u001B[47m");

        private final String code;

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

    public CardPrinter() {
        this.cardWidth = 20;
        this.cardHeight = 5;
        this.cornerSize = 3;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCornerSize() {
        return cornerSize;
    }

    public void printCard(Card card, boolean faceDown) {
        PrintContext context = new PrintContext(this);
        card.print(context, faceDown);
    }


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
