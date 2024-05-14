package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.Scanner;

public class DrawCardState implements ClientState {
    VirtualView client;
    private final Scanner scanner;

    public DrawCardState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        CardPrinter printer = new CardPrinter();
        System.out.println("|-------Draw card state--------|");
        //mostrare i deck e mostrare le carte da poter scegliere
        try {
            System.out.println("|-------Resource deck: visible cards--------|");
            for (GameCard card : client.getVisibleCardsDeck(1)) {
                printer.printCard(card, false);
            }
            System.out.println("|-------Gold deck: visible cards--------|");
            for (GameCard card : client.getVisibleCardsDeck(2)) {
                printer.printCard(card, false);
            }
        } catch ( IOException | InterruptedException ex) {
            System.out.println("Error while getting cards");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        //l'input mi darà 1 se è resource e 2 se è gold per esempio
        switch (input) {
            case 1:
            case 2:
                int inVisible = chooseWhichCardToDraw();
                try {
                    client.drawCard(1, inVisible);
                    String nextState = client.getNextState();
                    if (nextState.equals("WaitForYourTurnState")) {
                        client.setCurrentState(new WaitForYourTurnState(client, scanner));
                    } else if (nextState.equals("LastRoundState")) {
                        client.setCurrentState(new WaitForYourLastTurnState(client, scanner));
                    }

                } catch (CardNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 3:
                client.setCurrentState(new ChatState(client, scanner, this));
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
        System.out.println("3. Chat");

    }
}
