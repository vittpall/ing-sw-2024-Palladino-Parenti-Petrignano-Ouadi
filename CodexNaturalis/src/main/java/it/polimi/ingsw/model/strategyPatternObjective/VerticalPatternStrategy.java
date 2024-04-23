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
    private final Resource PrimarySource;
    private final Resource SecondarySource;
    private final int Points;
    private Point WhichCorner;

    public VerticalPatternStrategy(Resource PrimarySource, Resource SecondarySource, int Points, Point WhichCorner) {
        this.PrimarySource = PrimarySource;
        this.SecondarySource = SecondarySource;
        this.Points = Points;
        this.WhichCorner = WhichCorner;
    }

    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires two vertical cards and another one over one corner.
     *
     * @param desk
     */
    public int isSatisfied(PlayerDesk desk) {
        int NumberOfTimesVerifiedObjective = 0;
        //iterate over desk until I found a position where the card's color is the primarySource
        for (Point point : desk.getDesk().keySet()) {
            if (desk.getDesk().get(point).getbackSideResource().equals(PrimarySource)) {
                //instead of mapping the color to the corner that needs to be checked, it'll use the parameter WhichCorner
                if (CheckCorner(desk, point))
                    NumberOfTimesVerifiedObjective++;
            }
        }
        return NumberOfTimesVerifiedObjective * this.Points;
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
        Point CornerCard = new Point(StartingPoint.x + WhichCorner.x, StartingPoint.y + WhichCorner.y);

        if (deskToUse.containsKey(LowerCard) && deskToUse.get(LowerCard).getbackSideResource() == PrimarySource && deskToUse.containsKey(UpperCard) && deskToUse.get(UpperCard).getbackSideResource() == PrimarySource) {
            if (deskToUse.containsKey(CornerCard) && deskToUse.get(CornerCard).getbackSideResource() == SecondarySource)
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
