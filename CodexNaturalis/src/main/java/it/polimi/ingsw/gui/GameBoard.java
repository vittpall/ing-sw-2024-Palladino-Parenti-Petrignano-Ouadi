package it.polimi.ingsw.gui;


import it.polimi.ingsw.model.Card;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.HashMap;

public class GameBoard extends Pane {
    private final HashMap<Point, CardView> cards = new HashMap<>();
    private static final double HORIZONTAL_SPACING_FACTOR = CardView.CARD_WIDTH * 0.2214;
    private static final double VERTICAL_SPACING_FACTOR = CardView.CARD_HEIGHT * 0.365;

    public void addCard(Card card, boolean showFront, int x, int y) {
        CardView cardView = new CardView(card, showFront);
        addCardView(cardView, x, y);
    }

    public GameBoard() {
        this.setPrefSize(1000, 1000);
        this.setWidth(1000);
        this.setHeight(1000);
    }


    public void addCardView(CardView cardView, int x, int y) {
        double centerX = getWidth() / 2;
        double centerY = getHeight() / 2;

        double layoutX = centerX + x * CardView.CARD_WIDTH - CardView.CARD_WIDTH / 2;
        if (x != 0) {
            layoutX -= x * HORIZONTAL_SPACING_FACTOR;
        }

        double layoutY = centerY - y * CardView.CARD_HEIGHT - CardView.CARD_HEIGHT / 2;
        if (y != 0) {
            layoutY += y * VERTICAL_SPACING_FACTOR;
        }

        cardView.relocate(layoutX, layoutY);
        cards.put(new Point(x, y), cardView);
        this.getChildren().add(cardView);
    }

}