package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This client state is used to choose which card to play
 */
public class PlayCardState implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the BaseClient class that can call the methods in the server
     * @param scanner is a reference to the Scanner class that handles and returns the input of the user
     */
    public PlayCardState(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() throws RemoteException {
        System.out.println("|--------Play card state---------|");
        CardPrinter printer = new CardPrinter();
        try {
            showProvisionalRanking();
            showObjectiveCards(printer);
            showPlayerDesk(printer);
            showPlayerHand(printer);
        } catch (RemoteException ex) {
            throw new RemoteException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.err.println("Error while retrieving data: " + e.getMessage());
        }
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        if (input > 0 && input < 4) {
            //Choose which card to play, where and how
            Point pointChosen = choosePosition();
            boolean faceDown = chooseIfFaceDown();
            try {
                client.playCard(input - 1, faceDown, pointChosen);
                if (client.getCurrentPlayerState() != PlayerState.ENDGAME) {
                    System.out.println("Card played successfully");
                    System.out.println("You should now draw a card");
                } else {
                    System.out.println("Wait for everyone to finish");
                }
                client.setCurrentState(null);
            } catch (RemoteException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            } catch (PlaceNotAvailableException ex) {
                System.out.println("Place not available");
            } catch (RequirementsNotMetException ex) {
                System.out.println("Requirements not met. Please choose another card");
            }
        } else {
            System.out.println("Invalid input");
        }
    }

    @Override
    public void promptForInput() {
        System.out.println("Choose a card to play:");
        System.out.println("1. Play card 1");
        System.out.println("2. Play card 2");
        System.out.println("3. Play card 3");
    }

    /**
     * Private method used to print the provisional rankings
     *
     * @throws IOException          when an I/O operation fails
     * @throws InterruptedException when the thread running is interrupted
     */
    private void showProvisionalRanking() throws IOException, InterruptedException {
        ArrayList<Player> allPlayers = client.getAllPlayers();
        System.out.println("--------------------------------");
        System.out.println("Provisional Ranking:");
        for (Player player : allPlayers) {
            int playerScore = player.getPoints();
            System.out.println("Player: " + player.getUsername() + " | Score: " + playerScore);
        }
        System.out.println("--------------------------------");
    }

    /**
     * Private method used to print the shared and secret Objective cards
     *
     * @param printer is the instance of the Printer used to print the cards
     * @throws IOException          when an I/O operation fails
     * @throws InterruptedException when the thread running is interrupted
     */
    private void showObjectiveCards(CardPrinter printer) throws IOException, InterruptedException {
        System.out.println("|-------- Objective Cards --------|");
        System.out.println("Common objective cards:");
        for (ObjectiveCard card : client.getSharedObjectiveCards()) {
            printer.printCard(card, false);
        }
        System.out.println("Your objective card:");
        printer.printCard(client.getPlayerObjectiveCard(), false);
    }

    /**
     * Private method used to print the user's desk
     *
     * @param printer is the instance of the Printer used to print the cards
     * @throws IOException          when an I/O operation fails
     * @throws InterruptedException when the thread running is interrupted
     */
    private void showPlayerDesk(CardPrinter printer) throws IOException, InterruptedException {
        System.out.println("Your desk:");
        printer.printDesk(client.getPlayerDesk());
    }

    /**
     * Private method used to print the user's hand
     *
     * @param printer is the instance of the Printer used to print the cards
     * @throws IOException          when an I/O operation fails
     * @throws InterruptedException when the thread running is interrupted
     */
    private void showPlayerHand(CardPrinter printer) throws IOException, InterruptedException {
        System.out.println("Choose a card to play:");
        ArrayList<GameCard> playerHand = client.getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            System.out.println((i + 1) + ".");
            printer.printCard(playerHand.get(i), false);
        }
    }

    /**
     * Private method used to check if the card needs to be played faced down or not
     *
     * @return true if the card needs to be played faced down, false otherwise
     */
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

    /**
     * Private method used to choose where to play the card
     *
     * @return a Point that represents the position in which the player wants to play the card
     */
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

    /**
     * Private method used to check if the prompt sent is valid. If not it requests a valid one
     *
     * @param prompt String that represent the input sent by the user
     * @return an integer that represents the prompt sent as a parameter (if valid)
     */
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

    @Override
    public String toString() {
        return "PlayCardState";
    }


}
