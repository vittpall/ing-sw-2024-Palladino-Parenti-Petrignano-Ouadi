package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class CardView extends Pane {
    private ImageView frontImageView;
    private ImageView backImageView;
    public static final double CARD_WIDTH = 145;
    public static final double CARD_HEIGHT = 95;
    private boolean isPlaceholder;

    /**
     * Constructor
     *
     * @param card      is a reference to the Card class
     * @param showFront boolean, it indicates the side of the card
     */
    public CardView(Card card, boolean showFront) {
        super();
        initializeView(card, showFront);
    }

    /**
     * it calls setupPlaceholder if the param is true
     *
     * @param isPlaceholder boolean, the place where there will be the card
     */
    public CardView(boolean isPlaceholder) {
        super();
        this.isPlaceholder = isPlaceholder;
        if (isPlaceholder) {
            setupPlaceholder();
        }
    }

    /**
     * this method initializes the card and set the side
     *
     * @param card      is a reference to the Card class
     * @param showFront boolean, it indicates the side of the card
     */
    private void initializeView(Card card, boolean showFront) {
        loadImage(card);
        setCardVisibility(showFront);
    }


    /**
     * this method returns true if the place is a placeholder
     *
     * @return a boolean
     */
    public boolean isPlaceholder() {
        return isPlaceholder;
    }

    /**
     * this method loads the card's image, set the width and height and connects the front and the back of the same card
     *
     * @param card is a reference to the Card class
     */
    private void loadImage(Card card) {
        String frontImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + card.getImageFrontPath())).toExternalForm();
        Image frontImage = new Image(frontImagePath);
        frontImageView = new ImageView(frontImage);
        frontImageView.setFitWidth(CARD_WIDTH);
        frontImageView.setFitHeight(CARD_HEIGHT);

        String backImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + card.getImageBackPath())).toExternalForm();
        Image backImage = new Image(backImagePath);
        backImageView = new ImageView(backImage);
        backImageView.setFitWidth(CARD_WIDTH);
        backImageView.setFitHeight(CARD_HEIGHT);

        this.getChildren().addAll(backImageView, frontImageView);
    }

    /**
     * this method shows only one side of the same card
     *
     * @param showFront boolean, it indicates the side of the card
     */
    public void setCardVisibility(boolean showFront) {
        frontImageView.setVisible(showFront);
        backImageView.setVisible(!showFront);
    }

    /**
     * this method set up the place where there will be the card
     */
    private void setupPlaceholder() {
        this.setStyle("-fx-border-color: yellow; -fx-border-width: 2; -fx-background-color: rgba(255, 255, 0, 0.5);");
        this.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
    }
}