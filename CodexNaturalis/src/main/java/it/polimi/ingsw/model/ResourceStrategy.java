package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

import java.util.EnumMap;

public class ResourceStrategy implements ObjectiveStrategy {

    private final Resource resourceStrategyToCheck;
    private final int numOfResourcesToCheck;

    /**
     * Constructor which assigns the Strategy that needs to be checked inside the class ResourceStrategy (i.g. The ObjcetiveCard requieres two elements of type insect)
     *
     * @param resourceStrategyToCheck
     */

    public ResourceStrategy(Resource resourceStrategyToCheck, int numOfResourcesToCheck) {
        this.resourceStrategyToCheck = resourceStrategyToCheck;
        this.numOfResourcesToCheck = numOfResourcesToCheck;
    }

    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific sequence of resources
     *
     * @param desk
     */
    public int isSatisfied(PlayerDesk desk) {
        EnumMap<Resource, Integer> TotalResources = desk.getTotalResources();
        return TotalResources.get(resourceStrategyToCheck) / numOfResourcesToCheck;
    }


}