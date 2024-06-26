package it.polimi.ingsw.util;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.view.tui.CardPrinter;

public class CardPrintSimulator {
    /**
     * this method simulates the printing of the cards. It is used to check if the cards are loaded correctly.
     * It uses the CardPrinter class to print the cards.
     * It uses the GameCardLoader class to load the game cards.
     * It uses the ObjectiveCardLoader class to load the objective cards.
     *
     * @param args the arguments passed to the main method
     */
    public static void main(String[] args) {
        CardPrinter printer = new CardPrinter();
        GameCardLoader gameCardLoader = new GameCardLoader();
        ObjectiveCardLoader objectiveCardLoader = new ObjectiveCardLoader();
        for (GameCard card : gameCardLoader.loadGameCards()) {
            printer.printCard(card, false);
        }
        for (ObjectiveCard card : objectiveCardLoader.loadObjectiveCards()) {
            printer.printCard(card, true);
        }

    }
}
