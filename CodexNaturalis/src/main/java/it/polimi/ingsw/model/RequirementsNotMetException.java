package it.polimi.ingsw.model;

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