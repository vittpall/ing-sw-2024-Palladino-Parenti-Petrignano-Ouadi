package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.CornerObject;

import java.util.EnumMap;

/**
 * Concrete strategy of the strategy design pattern, check the resource objective
 */

public class ObjectStrategy implements ObjectiveStrategy{

    private CornerObject ObjectStrategyToCheck;
    private int NumOfCheck;

    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific sequence of objects
     * @param desk
     */
    public int isSatisfied (PlayerDesk desk){

        EnumMap<CornerObject, Integer> TotalObjects = desk.getTotalObjects();


    }

}
