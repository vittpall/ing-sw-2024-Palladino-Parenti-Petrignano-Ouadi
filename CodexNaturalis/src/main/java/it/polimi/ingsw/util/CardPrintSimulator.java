package it.polimi.ingsw.util;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.view.tui.CardPrinter;

public class CardPrintSimulator {
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
