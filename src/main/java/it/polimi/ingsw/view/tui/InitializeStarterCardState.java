package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This client state is used when the users need to initialize the starter card
 */
public class InitializeStarterCardState implements ClientStateTUI {
    private final BaseClient client;

    /**
     * Constructor
     *
     * @param client is a reference to the BaseClient class that can call the methods in the server
     *               +
     */
    public InitializeStarterCardState(BaseClient client) {
        this.client = client;
    }

    @Override
    public void promptForInput() {
    }

    @Override
    public void display() throws RemoteException {
        CardPrinter printer = new CardPrinter();
        System.out.println("\n---------- Initialize starter card ----------");
        try {
            System.out.println("The common objective cards are:");
            //Print the shared objective card
            ObjectiveCard[] sharedObjectiveCards = client.getSharedObjectiveCards();
            for (ObjectiveCard card : sharedObjectiveCards) {
                printer.printCard(card, false);
            }
            System.out.println("Your objective card is:");
            //Print the secret objective card
            printer.printCard(client.getPlayerObjectiveCard(), false);
            System.out.println("Your hand is:");
            //Print the player hand and starter card
            ArrayList<GameCard> playerHand = client.getPlayerHand();
            for (GameCard card : playerHand) {
                printer.printCard(card, false);
            }
            StarterCard playerStarterCard = client.getStarterCard();
            System.out.println("Your starter card is:");
            System.out.println("|   1. Play on the front 🎮          |");
            printer.printCard(playerStarterCard, false);
            System.out.println("|   2. Play faced down 🎮            |");
            printer.printCard(playerStarterCard, true);
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
                    client.playStarterCard(false);
                    client.setCurrentState(null);
                } catch (PlaceNotAvailableException | RequirementsNotMetException |
                         IOException | InterruptedException ex) {
                    System.out.println("Card not found. Please try again");
                }
                break;
            case 2:
                try {
                    client.playStarterCard(true);
                    client.setCurrentState(null);
                } catch (RemoteException | PlaceNotAvailableException |
                         RequirementsNotMetException ex) {
                    System.out.println("Card not found. Please try again");
                }
                break;

            default:
                System.out.print("Invalid input");
        }

    }

    @Override
    public String toString() {
        return "InitializeStarterCardState";
    }


}
