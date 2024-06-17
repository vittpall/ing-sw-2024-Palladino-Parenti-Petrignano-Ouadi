package it.polimi.ingsw.gui;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TokenView extends ImageView {
    public static final double BLACK_TOKEN_X = 0.70;
    public static final double BLACK_TOKEN_Y = 0.47;
    public static final double OTHER_TOKEN_X = 0.30;
    public static final double OTHER_TOKEN_Y = 0.47;

    private final double offsetX;
    private final double offsetY;

    public TokenView(String imagePath, double offsetX, double offsetY) {
        Image image = new Image(imagePath);
        this.setImage(image);
        this.setFitWidth(30);
        this.setFitHeight(30);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }
}
