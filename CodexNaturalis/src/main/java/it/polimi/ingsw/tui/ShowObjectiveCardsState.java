package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This client state is used to show the secret and shared objective cards
 */
public class ShowObjectiveCardsState implements ClientStateTUI {
    private final BaseClient client;

    /**
     * Constructor
     *
     * @param client is a reference to the BaseClient class that can call the methods in the server
     */
    public ShowObjectiveCardsState(BaseClient client) {
        this.client = client;
    }

    @Override
    public void display() throws RemoteException {
        System.out.println("|-------- Show objective cards ---------|");
        try {
            CardPrinter printer = new CardPrinter();
            System.out.println("|--- The common objective cards are: ---|");
            for (ObjectiveCard card : client.getSharedObjectiveCards())
                printer.printCard(card, false);
            System.out.println("|------- Your objective card is: -------|");
            printer.printCard(client.getPlayerObjectiveCard(), false);
        } catch (RemoteException ex) {
            throw new RemoteException();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error while getting the objective cards");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        if (input != 1)
            System.out.println("Invalid input");
        client.setCurrentState(null);
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Return to main menu");
    }

    @Override
    public String toString() {
        return "ShowObjectiveCardsState";
    }


}
