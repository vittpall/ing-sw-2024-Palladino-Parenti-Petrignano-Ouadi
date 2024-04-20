package it.polimi.ingsw.model;

/**
 * Contains a reference to the possible objectives (strategies) in the game
 */
public class ObjectiveCard extends Card {
    ObjectiveStrategy strategy;

    public ObjectiveCard(ObjectiveCard copy) {
        this.strategy = copy.getStrategy();
        this.points = copy.getPoints();
    }

    /**
     * Default constructor used to initialise the strategy reference
     *
     * @param strategy it's the strategy we want to use
     */
    public ObjectiveCard(ObjectiveStrategy strategy, int points, String backImagePath, String frontImagePath) {
        this.strategy = strategy;
        this.points = points;
        this.backImagePath = backImagePath;
        this.frontImagePath = frontImagePath;
    }

    /**
     * Will call the method isSatisfied which will make override on the concrete strategies
     *
     * @param desk
     * @return the number of times the objective is verified
     */
    public int verifyObjective(PlayerDesk desk) {
        return strategy.isSatisfied(desk);
    }


    public ObjectiveStrategy getStrategy() {
        return strategy;
    }
}
