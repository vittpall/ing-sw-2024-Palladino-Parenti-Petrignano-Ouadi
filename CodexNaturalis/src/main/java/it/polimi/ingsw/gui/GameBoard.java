package it.polimi.ingsw.gui;


import it.polimi.ingsw.model.Card;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.HashMap;

public class GameBoard extends Pane {
    private final HashMap<Point, CardView> cards = new HashMap<>();
    private final Point origin = new Point(300, 200);
    private static final double HORIZONTAL_SPACING_FACTOR = 22;
    private static final double VERTICAL_SPACING_FACTOR = 40;

    public void addCard(Card card, boolean showFront, int x, int y) {
        CardView cardView = new CardView(card, showFront);
        addCardView(cardView, x, y);
    }

    public void addCardView(CardView cardView, int x, int y) {
        Point point = new Point(x, y);
        cards.put(point, cardView);

        double layoutX = origin.x + x * CardView.CARD_WIDTH - CardView.CARD_WIDTH / 2;
        if (x != 0) {
            layoutX -= Integer.signum(x) * CardView.CARD_WIDTH / 100 * HORIZONTAL_SPACING_FACTOR;
        }

        double layoutY = origin.y + y * CardView.CARD_HEIGHT + CardView.CARD_HEIGHT / 2;
        if (y != 0) {
            layoutY -= Integer.signum(y) * CardView.CARD_HEIGHT / 100 * VERTICAL_SPACING_FACTOR;
        }

        cardView.relocate(layoutX, layoutY);
        this.getChildren().add(cardView);
    }

    public void removeCard(int x, int y) {
        CardView card = cards.remove(new Point(x, y));
        if (card != null) {
            this.getChildren().remove(card);
        }
    }

    public CardView getCard(int x, int y) {
        return cards.get(new Point(x, y));
    }
}