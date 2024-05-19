package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
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
        try {
            showProvisionalRanking();
            showObjectiveCards(printer);
            showPlayerDesk(printer);
            showPlayerHand(printer);
        } catch (RemoteException ex) {
            System.out.println("Error while getting the drawn objective cards");
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.err.println("Error while retrieving data: " + e.getMessage());
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
            }
            return;
        }
        else {
            System.out.println("Invalid input");
        }
    }

    @Override
    public void promptForInput() {
        System.out.println("Choose a card to play:");
        System.out.println("1. Play card 1");
        System.out.println("2. Play card 2");
        System.out.println("3. Play card 3");
        System.out.println("4. Chat");
    }


    private void showProvisionalRanking() throws IOException, InterruptedException {
        ArrayList<Player> allPlayers = client.getAllPlayers(client.getIdGame());
        System.out.println("--------------------------------");
        System.out.println("Provisional Ranking:");
        for (Player player : allPlayers) {
            int playerScore = client.getPoints();
            System.out.println("Player: " + player.getUsername() + " | Score: " + playerScore);
        }
        System.out.println("--------------------------------");
    }

    private void showObjectiveCards(CardPrinter printer) throws IOException, InterruptedException {
        System.out.println("|-------- Objective Cards --------|");
        System.out.println("Common objective cards:");
        for (ObjectiveCard card : client.getSharedObjectiveCards()) {
            printer.printCard(card, false);
        }
        System.out.println("Your objective card:");
        printer.printCard(client.getPlayerObjectiveCard(), false);
    }

    private void showPlayerDesk(CardPrinter printer) throws IOException, InterruptedException {
        System.out.println("Your desk:");
        printer.printDesk(client.getPlayerDesk());
    }

    private void showPlayerHand(CardPrinter printer) throws IOException, InterruptedException {
        System.out.println("Choose a card to play:");
        ArrayList<GameCard> playerHand = client.getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            System.out.println((i + 1) + ".");
            printer.printCard(playerHand.get(i), false);
        }
    }


    private boolean chooseIfFaceDown() {
        System.out.println("Choose how to play the card");
        System.out.println("1. Faced up");
        System.out.println("2. Faced down");
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());

        if (input.equals("1"))
            return false;
        if (input.equals("2"))
            return true;

        return chooseIfFaceDown();
    }

    private Point choosePosition() {
        HashSet<Point> availablePlaces;
        try {
            System.out.println("Available places:");
            availablePlaces = client.getAvailablePlaces();
            for (Point avPoint : availablePlaces) {
                String formattedCoordinates = String.format("Position: (%d, %d)", avPoint.x, avPoint.y);
                System.out.println(formattedCoordinates);
            }
            int xCoordinate = getValidCoordinate("Choose the x-coordinate: ");
            int yCoordinate = getValidCoordinate("Choose the y-coordinate: ");
            Point selectedPoint = new Point(xCoordinate, yCoordinate);
            if (!availablePlaces.contains(selectedPoint)) {
                System.out.println("Selected point is not an available place. Please try again.");
                return choosePosition();
            }
            return selectedPoint;
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private int getValidCoordinate(String prompt) {
        int coordinate = -1;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                coordinate = Integer.parseInt(input);
                isValid = true; // Valid integer input received
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return coordinate;
    }


}
