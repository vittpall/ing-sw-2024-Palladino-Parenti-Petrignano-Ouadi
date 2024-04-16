package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.CornerObject;

import java.util.EnumMap;

public class ObjectStrategy implements ObjectiveStrategy {

    private final CornerObject objectStrategyToCheck;
    private int numOfObjectsToCheck;

    public ObjectStrategy(CornerObject objectStrategyToCheck1, int numOfObjectsToCheck) {
        this.objectStrategyToCheck = objectStrategyToCheck1;
        this.numOfObjectsToCheck = numOfObjectsToCheck;
    }

    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific sequence of objects
     *
     * @param desk
     */
    public int isSatisfied(PlayerDesk desk) {
        EnumMap<CornerObject, Integer> TotaleObjects = desk.getTotalObjects();
        return TotaleObjects.get(objectStrategyToCheck) / numOfObjectsToCheck;
    }

}
