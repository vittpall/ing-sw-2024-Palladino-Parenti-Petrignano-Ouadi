package it.polimi.ingsw.gui;


import it.polimi.ingsw.model.Card;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.HashMap;

public class GameDesk extends Pane {
    private final HashMap<Point, CardView> cards = new HashMap<>();
    private static final double HORIZONTAL_SPACING_FACTOR = CardView.CARD_WIDTH * 0.2214;
    private static final double VERTICAL_SPACING_FACTOR = CardView.CARD_HEIGHT * 0.365;

    public void addCard(Card card, boolean showFront, int x, int y) {
        CardView cardView = new CardView(card, showFront);
        addCardView(cardView, x, y);
    }

    public GameDesk() {
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

    public void addTokenToCard(int x, int y, String imagePath, boolean isBlackToken) {
        CardView cardView = cards.get(new Point(x, y));
        if (cardView != null) {
            double percentX = isBlackToken ? TokenView.BLACK_TOKEN_X : TokenView.OTHER_TOKEN_X;
            double percentY = isBlackToken ? TokenView.BLACK_TOKEN_Y : TokenView.OTHER_TOKEN_Y;

            TokenView token = new TokenView(imagePath, percentX, percentY);
            // Calcola la posizione del token basata sulle percentuali e sulla dimensione della carta
            double tokenX = cardView.getLayoutX() + token.getOffsetX() * CardView.CARD_WIDTH - token.getFitWidth() / 2;
            double tokenY = cardView.getLayoutY() + token.getOffsetY() * CardView.CARD_HEIGHT - token.getFitHeight() / 2;

            token.relocate(tokenX, tokenY);
            this.getChildren().add(token);
        }
    }


}