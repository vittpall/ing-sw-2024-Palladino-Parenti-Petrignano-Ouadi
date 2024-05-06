package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class InitializeStarterCardState implements ClientState{
    VirtualView client;
    private final Scanner scanner;

    public InitializeStarterCardState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }
    @Override
    public void promptForInput() {
        System.out.print("Enter your choice\n(1- if you want to play it on the front\n" +
                "2- if you want to play it faced down \n3 to exit) : ");
    }
    @Override
    public void display() {
        CardPrinter printer = new CardPrinter();
        System.out.println("\n---------- Initialize starter card ----------");
        try {
            System.out.println("The common objective cards are:");
            //stampare le objective card comuni a tutti i giocatori
            ObjectiveCard[] sharedObjectiveCards = client.getSharedObjectiveCards();
            for(ObjectiveCard card : sharedObjectiveCards){
                printer.printCard(card);
            }
            System.out.println("Your objective card is:");
            //stampare l'objective card richiesta
            printer.printCard(client.getPlayerObjectiveCard());
            System.out.println("Your hand is:");
            //stampare la mano del giocatore richiesta
            ArrayList<GameCard> playerHand = client.getPlayerHand();
            for(GameCard card : playerHand){
                printer.printCard(card);
            }
            StarterCard playerStarterCard = client.getStarterCard();
            System.out.println("Your starter card is:");
            printer.printCard(playerStarterCard);
            System.out.println("|   3. Exit 🚪                        |");
        } catch (RemoteException ex) {
            System.out.println("Error while getting the drawn objective cards");
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void inputHandler(int input) throws RemoteException {
        switch (input) {
            case 1:
                try {
                    client.playStarterCard(false);
                    // client.setCurrentState(new InitializeStarterCardState(client, scanner));
                } catch (RemoteException | PlaceNotAvailableException | CardNotFoundException | RequirementsNotMetException ex) {
                    System.out.println("Card not found. Please try again");
                }
                break;
            case 2:
                try {
                    client.playStarterCard(true);
                    // client.setCurrentState(new InitializeStarterCardState(client, scanner));
                } catch (RemoteException | PlaceNotAvailableException | CardNotFoundException | RequirementsNotMetException ex) {
                    System.out.println("Card not found. Please try again");
                }
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.print("Invalid input");
                display();

        }
        String nextState=client.getNextState();
        if(nextState.equals("PlayCardState"))
            client.setCurrentState(new PlayCardState(client, scanner));
        else if(nextState.equals("WaitForYourTurnState"))
            client.setCurrentState(new WaitForYourTurnState(client, scanner));
        else{
            System.out.println("Error");
            display();
        }
    }
}
