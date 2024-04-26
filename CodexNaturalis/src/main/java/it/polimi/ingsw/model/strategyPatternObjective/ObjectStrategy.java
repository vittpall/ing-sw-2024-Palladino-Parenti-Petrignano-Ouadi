package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.CornerObject;

import java.util.EnumMap;

public class ObjectStrategy implements ObjectiveStrategy {

    private final EnumMap<CornerObject, Integer> objectToCheck;

    /**
     * Constructor which assigns the Strategy that needs to be checked inside the class ObjectStrategy (the required object and the respective number of object
     *
     * @param objectToCheck
     */

    public ObjectStrategy(EnumMap<CornerObject, Integer> objectToCheck) {
        this.objectToCheck = objectToCheck;
    }



    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific sequence of objects
     *
     * @param desk
     */
    public int isSatisfied(PlayerDesk desk) {

        EnumMap<CornerObject, Integer> TotalObjects = desk.getTotalObjects();
        int numberOfTimesVerifiedObjective = -1;
        int temporaryNumberOfTimes;
        int objectOnDesk;

        for (CornerObject currObj : objectToCheck.keySet()) {
            objectOnDesk = TotalObjects.get(currObj);
            int numToCheck = objectToCheck.get(currObj);
            if(objectOnDesk == 0)
                return 0;
            temporaryNumberOfTimes = objectOnDesk / numToCheck;
            if (temporaryNumberOfTimes < numberOfTimesVerifiedObjective || numberOfTimesVerifiedObjective == -1)
                numberOfTimesVerifiedObjective = temporaryNumberOfTimes;
        }

        return numberOfTimesVerifiedObjective;
    }
}

    
