package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.tui.CardPrinter;
import it.polimi.ingsw.tui.PrintContext;

import java.util.EnumMap;

/**
 * Concrete strategy of the strategy design pattern, check the resource objective
 */


public class ResourceStrategy implements ObjectiveStrategy {

    private final Resource resourceStrategyToCheck;
    private final int numOfResourceToCheck;

    /**
     * Constructor which assigns the Strategy that needs to be checked inside the class ResourceStrategy
     * (i.g. The ObjectiveCard requires two elements of type insect)
     *
     * @param resourceStrategyToCheck the resource that the objective card requires
     * @param numResource the number of times that resource needs to be present
     */

    public ResourceStrategy(Resource resourceStrategyToCheck, int numResource) {
        this.resourceStrategyToCheck = resourceStrategyToCheck;
        this.numOfResourceToCheck = numResource;

    }

    /**
     * This method prints the objective card.
     *
     * @param context represents the context of the print
     */
    @Override
    public void print(PrintContext context) {
        int cardWidth = context.getCardWidth();
        int cardHeight = context.getCardHeight();
        CardPrinter.Color resourceColor = context.chooseColor(resourceStrategyToCheck);

        // Calculate the center position for the number of resources
        int centerTextX = (cardWidth - String.valueOf(numOfResourceToCheck).length()) / 2;
        int centerTextY = cardHeight / 2;

        for (int y = 0; y < cardHeight - 1; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < cardWidth; x++) {
                if (y == centerTextY && x >= centerTextX && x < centerTextX + String.valueOf(numOfResourceToCheck).length()) {
                    line.append(numOfResourceToCheck); // Append the number at the center
                    x += String.valueOf(numOfResourceToCheck).length() - 1; // Adjust x to account for the length of numResource
                } else {
                    line.append(" "); // Fill with space
                }
            }
            line.insert(0, resourceColor); // Apply resource color to the entire line
            line.append(CardPrinter.RESET);
            System.out.println(line);
        }
    }


    /**

     * this method will recognize if the cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires a specific number and type of resources
     *
     * @param desk represents the player's desk
     * @return NumberOfTimesVerifiedObjective
     */
    public int isSatisfied(PlayerDesk desk) {
        EnumMap<Resource, Integer> totalResources = desk.getTotalResources();
        int numberOfTimesVerifiedObjective = 0;
        int resourcesOnDesk;


        resourcesOnDesk = totalResources.get(resourceStrategyToCheck);
        if (resourcesOnDesk >= numOfResourceToCheck) {
            numberOfTimesVerifiedObjective = resourcesOnDesk / numOfResourceToCheck;
        }


        return numberOfTimesVerifiedObjective;
    }

}