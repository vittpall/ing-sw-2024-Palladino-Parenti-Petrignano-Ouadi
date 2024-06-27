package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.view.tui.PrintContext;

import java.io.Serializable;

/**
 * interface of the design pattern strategy
 */
public interface ObjectiveStrategy extends Serializable {
    /**
     * standard method of the strategy pattern that will be overridden by the concrete strategies.
     * it checks if the objective is satisfied and if so, how many times
     *
     * @param desk represents the desk of the player
     * @return the number of times the objective is verified
     */
    int isSatisfied(PlayerDesk desk);

    /**
     * standard method of the strategy pattern that will be overridden by the concrete strategies.
     * it prints the objective
     *
     * @param context represents the context of the print
     */
    void print(PrintContext context);
}
