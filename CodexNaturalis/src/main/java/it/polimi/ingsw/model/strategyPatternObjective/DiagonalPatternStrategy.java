package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

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
    public DiagonalPatternStrategy(Resource primarySource, Point diagonalOffset) {
        this.primarySource = primarySource;
        this.diagonalOffset = diagonalOffset;
    }


    public Resource getPrimarySource() {
        return primarySource;
    }

    public Point getDiagonalOffset() {
        return diagonalOffset;
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

    @Override
    public void print(PrintContext context) {
        Point offset = getDiagonalOffset();
        Resource primarySource = getPrimarySource();
        CardPrinter.Color resourceColor = context.chooseColor(primarySource);
        CardPrinter.Color backgroundColor = CardPrinter.Color.GREY; // Default background color for the card

        // Calculate center positions correctly
        int centerX = (context.getCardWidth() / 2) - 1; // Center square starts at cardWidth/2 - 1
        int centerY = context.getCardHeight() / 2;

        // Adjust the x positions for the diagonal based on center X and the offset
        Point centerPoint = new Point(centerX / 2, centerY); // Divide by 2 because each x represents 2 spaces
        Point firstOffsetPoint = new Point((centerX / 2) + offset.x, centerY - offset.y);
        Point secondOffsetPoint = new Point((centerX / 2) - offset.x, centerY + offset.y);

        // Print the card with the diagonal
        for (int y = 0; y < context.getCardHeight() - 1; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < context.getCardWidth() / 2; x++) { // Iterate over half the cardWidth because each square takes 2 character spaces
                if ((x == centerPoint.x && y == centerPoint.y) ||
                        (x == firstOffsetPoint.x && y == firstOffsetPoint.y) ||
                        (x == secondOffsetPoint.x && y == secondOffsetPoint.y)) {
                    line.append(resourceColor);  // Apply resource color for diagonal squares
                } else {
                    line.append(backgroundColor); // Apply grey color for the rest of the card
                }
                line.append("  ");  // Append two spaces for each square
            }
            line.append(CardPrinter.RESET);
            System.out.println(line);
        }
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


        if (diagonalOffset.equals(new Point(1, -1))) {
            changeDiagonal = -1;
        }

        if (!deskToUse.containsKey(startingPoint))
            return false;

        //scan the diagonal until it finds a card that doesn't match the color of the research objective going up and left or right depending on the changeDiagonal
        while (deskToUse.containsKey(new Point(startingPoint.x + i * changeDiagonal, startingPoint.y + i)) &&
                deskToUse.get(new Point(startingPoint.x + i * changeDiagonal, startingPoint.y + i)).getBackSideResource()!=null &&
                deskToUse.get(new Point(startingPoint.x + i * changeDiagonal, startingPoint.y + i)).getBackSideResource().equals(primarySource)) {
            i++;
        }
        i--;

        Point firstPoint = new Point(startingPoint.x + i * changeDiagonal, startingPoint.y + i);
        Point secondPoint = new Point(firstPoint.x - changeDiagonal, firstPoint.y - 1);
        Point thirdPoint = new Point(secondPoint.x - changeDiagonal, secondPoint.y - 1);

        if (deskToUse.containsKey(firstPoint) && deskToUse.get(firstPoint).getBackSideResource()!=null &&
                deskToUse.get(firstPoint).getBackSideResource() == primarySource &&
                deskToUse.containsKey(secondPoint) && deskToUse.get(secondPoint).getBackSideResource()!=null &&
                deskToUse.containsKey(secondPoint) && deskToUse.get(secondPoint).getBackSideResource() == primarySource &&
                deskToUse.containsKey(thirdPoint) && deskToUse.get(thirdPoint).getBackSideResource()!=null &&
                deskToUse.containsKey(thirdPoint) && deskToUse.get(thirdPoint).getBackSideResource() == primarySource) {
            isVerified = true;
        }

        deskToUse.remove(firstPoint);
        deskToUse.remove(secondPoint);
        deskToUse.remove(thirdPoint);

        return isVerified;
    }

}
