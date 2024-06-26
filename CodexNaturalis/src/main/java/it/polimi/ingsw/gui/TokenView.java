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

    /**
     * Constructor
     *
     * @param imagePath is the path of the image of the token
     * @param offsetX   is the offset on the x-axis
     * @param offsetY   is the offset on the y-axis
     */
    public TokenView(String imagePath, double offsetX, double offsetY) {
        Image image = new Image(imagePath);
        this.setImage(image);
        this.setFitWidth(25);
        this.setFitHeight(25);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * This method returns the offset on the x-axis
     *
     * @return the offset on the x-axis
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * This method returns the offset on the y-axis
     *
     * @return the offset on the y-axis
     */
    public double getOffsetY() {
        return offsetY;
    }
}
