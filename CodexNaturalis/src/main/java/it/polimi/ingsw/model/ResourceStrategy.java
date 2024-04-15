package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public class ResourceStrategy implements ObjectiveStrategy{

    private Resource ResourceStrategyToCheck;
    private int NumOfResourceToCheck;

    /**
     * Constructor which assigns the Strategy that needs to be checked inside the class ResourceStrategy (i.g. The ObjcetiveCard requieres two elements of type insect)
     * @param ResourceStrategyToCheck
     */

    public ResourceStrategy(ResourceStrategyValues ResourceStrategyToCheck)
    {
        this.ResourceStrategyToCheck = ResourceStrategyToCheck;
    }

    //enum map è una mappa che contiene ha come chiave la risorsa mentre come valore il numero di risorse. Quello che bisogna fare è vedere quale è il tipo di risorsa
    // della strategy da controllare(c'è l'attributo privato apposta) prendere dalla mappa il numero di volte e restituire quante volte sono verificati. stessa cosa succeda in resource strategy

    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific sequence of resources
     * @param desk
     */
    public int isSatisfied (PlayerDesk desk) {
        EnumMap<Resource, Integer> TotalResources = desk.getTotalResources();
    }


}