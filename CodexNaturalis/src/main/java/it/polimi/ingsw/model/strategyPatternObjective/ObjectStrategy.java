package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveStrategy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class ObjectStrategy implements ObjectiveStrategy{

    private final CornerObject objectStrategyToCheck;
    private int numToCheck;
    private final EnumMap<CornerObject, Integer> objectToCheck;

    /**
     *  Constructor which assigns the Strategy that needs to be checked inside the class ObjectStrategy (the required object and the respective number of object
     * @param objectStrategyToCheck
     * @param numToCheck
     * @param objectToCheck
     */

    public ObjectStrategy(CornerObject objectStrategyToCheck, int numToCheck, EnumMap<CornerObject, Integer> objectToCheck) {
        this.objectStrategyToCheck = objectStrategyToCheck;
        this.numToCheck = numToCheck;
        this.objectToCheck = objectToCheck;
        //objectToCheck = new EnumMap<>(Object.class);
    }

    /**
     * the EnumMap objectToCheck has the required object as a key and the number of object on the player desk as the integer
     * @return
     */
    public EnumMap<CornerObject, Integer> getObjectToCheck() {
        return new EnumMap<>(objectToCheck);
    }
    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific sequence of objects
     * @param desk
     */
    public int isSatisfied (PlayerDesk desk){

        EnumMap<CornerObject, Integer> TotalObjects = desk.getTotalObjects();
        int numberOfTimesVerifiedObjective = -1;
        int temporaryNumberOfTimes;
        int objectOnDesk;
        
        for(CornerObject currObj : objectToCheck.keySet()){
            objectOnDesk = TotalObjects.get(objectStrategyToCheck);
            numToCheck = objectToCheck.get(objectStrategyToCheck);
            temporaryNumberOfTimes = objectOnDesk/numToCheck;
            if( temporaryNumberOfTimes  < numberOfTimesVerifiedObjective || numberOfTimesVerifiedObjective == -1)
                numberOfTimesVerifiedObjective = temporaryNumberOfTimes ;
        }

        return numberOfTimesVerifiedObjective;
    }
}

    
