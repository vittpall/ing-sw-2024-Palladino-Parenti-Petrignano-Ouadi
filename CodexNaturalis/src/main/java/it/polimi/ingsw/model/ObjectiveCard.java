package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Contains a reference to the possible objectives (strategies) in the game
 */
public class ObjectiveCard {
    ObjectiveStrategy strategy;

    /**
     * Default constructor used to initialise the strategy reference
     * @param strategy it's the strategy we want to use
     */
    public ObjectiveCard(ObjectiveStrategy strategy)
    {
        this.strategy = strategy;
    }

    /**
     * Will call the method isSatisfied which will make override on the concrete strategies
     */
    public void verifyObjective()
    {
        strategy.isSatisfied(PlayerDesk playerDesk);
    }

}
