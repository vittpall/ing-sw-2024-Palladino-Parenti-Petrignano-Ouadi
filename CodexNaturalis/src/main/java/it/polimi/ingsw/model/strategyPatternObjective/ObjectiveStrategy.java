package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.tui.PrintContext;

import java.io.Serializable;

/**
 * interface of the design pattern strategy
 */
public interface ObjectiveStrategy extends Serializable {
    /**
     * standard method of the strategy pattern that will be overridden by the concrete strategies
     * @param desk
     * @return
     */
    int isSatisfied(PlayerDesk desk);

    void print(PrintContext context);
}
