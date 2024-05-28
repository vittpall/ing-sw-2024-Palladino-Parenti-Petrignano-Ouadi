package it.polimi.ingsw.network;

import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract public class BaseClient implements VirtualView, GameListener {
    protected Scanner scan;
    protected ClientState currentState;

    protected abstract boolean checkState(RequestedActions action) throws IOException, InterruptedException, ClassNotFoundException;

    public abstract void close() throws IOException, InterruptedException;

    public abstract PlayerState getCurrentPlayerState() throws IOException, InterruptedException;

    protected abstract String getCurrentState() throws IOException, InterruptedException;

    protected void inputHandler() throws IOException, ClassNotFoundException, InterruptedException {
        boolean correctInput;
        String input = "";
        do {
            showState();
            correctInput = false;
            while (!correctInput) {
                try {
                    System.out.print("Type your command or 'exit' to quit: ");
                    input = scan.nextLine().trim().toLowerCase();
                    correctInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input: Reinsert the value: ");
                }
            }

            if (!handleCommonInput(input)) {
                try {
                    currentState.inputHandler(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Please enter a number.");
                }
            }
        } while (currentState != null);
        while (true) {
            if (currentState == null) {
                display();
                correctInput = false;
                while (!correctInput) {
                    try {
                        System.out.print("Type your command or 'exit' to quit: ");
                        input = scan.nextLine().trim().toLowerCase();
                        correctInput = true;
                    } catch (InputMismatchException e) {
                        System.out.println("\nInvalid input: Reinsert the value: ");
                    }
                }
            } else
                input = "no display";

            if (!handleCommonInput(input)) {
                try {
                    boolean checkState;
                    if (currentState == null)
                        checkState = gameLogicInputHandler(Integer.parseInt(input));
                    else
                        checkState = true;
                    if (checkState) {
                        showState();
                        boolean correctInput2 = false;
                        while (!correctInput2) {
                            try {
                                System.out.print("Type your command or 'exit' to quit: ");
                                input = scan.nextLine().trim().toLowerCase();
                                correctInput2 = true;
                            } catch (InputMismatchException e) {
                                System.out.println("\nInvalid input: Reinsert the value: ");
                            }
                        }
                        if (!handleCommonInput(input)) {
                            try {
                                currentState.inputHandler(Integer.parseInt(input));
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input: Please enter a number.");
                            }
                        }
                    } else {
                        System.out.println("The input was not valid. You can " + getCurrentState());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Please enter a number.");
                }
            }
        }
    }

    public void setCurrentState(ClientState state) {
        this.currentState = state;
    }

    private boolean gameLogicInputHandler(int i) {
        try {
            boolean checkState;
            switch (i) {
                case 1:
                    checkState = checkState(RequestedActions.PLAY_CARD);
                    if (checkState) currentState = new PlayCardState(this, scan);
                    return checkState;

                case 2:
                    checkState = checkState(RequestedActions.DRAW);
                    if (checkState) currentState = new DrawCardState(this, scan);
                    return checkState;
                case 3:
                    checkState = checkState(RequestedActions.SHOW_DESKS);
                    if (checkState) currentState = new GetOtherPlayerDesk(this, scan);
                    return checkState;
                case 4:
                    checkState = checkState(RequestedActions.SHOW_OBJ_CARDS);
                    if (checkState) currentState = new ShowObjectiveCardsState(this, scan);
                    return checkState;
                case 5:
                    checkState = checkState(RequestedActions.SHOW_POINTS);
                    if (checkState) currentState = new ShowPointsState(this, scan);
                    return checkState;
                case 6:
                    checkState = checkState(RequestedActions.CHAT);
                    if (checkState) currentState = new ChatState(this, scan);
                    return checkState;
                case 7:
                    checkState = checkState(RequestedActions.SHOW_WINNER);
                    if (checkState) currentState = new GetWinnerState(this, scan);
                    return checkState;
                default:
                    return false;
            }
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private void display() {
        System.out.println("⚔️  _________________________________  ⚔️");
        System.out.println("|                                       |");
        System.out.println("|   Please choose an action:            |");
        try {
            if (getCurrentPlayerState().equals(PlayerState.PLAY_CARD))
                System.out.println("|   1. Play a card🎮                    |");
            else if (getCurrentPlayerState().equals(PlayerState.DRAW))
                System.out.println("|   2. Draw a card🎴                    |");
            System.out.println("|   3. Show your desk or others' desks  |");
            System.out.println("|   4. Show the shared objective cards  |");
            System.out.println("|      and your objective card          |");
            System.out.println("|   5. Show players' points             |");
            System.out.println("|   6. Chat 💬                          |");
            if (getCurrentPlayerState().equals(PlayerState.ENDGAME))
                System.out.println("|   7. Show winner                      |");
            System.out.println("|_______________________________________|\n");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean handleCommonInput(String input) {
        if ("exit".equals(input)) {
            try {
                System.out.println("Exiting game...");
                close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


}
