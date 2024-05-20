package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class CardView extends Pane {
    private ImageView frontImageView;
    private ImageView backImageView;

    public CardView(Card card, boolean showFront) {
        super();
        initializeView(card, showFront);
    }

    private void initializeView(Card card, boolean showFront) {
        loadImage(card);
        setCardVisibility(showFront);
    }

    private void loadImage(Card card) {
        String frontImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + card.getImageFrontPath())).toExternalForm();
        Image frontImage = new Image(frontImagePath);
        frontImageView = new ImageView(frontImage);
        frontImageView.setFitWidth(154);
        frontImageView.setFitHeight(214);
        frontImageView.setPreserveRatio(true);

        String backImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + card.getImageBackPath())).toExternalForm();
        Image backImage = new Image(backImagePath);
        backImageView = new ImageView(backImage);
        backImageView.setFitWidth(154);
        backImageView.setFitHeight(214);
        backImageView.setPreserveRatio(true);

        this.getChildren().addAll(backImageView, frontImageView);
    }

    public void setCardVisibility(boolean showFront) {
        frontImageView.setVisible(showFront);
        backImageView.setVisible(!showFront);
    }

    public void flipCard() {
        setCardVisibility(!frontImageView.isVisible());
    }
}