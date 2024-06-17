package it.polimi.ingsw.network;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.UsefulData;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.notifications.ServerNotification;
import it.polimi.ingsw.tui.*;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

abstract public class BaseClient implements VirtualView, GameListener {
    private Scanner scan;
    private ClientState currentState;
    protected String username;
    private Integer idGame;
    protected BlockingQueue<ServerNotification> notificationsQueue;
    private boolean isGUIMode;
    protected UsefulData usefulData;
    private volatile boolean waitingForCloseGameNotification;
    private TokenColor userTokenColor;
    String input;
    private final Stage stageUI;

    public BaseClient(String mode, Stage stage) {
        this.stageUI = stage;
        switch (mode) {
            case "GUI":
                setGUIMode(true);
                setCurrentState(new MainMenuStateGUI(stage, this));
                break;
            case "TUI":
                setGUIMode(false);
                setScan(new Scanner(System.in));
                setCurrentState(new MainMenuState(this, getScan()));
                break;
            default:
                throw new IllegalArgumentException("Unsupported mode");
        }
        input = "";
        waitingForCloseGameNotification = false;
        notificationsQueue = new LinkedBlockingQueue<>();
        usefulData = UsefulData.getInstance();
        new Thread(() -> {
            try {
                notificationsHandler();
            } catch (InterruptedException | RemoteException e) {
                //throw new RuntimeException(e);
                System.out.println(e.getMessage());
                e.printStackTrace();

            }
        }).start();
    }

    public void setUserTokenColor(TokenColor tokenColor) {
        this.userTokenColor = tokenColor;
    }

    public TokenColor getTokenColor() {
        return userTokenColor;
    }

    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public void setGUIMode(boolean GUIMode) {
        isGUIMode = GUIMode;
    }

    public boolean isGUIMode() {
        return isGUIMode;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setScan(Scanner scan) {
        this.scan = scan;
    }

    public Scanner getScan() {
        return scan;
    }

    public ClientState getClientCurrentState() {
        return currentState;
    }

    public void setCurrentState(ClientState currentState) {
        this.currentState = currentState;
    }

    //   public abstract void sendHeartBeat() throws IOException, InterruptedException;

    protected abstract boolean checkState(RequestedActions action) throws IOException, InterruptedException, ClassNotFoundException;

    public abstract void close() throws IOException, InterruptedException;

    public abstract void returnToLobby() throws IOException, InterruptedException;

    public abstract PlayerState getCurrentPlayerState() throws IOException, InterruptedException;

    protected abstract String getServerCurrentState() throws IOException, InterruptedException;

    public synchronized void onTokenColorSelected(String msg, ArrayList<TokenColor> availableColors) {
        if (!isGUIMode) {
            System.out.println(msg);
            if (getClientCurrentState() instanceof ColorSelection)
                ((ColorSelection) getClientCurrentState()).refresh(availableColors);
        } else {
            if (getClientCurrentState() instanceof ColorSelectionGUI)
                ((ColorSelectionGUI) getClientCurrentState()).refresh(availableColors);
        }
    }

    public synchronized void onGameJoined(ArrayList<Player> players, int nOfMissingPlayers) {
        if (!isGUIMode) {
            if (getClientCurrentState() instanceof WaitingForPlayersState)
                ((WaitingForPlayersState) getClientCurrentState()).refresh(players, nOfMissingPlayers);
        } else if (getClientCurrentState() instanceof WaitingForPlayersGUI)
            ((WaitingForPlayersGUI) getClientCurrentState()).refresh(players, nOfMissingPlayers);

    }

    public synchronized void onGameJoinedAsOutsider(String message, HashMap<Integer, Integer[]> availableGames) {
        if (!isGUIMode) {
            System.out.println(message);
            if (getClientCurrentState() instanceof JoinGameMenuState)
                ((JoinGameMenuState) getClientCurrentState()).refresh(availableGames);
        } else if (getClientCurrentState() instanceof JoinGameMenuStateGUI)
            ((JoinGameMenuStateGUI) getClientCurrentState()).refresh(availableGames);
    }

    public synchronized void onChangeTurn(String msg, String currentPlayerUsername) {
        if (!isGUIMode) {
            System.out.println(msg);
            if (getClientCurrentState() == null) {
                display();
                System.out.println("Type your command or 'exit' to quit:");
            } else if (currentPlayerUsername.equals(username))
                System.out.println("You can go back to the main menu to play a card");
        } else if (getClientCurrentState() instanceof GameStateGUI)
            ((GameStateGUI) getClientCurrentState()).changeTurnNotified(currentPlayerUsername);
    }

    public synchronized void onPlayedCard(String msg, HashMap<String, Integer> playersPoints, String username) {
        if (!isGUIMode) {
            System.out.println(msg);
            if (getClientCurrentState() instanceof ShowPointsState) {
                ((ShowPointsState) getClientCurrentState()).refresh(playersPoints);
            } else if (getClientCurrentState() instanceof GetOtherPlayerDesk) {
                System.out.println("You can see the updated desk by choosing " + username + "'s desk");
            }
        } else if (getClientCurrentState() instanceof GameStateGUI)
            ((GameStateGUI) getClientCurrentState()).cardPlayedRefresh(username);
    }

    public synchronized void onLastTurnSet(String username) {
        if (!isGUIMode) {
            System.out.println("\n----------------------------------\n" +
                    "Player " + username + " reached 20 points\n" +
                    "The last turn has begun\n");
        } else if (getClientCurrentState() instanceof GameStateGUI)
            ((GameStateGUI) getClientCurrentState()).lastTurnSetNotification(username);
    }

    public synchronized void onEndGame(String msg) {
        if (!isGUIMode) {
            System.out.println(msg);
            if (getClientCurrentState() == null) {
                display();
                System.out.println("Type your command or 'exit' to quit:");
            } else
                System.out.println("You can go back to the main menu to see the winner");
        } //else
        //  getClientCurrentState().refresh(msg);
    }

    public synchronized void onGameClosed(String msg) {
        //to avoid trying close game already closed
        idGame = null;
        waitingForCloseGameNotification = true;
        if (isGUIMode) {
            Platform.runLater(() -> {
                try {
                    setCurrentState(new LobbyMenuStateGUI(this.stageUI, this));
                    getClientCurrentState().display();
                    ((LobbyMenuStateGUI) getClientCurrentState()).gameClosedNotification(msg);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }


    public void onChatMessageReceived(Message msg) {
        if (!isGUIMode) {
            if (getClientCurrentState() instanceof GlobalChatState || getClientCurrentState() instanceof PrivateChatState || getClientCurrentState() instanceof ChatState) {
                if (msg.getSender().equals(username))
                    System.out.println("You: " + msg.getContent());
                else
                    System.out.println(msg.getSender() + ": " + msg.getContent());
            } else {
                if (msg.getReceiver() == null)
                    System.out.println("You have received a message from the global chat");
                else
                    System.out.println("You have received a from " + msg.getSender());
            }
        }
    }

    @Override
    public void update(ServerNotification notification) {
        notificationsQueue.add(notification);
    }

    protected void inputHandler() throws IOException, ClassNotFoundException, InterruptedException {

        boolean correctInput;
        do {
            if (!input.equals("exit"))
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

            if (handleCommonInput(input)) {
                try {
                    handleTUIInput(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Please enter a number.");
                } catch (Exception e) {
                    System.out.println("The game is closing wait...");
                }
            }
            if (waitingForCloseGameNotification) {
                waitingForCloseGameNotification = false;
                setCurrentState(new LobbyMenuState(this, scan));
                input = "";
                inputHandler();
            }
        } while (currentState != null);
        while (true) {
            if (currentState == null) {
                try {
                    display();
                } catch (Exception e) {
                    break;
                }
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
            if (handleCommonInput(input)) {
                try {
                    boolean checkState;
                    if (currentState == null)
                        checkState = gameLogicInputHandler(Integer.parseInt(input));
                    else
                        checkState = true;
                    if (checkState) {
                        synchronized (this) {
                            if (!waitingForCloseGameNotification)
                                showState();
                        }
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
                        if (handleCommonInput(input)) {
                            synchronized (this) {
                                try {
                                    handleTUIInput(Integer.parseInt(input));
                                } catch (NumberFormatException e) {
                                    //TODO to catch the exception in case the player finishes his move while the game is being closed.
                                    System.out.println("Invalid input: Please enter a number.");
                                } catch (Exception e) {
                                    System.out.println("The game is closing wait...");
                                }
                            }
                        } else {
                            if (input.equals("exit")) {
                                System.out.println("The game is closing, please wait...");
                                while (!waitingForCloseGameNotification)
                                    Thread.onSpinWait();

                                setCurrentState(new LobbyMenuState(this, scan));
                                input = "";
                                waitingForCloseGameNotification = false;
                                inputHandler();
                            } else
                                System.out.println("The input was not valid. You can " + getServerCurrentState());
                        }
                    } else {
                        System.out.println("The input was not valid. You can " + getServerCurrentState());
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Please enter a number.");
                } catch (Exception e) {
                    //There's too many runtime exceptions to catch so i GOTTA CATCH 'EM ALL
                    System.out.println("The game is closing wait... Click a button to continue.");
                }
                if (waitingForCloseGameNotification) {
                    waitingForCloseGameNotification = false;
                    if (!isGUIMode)
                        setCurrentState(new LobbyMenuState(this, scan));
                    else {
                        //      setCurrentState(new LobbyMenuStateGUI(new Stage(), this));
                        //      ((LobbyMenuStateGUI) getClientCurrentState()).refresh(msg);
                    }
                    input = "";
                    inputHandler();
                }
            } else {
                //to avoid doing the else branch before the close notification has arrived
                if (input.equals("exit")) {
                    System.out.println("The game is closing, please wait...");
                    while (!waitingForCloseGameNotification) {
                        Thread.onSpinWait();
                    }
                    if (!isGUIMode)
                        setCurrentState(new LobbyMenuState(this, scan));
                    else {
                        setCurrentState(new LobbyMenuStateGUI(new Stage(), this));
                        //      ((LobbyMenuStateGUI) getClientCurrentState()).refresh(msg);
                    }
                    input = "";
                    waitingForCloseGameNotification = false;
                    inputHandler();
                } else
                    System.out.println("The input was not valid. You can " + getServerCurrentState());

            }
        }
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


    private synchronized void display() {
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
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


    private boolean handleCommonInput(String input) {
        if ("exit".equals(input)) {
            try {
                if (getClientCurrentState() != null && usefulData.quitableStates.contains(currentState.toString())) {
                    System.out.println("Game has been successfully quit.");
                    close();
                } else {
                    System.out.println("Ending the game and returning to the lobby...");
                    returnToLobby();
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("An error occurred while handling the input: " + e.getMessage());
            }
            return false;
        }
        return true;
    }
/*
    public void receiveMessage(Message msg) throws RemoteException {
        if (currentState instanceof GlobalChatState || currentState instanceof PrivateChatState) {
            if (msg.getSender().equals(username))
                System.out.println("You: " + msg.getContent());
            else
                System.out.println(msg.getSender() + ": " + msg.getContent());
        } else {
            if (msg.getReceiver() == null)
                System.out.println("You have received a message");
            else
                System.out.println("You have received a from " + msg.getSender());
        }
    }*/

    @SuppressWarnings("InfiniteLoopStatement")
    public void notificationsHandler() throws RemoteException, InterruptedException {
        while (true) {
            ServerNotification msg = notificationsQueue.take();
            try {
                msg.notifyClient(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleTUIInput(int input) throws ClassNotFoundException, InterruptedException, IOException {
        try {
            if (currentState instanceof ClientStateTUI tuiState)
                tuiState.inputHandler(input);
        } catch (RemoteException e) {
            System.out.println("The server has crashed, thanks for playing");
            System.exit(0);
        }
    }

    public void showState() {
        try {
            if (getClientCurrentState() instanceof ClientStateTUI clientStateTUI) {
                clientStateTUI.display();
                clientStateTUI.promptForInput();
            }
        } catch (RemoteException e) {
            System.out.println("The server has crashed, thanks for playing");

        }
    }
}
