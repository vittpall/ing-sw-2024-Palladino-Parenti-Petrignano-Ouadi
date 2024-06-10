package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.util.Scanner;

public class ShowObjectiveCardsState implements ClientStateTUI {
    BaseClient client;
    private final Scanner scanner;

    public ShowObjectiveCardsState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }
    @Override
    public void display() {
        System.out.println("|-------- Show objective cards ---------|");
        try{
            CardPrinter printer = new CardPrinter();
            System.out.println("|--- The common objective cards are: ---|");
            for (ObjectiveCard card : client.getSharedObjectiveCards())
                printer.printCard(card, false);
            System.out.println("|------- Your objective card is: -------|");
            printer.printCard(client.getPlayerObjectiveCard(), false);
        }catch(IOException | InterruptedException e){
            System.out.println("Error while getting the objective cards");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        if(input!=1)
            System.out.println("Invalid input");
        client.setCurrentState(null);
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Return to main menu");
    }

    public String toString() {
        return "ShowObjectiveCardsState";
    }

    
   
}
