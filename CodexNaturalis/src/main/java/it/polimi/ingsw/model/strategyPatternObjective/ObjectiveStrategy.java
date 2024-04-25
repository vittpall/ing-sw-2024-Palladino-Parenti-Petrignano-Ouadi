package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;

/**
 * interface of the design pattern strategy
 */
public interface ObjectiveStrategy {
    /**
     * standard method of the strategy pattern that will be overridden by the concrete strategies
     * @param desk
     * @return
     */
    int isSatisfied(PlayerDesk desk);
}
