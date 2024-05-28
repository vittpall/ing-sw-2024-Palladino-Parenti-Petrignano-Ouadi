package it.polimi.ingsw.model.enumeration;

import java.io.Serializable;

/**
 * this enum contains the possible colors that can be associated to the players' token
 *
 * @author Carolina Parenti
 */
public enum TokenColor implements Serializable {
    RED("Red"), BLUE("Blue"), YELLOW("Yellow"), GREEN("Green");

    private final String value;

    /**
     * constructor
     *
     * @param value represents the name of the TokenColor
     */
    TokenColor(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the string representing the TokenColor
     */

    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}