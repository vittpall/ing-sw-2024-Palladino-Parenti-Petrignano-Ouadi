package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;

/**
 * interface of the design pattern strategy
 */
public interface ObjectiveStrategy {
    int isSatisfied(PlayerDesk desk);
}
