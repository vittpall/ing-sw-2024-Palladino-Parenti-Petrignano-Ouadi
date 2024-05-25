package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

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
        int primaryX = centerOfCardX - 2;  // Adjusted to center primary squares a bit left
        int primaryY1 = centerOfCardY + 1;
        int primaryY2 = primaryY1 - 1;

        // Calculate position for the secondary square using the offset
        int secondaryX = primaryX + whichCorner.x * 2;
        int secondaryY = primaryY1 - whichCorner.y;

        for (int y = 0; y < cardHeight; y++) {
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
            if (desk.getDesk().get(point).getBackSideResource()!=null && desk.getDesk().get(point).getBackSideResource().equals(primarySource)) {
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
    boolean CheckCorner(HashMap<Point, GameCard> deskToUse, Point startingPoint) {
        int i = 0;
        boolean IsVerified = false;

        if (!deskToUse.containsKey(startingPoint))
            return false;

        //scan the desk until it finds the first card that doesn't match the primarySource going down the desk
        while (deskToUse.containsKey(new Point(startingPoint.x, startingPoint.y + i)) && deskToUse.get(new Point(startingPoint.x, startingPoint.y + i)).getBackSideResource().equals(primarySource))
            i -= 2;

        i += 2;

        Point LowerCard = new Point(startingPoint.x, startingPoint.y + i);
        Point UpperCard = new Point(startingPoint.x, startingPoint.y + i + 2);
        Point CornerCard = new Point(LowerCard.x + whichCorner.x, LowerCard.y + whichCorner.y);

        if (deskToUse.containsKey(LowerCard) && deskToUse.get(LowerCard).getBackSideResource()!=null &&
                deskToUse.get(LowerCard).getBackSideResource() == primarySource &&
                deskToUse.containsKey(UpperCard) && deskToUse.get(UpperCard).getBackSideResource()!=null &&
                deskToUse.containsKey(UpperCard) && deskToUse.get(UpperCard).getBackSideResource() == primarySource) {
            if (deskToUse.containsKey(CornerCard) && deskToUse.get(CornerCard).getBackSideResource()!=null &&
                    deskToUse.get(CornerCard).getBackSideResource() == secondarySource)
                IsVerified = true;
        }

        //remove the card previously checked
        deskToUse.remove(LowerCard);
        deskToUse.remove(UpperCard);
        deskToUse.remove(CornerCard);

        return IsVerified;
    }
}
