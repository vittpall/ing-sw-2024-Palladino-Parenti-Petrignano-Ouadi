package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;

import java.awt.Point;
import java.util.HashMap;

/**
 * Concrete strategy of the strategy design pattern, check the vertical objective
 */

public class VerticalPatternStrategy implements ObjectiveStrategy {
    private final Resource primarySource;
    private final Resource secondarySource;
    private final int points;
    private Point whichCorner;

    public VerticalPatternStrategy(Resource primarySource, Resource secondarySource, int points, Point whichCorner) {
        this.primarySource = primarySource;
        this.secondarySource = secondarySource;
        this.points = points;
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
        //iterate over desk until I found a position where the card's color is the primarySource
        for (Point point : desk.getDesk().keySet()) {
            if (desk.getDesk().get(point).getbackSideResource().equals(PrimarySource)) {
                //instead of mapping the color to the corner that needs to be checked, it'll use the parameter WhichCorner
                if (CheckCorner(desk, point))
                    numberOfTimesVerifiedObjective++;
            }
        }
        return numberOfTimesVerifiedObjective * this.points;
    }

    private boolean CheckCorner(PlayerDesk desk, Point StartingPoint) {
        int i = 2;
        HashMap<Point, GameCard> deskToUse = desk.getDesk();
        boolean IsVerified = false;

        while (desk.getDesk().containsKey(new Point(StartingPoint.x, (int) StartingPoint.y + i)) && desk.getDesk().get(new Point(StartingPoint.x, (int) StartingPoint.y + i)).getbackSideResource().equals(PrimarySource))
            i += 2;

        i -= 2;

        Point LowerCard = new Point(StartingPoint.x, StartingPoint.y + i);
        Point UpperCard = new Point(StartingPoint.x, StartingPoint.y + i - 2);
        Point CornerCard = new Point(StartingPoint.x + whichCorner.x, StartingPoint.y + whichCorner.y);

        if (deskToUse.containsKey(LowerCard) && deskToUse.get(LowerCard).getbackSideResource() == primarySource && deskToUse.containsKey(UpperCard) && deskToUse.get(UpperCard).getbackSideResource() == primarySource) {
            if (deskToUse.containsKey(CornerCard) && deskToUse.get(CornerCard).getbackSideResource() == secondarySource)
                IsVerified = true;
        }

        //remove the card previously checked
        deskToUse.remove(LowerCard);
        deskToUse.remove(UpperCard);

        if(IsVerified)
            return true;
        return false;
    }
}
