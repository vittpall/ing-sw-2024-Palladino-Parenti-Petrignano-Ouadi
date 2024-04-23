package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.HashMap;
import java.awt.Point;

/**
 * Concrete strategy of the strategy design pattern, check the diagonal objective
 */

public class DiagonalPatternStrategy implements ObjectiveStrategy {

    private Resource primarySource;
    private int points;
    private Point diagonalOffset;

    public DiagonalPatternStrategy(Resource PrimarySource, int Points, Point diagonalOffset) {
        this.primarySource = PrimarySource;
        this.points = Points;
        this.diagonalOffset = diagonalOffset;
    }


    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires two vertical cards and another one over one corner.
     *
     * @param desk
     */
    public int isSatisfied(PlayerDesk desk) {

        int i = 1;
        int numberOfTimesVerifiedObjective = 0;

        //iterate over desk until I found a position where the card's color is the primarySource
        for (Point point : desk.getDesk().keySet()) {
            CheckDiagonal(desk, point);
        }
        return numberOfTimesVerifiedObjective * this.points;
    }


    private boolean CheckDiagonal(PlayerDesk desk, Point startingPoint) {
        int i = 0;
        HashMap<Point, GameCard> deskToUse = desk.getDesk();

            while (desk.getDesk().containsKey(new Point(startingPoint.x + i, startingPoint.y - i)) && desk.getDesk().get(new Point((startingPoint.x + i, startingPoint.y - i)).getbackSideResource().equals(primarySource)) {
                i++;


            Point firstPoint = new Point(startingPoint.x+i-1, startingPoint.y-i-1);
            Point secondPoint = new Point(firstPoint.x+diagonalOffset.x, firstPoint.y+diagonalOffset.y);
            Point thirdPoint = new Point(secondPoint.x + diagonalOffset.x, secondPoint.y + diagonalOffset.y);

            if(deskToUse.containsKey(firstPoint) && deskToUse.get(firstPoint).getbackSideResource() == primarySource && deskToUse.containsKey(secondPoint) && deskToUse.get(secondPoint).getbackSideResource() == primarySource && deskToUse.containsKey(thirdPoint) && deskToUse.get(thirdPoint).getbackSideResource() == primarySource)
            {
                return true;
            }
            return false;
        }
    }
}
