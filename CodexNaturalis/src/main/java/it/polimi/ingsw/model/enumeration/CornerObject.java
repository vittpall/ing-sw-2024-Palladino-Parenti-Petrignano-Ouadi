package it.polimi.ingsw.model.enumeration;

/**
 * This enums contains all possible elements available on the cards' corners
 */
public enum CornerObject {
    MANUSCRIPT("Manuscript"), QUILL("Quill"), INKWELL("Inkewell");

    private final String text;

    /**
     * Constructor
     *
     * @param text is the representation the CornerObject
     */

    CornerObject(String text)
    {
        this.text = text;
    }

    /**
     * Returns the value of the string representing the CornerObject
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
