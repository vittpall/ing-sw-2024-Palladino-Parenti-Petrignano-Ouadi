package it.polimi.ingsw.model.enumeration;

import java.io.Serializable;

/**
 * this enum contains the possible colors that can be associated to the players' token
 *
 */
public enum TokenColor implements Serializable {

    RED("Red", "CODEX_pion_rouge.png", "\033[0;31m"),
    BLUE("Blue", "CODEX_pion_bleu.png", "\033[0;34m"),
    YELLOW("Yellow", "CODEX_pion_jaune.png", "\033[0;33m"),
    GREEN("Green", "CODEX_pion_vert.png", "\033[0;32m"),;

    private final String value;
    private final String imageName;
    private final String colorValueANSII;

    /**
     * constructor
     *
     * @param value represents the name of the TokenColor
     * @param imageName represents the image file name of the TokenColor
     * @param colorValueANSII represents the color value in ANSI
     */
    TokenColor(String value, String imageName, String colorValueANSII) {
        this.value = value;
        this.imageName = imageName;
        this.colorValueANSII = colorValueANSII;
    }

    /**
     * @return the value of the string representing the TokenColor
     */
    public String get() {
        return value;
    }

    /**
     * @return the image name representing the TokenColor
     */
    public String getImageName() {
        return imageName;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * @return the value of the string representing the color value in ANSI
     */
    public String getColorValueANSII() {
        return colorValueANSII;
    }
}