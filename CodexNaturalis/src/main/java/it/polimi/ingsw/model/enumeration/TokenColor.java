package it.polimi.ingsw.model.enumeration;

import java.io.Serializable;

/**
 * this enum contains the possible colors that can be associated to the players' token
 *
 * @author Carolina Parenti
 */
public enum TokenColor implements Serializable {

    RED("Red", "CODEX_pion_rouge.png"),
    BLUE("Blue", "CODEX_pion_bleu.png"),
    YELLOW("Yellow", "CODEX_pion_jaune.png"),
    GREEN("Green", "CODEX_pion_vert.png");

    private final String value;
    private final String imageName;

    /**
     * constructor
     *
     * @param value     represents the name of the TokenColor
     * @param imageName represents the image file name of the TokenColor
     */
    TokenColor(String value, String imageName) {
        this.value = value;
        this.imageName = imageName;
    }

    /**
     * Returns the value of the string representing the TokenColor
     */
    public String get() {
        return value;
    }

    /**
     * Returns the image name representing the TokenColor
     */
    public String getImageName() {
        return imageName;
    }

    @Override
    public String toString() {
        return value;
    }
}