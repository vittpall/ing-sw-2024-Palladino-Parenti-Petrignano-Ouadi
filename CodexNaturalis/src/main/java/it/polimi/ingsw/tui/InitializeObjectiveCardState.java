package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This client state is used when the users need to initialize its secret objective card
 */
public class InitializeObjectiveCardState implements ClientStateTUI {
    private final BaseClient client;

    /**
     * Constructor
     *
     * @param client is a reference to the BaseClient class that can call the methods in the server
     */
    public InitializeObjectiveCardState(BaseClient client) {
        this.client = client;
    }

    @Override
    public void promptForInput() {

    }

    @Override
    public void display() throws RemoteException {
        int nObjectiveCard = 1;
        CardPrinter printer = new CardPrinter();
        System.out.println("\n---------- Game Setup ----------");
        try {
            ArrayList<ObjectiveCard> playerObjectiveCards = client.getPlayerObjectiveCards();
            System.out.println("Your objective cards are:");
            for (ObjectiveCard card : playerObjectiveCards) {
                System.out.println(nObjectiveCard + ".");
                printer.printCard(card, false);
                nObjectiveCard++;
            }
        } catch (RemoteException ex) {
            throw new RemoteException();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {

        switch (input) {
            case 1:
                try {
                    client.setObjectiveCard(0);
                    client.setCurrentState(new InitializeStarterCardState(client));
                } catch (IOException | InterruptedException ex) {
                    System.out.println("Card not found. Please try again");
                }
                break;
            case 2:
                try {
                    client.setObjectiveCard(1);
                    client.setCurrentState(new InitializeStarterCardState(client));
                } catch (RemoteException ex) {
                    System.out.println("Card not found. Please try again");
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.print("Invalid input");
        }
    }

    @Override
    public String toString() {
        return "InitializeObjectiveCardState";
    }

}
