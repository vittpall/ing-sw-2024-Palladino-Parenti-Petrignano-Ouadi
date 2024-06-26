package it.polimi.ingsw.gui;


import it.polimi.ingsw.model.Card;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.HashMap;

public class GameDesk extends Pane {
    private final HashMap<Point, CardView> cards = new HashMap<>();
    private static final double HORIZONTAL_SPACING_FACTOR = CardView.CARD_WIDTH * 0.2214;
    private static final double VERTICAL_SPACING_FACTOR = CardView.CARD_HEIGHT * 0.365;
    private static final double GAME_DESK_WIDTH = 40 * CardView.CARD_WIDTH;
    private static final double GAME_DESK_HEIGHT = 40 * CardView.CARD_HEIGHT;

    /**
     * Add a card to the game desk
     * @param card the card to add
     * @param showFront true if the card should be shown with the front side, false otherwise
     * @param x is a coordinate that indicates the position of the card
     * @param y is a coordinate that indicates the position of the card
     */
    public void addCard(Card card, boolean showFront, int x, int y) {
        CardView cardView = new CardView(card, showFront);
        addCardView(cardView, x, y);
    }

    /**
     * Constructor
     */
    public GameDesk() {
        this.setPrefSize(GAME_DESK_WIDTH, GAME_DESK_HEIGHT);
        this.setWidth(GAME_DESK_WIDTH);
        this.setHeight(GAME_DESK_HEIGHT);
    }


    /**
     * this method calculates the position of the card and adds it to the game desk
     * @param cardView the view of the card to add to the game desk
     * @param x coordinate that indicates the position of the card
     * @param y coordinate that indicates the position of the card
     */
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

    /**
     * this method adds a token to the center of the card.The position of the token is calculated as a percentage of the  card.
     * If the client is the first player, he has a second token that is black
     * @param imagePath the path of the image
     * @param isBlackToken  true if the client is the first player, false otherwise
     */
    public void addTokenToCard(String imagePath, boolean isBlackToken) {
        CardView cardView = cards.get(new Point(0, 0));
        if (cardView != null) {
            double percentX = isBlackToken ? TokenView.BLACK_TOKEN_X : TokenView.OTHER_TOKEN_X;
            double percentY = isBlackToken ? TokenView.BLACK_TOKEN_Y : TokenView.OTHER_TOKEN_Y;

            TokenView token = new TokenView(imagePath, percentX, percentY);
            double tokenX = cardView.getLayoutX() + token.getOffsetX() * CardView.CARD_WIDTH - token.getFitWidth() / 2;
            double tokenY = cardView.getLayoutY() + token.getOffsetY() * CardView.CARD_HEIGHT - token.getFitHeight() / 2;

            token.relocate(tokenX, tokenY);
            this.getChildren().add(token);
        }
    }


}