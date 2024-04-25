package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;

import java.awt.*;
import java.util.HashMap;

/**
 * Concrete strategy of the strategy design pattern, check the diagonal objective
 */

public class DiagonalPatternStrategy implements ObjectiveStrategy {

    private final Resource primarySource;
    private final Point diagonalOffset;

    /**
     * Constructor which assigns the Strategy that needs to be checked inside the class DiagonalPatternStrategy (the required resource, the number of points of the objectiveCard and the direction of the diagonal)
     *
     * @param primarySource
     * @param diagonalOffset
     */
    public DiagonalPatternStrategy(Resource primarySource,  Point diagonalOffset) {
        this.primarySource = primarySource;
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

        int numberOfTimesVerifiedObjective = 0;
        HashMap<Point, GameCard> deskToUse = desk.getDesk();

        //iterate over desk until I found a position where the card's color is the primarySource
        for (Point point : desk.getDesk().keySet()) {
            if (CheckDiagonal(deskToUse, point))
                numberOfTimesVerifiedObjective++;
        }
        return numberOfTimesVerifiedObjective;
    }

    /**
     * scan the entire board (hashmap) until it founds a matching pattern and then return true if the pattern complete the objective otherwise will return false
     *
     * @param deskToUse
     * @param startingPoint
     * @return
     */
    private boolean CheckDiagonal(HashMap<Point, GameCard> deskToUse, Point startingPoint) {
        int i = 0;
        int changeDiagonal = 1;
        boolean isVerified = false;


        if(diagonalOffset.equals(new Point(1,1))){
            changeDiagonal = 1;
        }
        if(diagonalOffset.equals(new Point(1,-1))){
            changeDiagonal = -1;
        }

        if(!deskToUse.containsKey(startingPoint))
            return false;

        while (deskToUse.containsKey(new Point(startingPoint.x + i*changeDiagonal, startingPoint.y + i)) && deskToUse.get(new Point(startingPoint.x + i*changeDiagonal, startingPoint.y - i)).getbackSideResource().equals(primarySource)) {
            i++;
        }
        i--;

        Point firstPoint = new Point(startingPoint.x + i*changeDiagonal, startingPoint.y + i);
        Point secondPoint = new Point(firstPoint.x - changeDiagonal, firstPoint.y - 1);
        Point thirdPoint = new Point(secondPoint.x - changeDiagonal, secondPoint.y -1);

        if (deskToUse.containsKey(firstPoint) && deskToUse.get(firstPoint).getbackSideResource() == primarySource && deskToUse.containsKey(secondPoint) && deskToUse.get(secondPoint).getbackSideResource() == primarySource && deskToUse.containsKey(thirdPoint) && deskToUse.get(thirdPoint).getbackSideResource() == primarySource) {
            isVerified = true;
        }

        deskToUse.remove(firstPoint);
        deskToUse.remove(secondPoint);
        deskToUse.remove(thirdPoint);

        return isVerified;
    }

}
