package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class CardView extends Pane {
    private ImageView frontImageView;
    private ImageView backImageView;
    public static final double CARD_WIDTH = 166;
    public static final double CARD_HEIGHT = 111;

    public CardView(Card card, boolean showFront) {
        super();
        initializeView(card, showFront);
    }

    public CardView(boolean isPlaceholder) {
        super();
        if (isPlaceholder) {
            setupPlaceholder();
        }
    }

    private void initializeView(Card card, boolean showFront) {
        loadImage(card);
        setCardVisibility(showFront);
    }

    private void loadImage(Card card) {
        String frontImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + card.getImageFrontPath())).toExternalForm();
        Image frontImage = new Image(frontImagePath);
        frontImageView = new ImageView(frontImage);
        frontImageView.setFitWidth(CARD_WIDTH);
        frontImageView.setFitHeight(CARD_HEIGHT);
        //frontImageView.setPreserveRatio(true);

        String backImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + card.getImageBackPath())).toExternalForm();
        Image backImage = new Image(backImagePath);
        backImageView = new ImageView(backImage);
        backImageView.setFitWidth(CARD_WIDTH);
        backImageView.setFitHeight(CARD_HEIGHT);
        // backImageView.setPreserveRatio(true);

        this.getChildren().addAll(backImageView, frontImageView);
    }

    public void setCardVisibility(boolean showFront) {
        frontImageView.setVisible(showFront);
        backImageView.setVisible(!showFront);
    }

    private void setupPlaceholder() {
        String placeHolderPath = Objects.requireNonNull(getClass().getResource("/Images/cardPlaceHolder.jpeg")).toExternalForm();
        Image image = new Image(placeHolderPath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setFitHeight(CARD_HEIGHT);
        this.getChildren().addAll(imageView);
        imageView.setVisible(true);
    }
}