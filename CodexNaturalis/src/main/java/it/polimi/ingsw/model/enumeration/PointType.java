package it.polimi.ingsw.model.enumeration;

/**
 * This enums contains the possible Types associated with the points given by the card
 */
public enum PointType {
    GENERAL("General"), CORNER("Corner"), MANUSCRIPT("Manuscript"), QUILL("Quill"), INKWELL("Inkewell");

    private final String text;

    /**
     * Constructor
     *
     * @param text is the representation the PointType
     */

    PointType(String text)
    {
        this.text = text;
    }

    /**
     * Returns the value of the string representing the PointType
     */

    public String get()
    {
        return text;
    }

    @Override
    public String toString()
    {
        return text;
    }


}
