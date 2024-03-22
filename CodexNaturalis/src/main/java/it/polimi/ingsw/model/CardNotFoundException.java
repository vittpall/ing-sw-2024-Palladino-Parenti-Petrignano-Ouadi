package it.polimi.ingsw.model;

public class CardNotFoundException extends Exception{
    /**
     * Default Construct
     * @param text It's the text that will be print after the exception
     */
    public CardNotFoundException(String text)
    {
        super(text);
    }
}
