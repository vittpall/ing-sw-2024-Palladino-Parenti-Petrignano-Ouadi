package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class DrawCardState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;

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
            printer.printCard(client.getLastFromUsableCards(1), true);
            System.out.println("|-------Resource deck: visible cards--------|");
            for (GameCard card : client.getVisibleCardsDeck(1)) {
                printer.printCard(card, false);
            }
            System.out.println("|-------Gold deck: last hidden card--------|");
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
                int inVisible = chooseWhichCardToDraw();
                client.drawCard(input, inVisible);
                client.setCurrentState(null);

                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    private int chooseWhichCardToDraw() {
        System.out.println("1. Draw first visible card of the deck chosen");
        System.out.println("2. Draw second visible card of the deck chosen");
        System.out.println("3. Draw hidden card of the deck chosen");
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return switch (input) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            default -> chooseWhichCardToDraw();
        };
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Draw resource card(visible or hidden)");
        System.out.println("2. Draw gold card(visible or hidden)");

    }

    public String toString() {
        return "DrawCardState";
    }


}
