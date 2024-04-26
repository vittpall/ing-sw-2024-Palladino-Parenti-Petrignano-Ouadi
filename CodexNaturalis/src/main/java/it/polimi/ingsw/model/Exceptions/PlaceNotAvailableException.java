package it.polimi.ingsw.model.Exceptions;

/**
 * This exception is thrown when the user tries to add a card in his playerDesk in a position that is not available
 */
public class PlaceNotAvailableException extends Exception{
    /**
     * Default Construct
     * @param text It's the text that will be print after the exception
     */
    public PlaceNotAvailableException(String text)
    {
        super(text);
    }
}