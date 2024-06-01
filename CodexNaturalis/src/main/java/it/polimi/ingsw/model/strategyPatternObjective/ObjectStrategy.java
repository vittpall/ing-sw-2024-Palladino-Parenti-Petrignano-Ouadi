package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.util.EnumMap;
import java.util.Map;

public class ObjectStrategy implements ObjectiveStrategy {

    private final EnumMap<CornerObject, Integer> objectToCheck;

    /**
     * Constructor which assigns the Strategy that needs to be checked inside the class ObjectStrategy (the required object and the respective number of object
     *
     * @param objectToCheck the map of objects to check
     */

    public ObjectStrategy(EnumMap<CornerObject, Integer> objectToCheck) {
        this.objectToCheck = objectToCheck;
    }


    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific sequence of objects
     *
     * @param desk the player's desk
     * @return the number of times the objective is satisfied
     */
    public int isSatisfied(PlayerDesk desk) {

        EnumMap<CornerObject, Integer> TotalObjects = desk.getTotalObjects();
        int numberOfTimesVerifiedObjective = -1;
        int temporaryNumberOfTimes;
        int objectOnDesk;

        for (CornerObject currObj : objectToCheck.keySet()) {
            objectOnDesk = TotalObjects.get(currObj);
            int numToCheck = objectToCheck.get(currObj);
            if (objectOnDesk == 0)
                return 0;
            temporaryNumberOfTimes = objectOnDesk / numToCheck;
            if (temporaryNumberOfTimes < numberOfTimesVerifiedObjective || numberOfTimesVerifiedObjective == -1)
                numberOfTimesVerifiedObjective = temporaryNumberOfTimes;
        }

        return numberOfTimesVerifiedObjective;
    }


    /**
     * This method prints the object pattern objective.
     *
     * @param context the print context
     */
    @Override
    public void print(PrintContext context) {
        CardPrinter.Color backgroundColor = CardPrinter.Color.GREY;
        context.printCenteredLine("", backgroundColor);
        int y = 1;
        for (Map.Entry<CornerObject, Integer> entry : objectToCheck.entrySet()) {
            if (y >= context.getCardHeight()) break;
            String lineContent = entry.getKey().toString() + ": " + entry.getValue();
            y++;
            context.printCenteredLine(lineContent, backgroundColor);
        }

        // Fill the rest of the card with blank lines if needed
        while (y < context.getCardHeight() - 1) {
            y++;
            context.printCenteredLine("", backgroundColor);
        }
    }
}

    
