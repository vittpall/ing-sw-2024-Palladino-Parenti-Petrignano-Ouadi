package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class InitializeObjectiveCardState implements ClientState {
    VirtualView client;
    private final Scanner scanner;

    public InitializeObjectiveCardState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void promptForInput() {

    }

    @Override
    public void display() {
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
            System.out.println("Error while getting the drawn objective cards");
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void inputHandler(int input) throws RemoteException {

        switch (input) {
            case 1:
                try {
                    client.setObjectiveCard(0);
                    client.setCurrentState(new InitializeStarterCardState(client, scanner));
                } catch (CardNotFoundException | IOException | InterruptedException ex) {
                    System.out.println("Card not found. Please try again");
                }
                break;
            case 2:
                try {
                    client.setObjectiveCard(1);
                    client.setCurrentState(new InitializeStarterCardState(client, scanner));
                } catch (CardNotFoundException | RemoteException ex) {
                    System.out.println("Card not found. Please try again");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.print("Invalid input");
        }
    }

    public String toString() {
        return "InitializeObjectiveCardState";
    }
}
