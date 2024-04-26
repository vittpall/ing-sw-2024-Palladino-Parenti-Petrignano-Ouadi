package it.polimi.ingsw.model.Exceptions;

/**
 * This exception is thrown when the player tries to put a card in his PlayerDesk but he does not have the requirements to do it
 */
public class RequirementsNotMetException extends Exception{
    /**
     * Default Construct
     * @param text It's the text that will be print after the exception
     */
    public RequirementsNotMetException(String text)
    {
        super(text);
    }
}