package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;

import java.awt.*;
import java.util.HashMap;

/**
 * Concrete strategy of the strategy design pattern, check the vertical objective
 */

public class VerticalPatternStrategy implements ObjectiveStrategy {
    private final Resource primarySource;
    private final Resource secondarySource;
    private final Point whichCorner;

    /**
     * Constructor which assigns the Strategy that needs to be checked inside the class VerticalPatternStrategy (which requires two resources, the number of points of the objective card and the position of the secondary resource)
     *
     * @param primarySource
     * @param secondarySource
     * @param whichCorner
     */
    public VerticalPatternStrategy(Resource primarySource, Resource secondarySource, Point whichCorner) {
        this.primarySource = primarySource;
        this.secondarySource = secondarySource;
        this.whichCorner = whichCorner;
    }

    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires two vertical cards and another one over one corner.
     *
     * @param desk
     */
    public int isSatisfied(PlayerDesk desk) {
        int numberOfTimesVerifiedObjective = 0;
        HashMap<Point, GameCard> deskToUse = desk.getDesk();
        //iterate over desk until I found a position where the card's color is the primarySource
        for (Point point : desk.getDesk().keySet()) {
            if (desk.getDesk().get(point).getbackSideResource().equals(primarySource)) {
                //instead of mapping the color to the corner that needs to be checked, it'll use the parameter WhichCorner
                if (CheckCorner(deskToUse, point))
                    numberOfTimesVerifiedObjective++;
            }
        }
        return numberOfTimesVerifiedObjective;
    }

    /**
     * Scan the map until it finds at least a card matching the color of the research objective, then it checks if the pattern is verified and will return false otherwise will return false.
     *
     * @param deskToUse
     * @param startingPoint
     * @return
     */
    private boolean CheckCorner(HashMap<Point, GameCard> deskToUse, Point startingPoint) {
        int i = 0;
        boolean IsVerified = false;

        if(!deskToUse.containsKey(startingPoint))
            return false;

        //scan the desk until it finds the first card that doesn't match the primarySource going down the desk
        while (deskToUse.containsKey(new Point(startingPoint.x, startingPoint.y + i)) && deskToUse.get(new Point(startingPoint.x, startingPoint.y + i)).getbackSideResource().equals(primarySource))
            i -= 2;

        i += 2;

        Point LowerCard = new Point(startingPoint.x, startingPoint.y + i);
        Point UpperCard = new Point(startingPoint.x, startingPoint.y + i + 2);
        Point CornerCard = new Point(startingPoint.x + whichCorner.x, startingPoint.y + whichCorner.y);

        if (deskToUse.containsKey(LowerCard) && deskToUse.get(LowerCard).getbackSideResource() == primarySource && deskToUse.containsKey(UpperCard) && deskToUse.get(UpperCard).getbackSideResource() == primarySource) {
            if (deskToUse.containsKey(CornerCard) && deskToUse.get(CornerCard).getbackSideResource() == secondarySource)
                IsVerified = true;
        }

        //remove the card previously checked
        deskToUse.remove(LowerCard);
        deskToUse.remove(UpperCard);

        return IsVerified;
    }
}
