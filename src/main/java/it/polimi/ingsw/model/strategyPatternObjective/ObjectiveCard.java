package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.view.tui.CardPrinter;
import it.polimi.ingsw.view.tui.PrintContext;

import java.io.Serializable;

/**
 * Contains a reference to the possible objectives (strategies) in the game
 */
public class ObjectiveCard extends Card implements Serializable {
    ObjectiveStrategy strategy;
    /**
     * Constructor
     *
     * @param copy it's the object we want to copy
     */

    public ObjectiveCard(ObjectiveCard copy) {
        super(copy.getPoints(), copy.getImageFrontPath(), copy.getImageBackPath());
        this.strategy = copy.getStrategy();
    }

    /**
     * Default constructor used to initialise the strategy reference
     *
     * @param strategy it's the strategy we want to use
     */
    public ObjectiveCard(ObjectiveStrategy strategy, int points, String backImagePath, String frontImagePath) {
        super(points, frontImagePath, backImagePath);
        this.strategy = strategy;
    }

    /**
     * Will call the method isSatisfied which will make override on the concrete strategies
     *
     * @param desk represents the desk of the player
     * @return the number of times the objective is verified
     */
    public int verifyObjective(PlayerDesk desk) {
        return strategy.isSatisfied(desk);
    }

    /**
     * @return the strategy of the objective
     */
    public ObjectiveStrategy getStrategy() {
        return strategy;
    }

    @Override
    public void print(PrintContext context, boolean faceDown) {
        strategy.print(context);
        String pointsDetail = "Pts: " + getPoints() + " ";
        System.out.println(CardPrinter.Color.GREY + " " + context.centerString(pointsDetail, context.getCardWidth() - 2) + " " + CardPrinter.RESET);
        System.out.println();
    }


}
