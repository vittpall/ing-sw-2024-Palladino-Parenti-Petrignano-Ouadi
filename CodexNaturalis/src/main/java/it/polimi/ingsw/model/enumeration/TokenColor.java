package it.polimi.ingsw.model.enumeration;

/**
 * this enum contains the possible colors that can be associated to the players' token
 *  @author Carolina Parenti
 */
public enum TokenColor{
    RED("Red"), BLUE("Blue"), YELLOW("Yellow"), GREEN("Green");

    private String value;
    /**
     * constructor
     * @param value represents the name of the TokenColor
     */
    private TokenColor(String value){
        this.value=value;
    }

    /**
     *
     * @return value of private attribute value
     */
    public String getValue(){return this.value;}
}