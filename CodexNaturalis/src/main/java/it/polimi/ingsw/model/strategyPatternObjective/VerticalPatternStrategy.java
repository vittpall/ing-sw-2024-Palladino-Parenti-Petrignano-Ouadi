package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.awt.*;
import java.util.HashMap;

/**
 * This class represents a strategy for checking vertical patterns in the game.
 * It implements the ObjectiveStrategy interface.
 */
public class VerticalPatternStrategy implements ObjectiveStrategy {
    private final Resource primarySource;
    private final Resource secondarySource;
    private final Offset whichCorner;

    /**
     * Enum representing the offset for the vertical pattern.
     */
    public enum Offset {
        BOTTOM_LEFT(new Point(-1, -1)),
        TOP_RIGHT(new Point(1, 3)),
        TOP_LEFT(new Point(-1, 3)),
        BOTTOM_RIGHT(new Point(1, -1));

        private final Point point;


        Offset(Point point) {
            this.point = point;
        }

        public Point getPoint() {
            return point;
        }

        public int getX() {
            return point.x;
        }

        public int getY() {
            return point.y;
        }


        /**
         * This method returns the Offset enum value corresponding to the given coordinates.
         *
         * @param x the x-coordinate
         * @param y the y-coordinate
         * @return the Offset enum value corresponding to the given coordinates
         */
        public static Offset fromCoordinates(int x, int y) {
            for (Offset offset : Offset.values()) {
                if (offset.getX() == x && offset.getY() == y) {
                    return offset;
                }
            }
            return null;
        }

    }


    /**
     * Constructor for creating a VerticalPatternStrategy with a primary resource, a secondary resource, and an offset.
     *
     * @param primarySource   the primary resource for the vertical pattern
     * @param secondarySource the secondary resource for the vertical pattern
     * @param whichCorner     the offset for the vertical pattern
     */
    public VerticalPatternStrategy(Resource primarySource, Resource secondarySource, Offset whichCorner) {
        this.primarySource = primarySource;
        this.secondarySource = secondarySource;
        this.whichCorner = whichCorner;
    }


    @Override
    public void print(PrintContext context) {
        int cardWidth = context.getCardWidth();
        int cardHeight = context.getCardHeight();

        CardPrinter.Color primaryColor = context.chooseColor(primarySource);
        CardPrinter.Color secondaryColor = context.chooseColor(secondarySource);
        CardPrinter.Color backgroundColor = CardPrinter.Color.GREY;

        // Calculate the central x position for the primary squares
        int centerOfCardX = cardWidth / 2;
        int centerOfCardY = cardHeight / 2;
        int primaryX = centerOfCardX - 2;
        int primaryY1 = centerOfCardY + 1;
        primaryY1 += Math.signum(whichCorner.getY()) == 1 ? 1 : 0;
        int primaryY2 = primaryY1 - 1;

        // Calculate position for the secondary square using the offset
        int secondaryX = primaryX + whichCorner.getX() * 2;
        int secondaryY = primaryY1 - whichCorner.getY();
        secondaryY += Math.signum(whichCorner.getY()) == 1 ? 1 : 0;

        for (int y = 1; y < cardHeight; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < cardWidth; x += 2) {
                if ((x == primaryX) && (y == primaryY1 || y == primaryY2)) {
                    line.append(primaryColor).append("  ");
                } else if (x == secondaryX && y == secondaryY) {
                    line.append(secondaryColor).append("  ");
                } else {
                    line.append(backgroundColor).append("  ");
                }
            }
            line.append(CardPrinter.RESET);
            System.out.println(line);
        }
    }


    /**
     * this method will recognize if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires two vertical cards and another one over one corner.
     *
     * @param desk the player's desk
     * @return the number of times the objective is satisfied
     */
    public int isSatisfied(PlayerDesk desk) {
        int numberOfTimesVerifiedObjective = 0;
        HashMap<Point, GameCard> deskToUse = desk.getDesk();
        Point starterCardLocation = new Point(0, 0);
        deskToUse.remove(starterCardLocation);

        //iterate over desk until I found a position where the card's color is the primarySource
        for (Point point : desk.getDesk().keySet()) {
            if (desk.getDesk().get(point).getBackSideResource() != null && desk.getDesk().get(point).getBackSideResource().equals(primarySource)) {
                //instead of mapping the color to the corner that needs to be checked, it'll use the parameter WhichCorner
                if (CheckCorner(deskToUse, point))
                    numberOfTimesVerifiedObjective++;
            }
        }
        return numberOfTimesVerifiedObjective;
    }

    /**
     * This method checks if a vertical pattern is satisfied starting from a given point.
     *
     * @param deskToUse     the player's desk
     * @param startingPoint the starting point for the check
     * @return true if the pattern is satisfied, false otherwise
     */
    boolean CheckCorner(HashMap<Point, GameCard> deskToUse, Point startingPoint) {
        int i = 0;
        Point starterCardLocation = new Point(0, 0);
        deskToUse.remove(starterCardLocation);
        boolean IsVerified = false;

        if (!deskToUse.containsKey(startingPoint))
            return false;

        while (deskToUse.containsKey(new Point(startingPoint.x, startingPoint.y + i)) && deskToUse.get(new Point(startingPoint.x, startingPoint.y + i)).getBackSideResource().equals(primarySource))
            i -= 2;

        i += 2;

        Point LowerCard = new Point(startingPoint.x, startingPoint.y + i);
        Point UpperCard = new Point(startingPoint.x, startingPoint.y + i + 2);
        Point CornerCard = new Point(LowerCard.x + whichCorner.getX(), LowerCard.y + whichCorner.getY());

        if (deskToUse.get(LowerCard).getBackSideResource() == primarySource && deskToUse.containsKey(UpperCard) && deskToUse.get(UpperCard).getBackSideResource() == primarySource) {
            if (deskToUse.get(CornerCard).getBackSideResource() == secondarySource)
                IsVerified = true;
        }

        //remove the card previously checked
        deskToUse.remove(LowerCard);
        deskToUse.remove(UpperCard);
        deskToUse.remove(CornerCard);

        return IsVerified;
    }
}
