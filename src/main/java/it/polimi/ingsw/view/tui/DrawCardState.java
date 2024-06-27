package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This client state is used when the user wants to create a new game, so that he can choose its characteristics
 */
public class DrawCardState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the BaseClient class that can call the methods in the server
     * @param scanner is a reference to the Scanner class that handles and returns the input of the user
     */
    public DrawCardState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() throws RemoteException {
        CardPrinter printer = new CardPrinter();
        System.out.println("|-------Draw card state--------|");
        //mostrare i deck e mostrare le carte da poter scegliere
        try {
            System.out.println("|-------Resource deck: last hidden card--------|");
            if (client.getLastFromUsableCards(1) != null)
                printer.printCard(client.getLastFromUsableCards(1), true);
            System.out.println("|-------Resource deck: visible cards--------|");
            for (GameCard card : client.getVisibleCardsDeck(1)) {
                printer.printCard(card, false);
            }
            System.out.println("|-------Gold deck: last hidden card--------|");
            if (client.getLastFromUsableCards(2) != null)
                printer.printCard(client.getLastFromUsableCards(2), true);
            System.out.println("|-------Gold deck: visible cards--------|");
            for (GameCard card : client.getVisibleCardsDeck(2)) {
                printer.printCard(card, false);
            }
        } catch (RemoteException e) {
            throw new RemoteException();
        } catch (IOException | InterruptedException ex) {
            System.out.println("Error while getting cards");
        }

    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        switch (input) {
            case 1:
            case 2:
                if (client.getVisibleCardsDeck(input).isEmpty() && client.getLastFromUsableCards(input) == null) {
                    System.out.println("No cards in this deck");
                    break;
                }
                int inVisible = chooseWhichCardToDraw(input);
                client.drawCard(input, inVisible);
                client.setCurrentState(null);
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }


    /**
     * Private method
     * Gets the client's input that represents which card he wants to draw
     *
     * @param deck the deck from which the user wants to draw a card
     * @return an integer that identify the specific or generic card the user wants to draw
     * @throws IOException          if there is a I/O error
     * @throws InterruptedException if the thread is interrupted
     */
    private int chooseWhichCardToDraw(int deck) throws IOException, InterruptedException {
        ArrayList<GameCard> visibleCards = client.getVisibleCardsDeck(deck);
        if (visibleCards.isEmpty()) {
            System.out.println("No visible cards in the deck");
        } else if (visibleCards.size() == 1) {
            System.out.println("1. Draw first visible card of the deck chosen");
        } else {
            System.out.println("1. Draw first visible card of the deck chosen");
            System.out.println("2. Draw second visible card of the deck chosen");
        }
        if (client.getLastFromUsableCards(deck) != null)
            System.out.println("3. Draw hidden card of the deck chosen");
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return switch (input) {
            case "1" -> {
                if (visibleCards.isEmpty())
                    yield chooseWhichCardToDraw(deck);
                yield 1;
            }
            case "2" -> {
                if (visibleCards.size() < 2)
                    yield chooseWhichCardToDraw(deck);
                yield 2;
            }
            case "3" -> {
                if (client.getLastFromUsableCards(deck) == null)
                    yield chooseWhichCardToDraw(deck);
                yield 3;
            }
            default -> chooseWhichCardToDraw(deck);
        };
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Draw resource card(visible or hidden)");
        System.out.println("2. Draw gold card(visible or hidden)");

    }

    @Override
    public String toString() {
        return "DrawCardState";
    }


}
