package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class PlayCardState implements ClientState {
    VirtualView client;
    private final Scanner scanner;

    public PlayCardState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("|--------Play card state---------|");
        CardPrinter printer = new CardPrinter();
        //mostrare il proprio desk e mostrare la mano in cui far scegliere quale carta giocare
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
            System.out.println("Your desk is:\n");
            //stampare il desk del giocatore
            System.out.println("Choose a card to play:");
            //stampare la mano del giocatore richiesta
            ArrayList<GameCard> playerHand = client.getPlayerHand();
            int i = 1;
            for (GameCard card : playerHand) {
                System.out.println(i + ".");
                printer.printCard(card, false);
                i++;
            }
        } catch (RemoteException ex) {
            System.out.println("Error while getting the drawn objective cards");
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        if (input > 0 && input < 4) {
            //scelgo se giocare la carta 1,2,3
            Point pointChosen = choosePosition();
            boolean faceDown = chooseIfFaceDown();
            try {
                client.playCard(input - 1, faceDown, pointChosen);
            } catch (RemoteException ex) {
                System.out.println(ex.getMessage());
            } catch (PlaceNotAvailableException ex) {
                System.out.println("Place not available");
            } catch (CardNotFoundException ex) {
                System.out.println("Card not found");
                System.out.println(ex.getMessage());
            } catch (RequirementsNotMetException ex) {
                System.out.println("Requirements not met. Please choose another card");
                //rimandare dopo tutte le eccezioni in questo stato
            }
            client.setCurrentState(new DrawCardState(client, scanner));
            return;
        }
        switch (input) {
            case 4:
                System.exit(0);
                break;
            case 5:
                client.setCurrentState(new ChatState(client, scanner));
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    private boolean chooseIfFaceDown() {
        //metodo in cui si chiede al giocatore se vuole giocare la carta coperta o scoperta
        System.out.println("Choose how to play the card(1: faced up - 2:faced down): ");
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());

        if (input.equals("1"))
            return false;
        if (input.equals("2"))
            return true;

        System.out.println("Invalid input");
        return chooseIfFaceDown();
    }

    private Point choosePosition() {
        //metodo in cui si chiede al giocatore in che posizione vuole giocare la carta
        //se non è disponibile la posizione si richiede fino a quando non ce n'è una disponibile
        try {
            System.out.println("Available places:");
            HashSet<Point> availablePlaces = client.getAvailablePlaces();
            for (Point avPoint : availablePlaces) {
                System.out.println(avPoint + "\n");
            }
            System.out.println("Choose the x-coordinate: ");
            String input;
            do {
                input = scanner.nextLine().trim();
            } while (input.isEmpty());

            int xCoordinate = Integer.parseInt(input);
            System.out.println("Choose the y-coordinate: ");
            do {
                input = scanner.nextLine().trim();
            } while (input.isEmpty());
            int yCoordinate = Integer.parseInt(input);
            //TODO catch exception if input is not a number
            if (!availablePlaces.contains(new Point(xCoordinate, yCoordinate))) {
                System.out.println("Invalid number of players");
                choosePosition();
            } else {
                return new Point(xCoordinate, yCoordinate);
            }
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public void promptForInput() {
        System.out.println("Choose a card to play:");
        System.out.println("1. Play card 1");
        System.out.println("2. Play card 2");
        System.out.println("3. Play card 3");
        System.out.println("4. Exit");
        System.out.println("5. Chat");
    }
}
