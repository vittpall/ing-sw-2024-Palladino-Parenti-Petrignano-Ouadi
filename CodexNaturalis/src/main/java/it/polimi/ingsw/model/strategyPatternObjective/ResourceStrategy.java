package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.EnumMap;

/**
 * Concrete strategy of the strategy design pattern, check the resource objective
 */



public class ResourceStrategy implements ObjectiveStrategy{

    private final Resource ResourceStrategyToCheck;
    private final int NumOfResourceToCheck;

    /**
     * Constructor which assigns the Strategy that needs to be checked inside the class ResourceStrategy (i.g. The ObjcetiveCard requires two elements of type insect)
     * @param ResourceStrategyToCheck
     * @param NumResource
     */

    public ResourceStrategy(Resource ResourceStrategyToCheck, int NumResource)
    {
        this.ResourceStrategyToCheck = ResourceStrategyToCheck;
        this.NumOfResourceToCheck = NumResource;

    }

    /**
     * enum map is a map where thw key is Resource and the integer is the number of times the resource is on the playerDesk.

     *this method will be recognized if the Resource cards
     *on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific sequence of resources
     * @param desk
     * @return NumberOfTimesVerifiedObjective
     */
    public int isSatisfied (PlayerDesk desk) {
        EnumMap<Resource, Integer> TotalResources = desk.getTotalResources();
        int NumberOfTimesVerifiedObjective = 0;
        int ResourcesOnDesk = 0;


        ResourcesOnDesk=TotalResources.get(ResourceStrategyToCheck);
        if(ResourcesOnDesk>=NumOfResourceToCheck)
        {
            NumberOfTimesVerifiedObjective = ResourcesOnDesk/NumOfResourceToCheck;
        }


        return NumberOfTimesVerifiedObjective;
    }

}