package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class ObjectStrategy implements ObjectiveStrategy{

    private final CornerObject objectStrategyToCheck;
    private int numToCheck;
    private final EnumMap<CornerObject, Integer> objectToCheck;

    public ObjectStrategy(CornerObject objectStrategyToCheck, int numToCheck, EnumMap<CornerObject, Integer> objectToCheck, EnumMap<CornerObject, Integer> objectToCheck1) {
        this.objectStrategyToCheck = objectStrategyToCheck;
        this.numToCheck = numToCheck;
        this.objectToCheck = objectToCheck1;
        objectToCheck = new EnumMap<>(Object.class);
    }

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

    
