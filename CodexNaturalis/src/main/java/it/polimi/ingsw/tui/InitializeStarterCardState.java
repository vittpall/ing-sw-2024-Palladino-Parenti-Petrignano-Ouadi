package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class InitializeStarterCardState implements ClientStateTUI {
    BaseClient client;
    private final Scanner scanner;

    public InitializeStarterCardState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void promptForInput() {
    }

    @Override
    public void display() {
        CardPrinter printer = new CardPrinter();
        System.out.println("\n---------- Initialize starter card ----------");
        try {
            System.out.println("The common objective cards are:");
            //stampare le objective card comuni a tutti i giocatori
            ObjectiveCard[] sharedObjectiveCards = client.getSharedObjectiveCards();
            for (ObjectiveCard card : sharedObjectiveCards) {
                printer.printCard(card, false);
            }
            System.out.println("Your objective card is:");
            //stampare l'objective card richiesta
            printer.printCard(client.getPlayerObjectiveCard(), false);
            System.out.println("Your hand is:");
            //stampare la mano del giocatore richiesta
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
            System.out.println("Error while getting the drawn objective cards");
            System.out.println(ex.getMessage());
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
                } catch (PlaceNotAvailableException | CardNotFoundException | RequirementsNotMetException |
                         IOException | InterruptedException ex) {
                    System.out.println("Card not found. Please try again");
                }
                break;
            case 2:
                try {
                    client.playStarterCard(true);
                    client.setCurrentState(null);
                } catch (RemoteException | PlaceNotAvailableException | CardNotFoundException |
                         RequirementsNotMetException ex) {
                    System.out.println("Card not found. Please try again");
                }
                break;

            default:
                System.out.print("Invalid input");
        }

    }

    public String toString() {
        return "InitializeStarterCardState";
    }


}
