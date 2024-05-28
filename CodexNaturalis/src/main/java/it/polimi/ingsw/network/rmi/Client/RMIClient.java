package it.polimi.ingsw.network.rmi.Client;

import it.polimi.ingsw.gui.JoinGameMenuStateGUI;
import it.polimi.ingsw.gui.MainMenuStateGUI;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.tui.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RMIClient extends UnicastRemoteObject implements VirtualView, GameListener, Serializable {
    public final VirtualServer server;
    ClientState currentState;
    private String username;
    private Scanner scan;
    private int idGame;
    private int idClientIntoGame;
    private final boolean isGUIMode;

    public RMIClient(VirtualServer server, String mode, Stage stage) throws RemoteException {

        this.server = server;
        switch (mode) {
            case "GUI":
                isGUIMode = true;
                setCurrentState(new MainMenuStateGUI(stage, this));
                break;
            case "TUI":
                isGUIMode = false;
                this.scan = new Scanner(System.in);
                setCurrentState(new MainMenuState(this, scan));
                break;
            default:
                throw new IllegalArgumentException("Unsupported mode");
        }
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getIdGame() {
        return idGame;
    }

    public int getIdClientIntoGame() {
        return idClientIntoGame;
    }

    @Override
    public void receiveMessage(Message msg) throws RemoteException {
        if (currentState instanceof GlobalChatState || currentState instanceof PrivateChatState) {
            if (msg.getSender().equals(this.username))
                System.out.println("You: " + msg.getContent());
            else
                System.out.println(msg.getSender() + ": " + msg.getContent());
        } else {
            if (msg.getReceiver() == null)
                System.out.println("You have received a message");
            else
                System.out.println("You have received a from " + msg.getSender());
        }
    }

    @Override
    public void receiveNotification(ReturnableObject msg) {
        System.out.println(msg.getResponseReturnable());
    }

    @Override
    public boolean checkUsername(String username) throws IOException {
        return server.checkUsername(username, (GameListener) this);
    }

    @Override
    public ArrayList<Integer> getNotStartedGames() throws RemoteException {
        return server.getNotStartedGames();
    }

    @Override
    public ArrayList<Player> getAllPlayers() throws RemoteException {
        return server.getAllPlayers(idGame);
    }

    @Override
    public void joinGame(int input, String username) throws IOException, InterruptedException {
        idClientIntoGame = server.joinGame(input, username, this);
        idGame = input;

    }

    @Override
    public ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws RemoteException {
        return server.getPlayerObjectiveCards(idGame, idClientIntoGame);
    }

    @Override
    public void setObjectiveCard(int idCard) throws RemoteException, CardNotFoundException {
        server.setObjectiveCard(idGame, idClientIntoGame, idCard);
    }

    @Override
    public void createGame(String username, int nPlayers) throws IOException, InterruptedException {
        idGame = server.createGame(username, nPlayers, this);
        idClientIntoGame = 0;
    }

    @Override
    public StarterCard getStarterCard() throws RemoteException {
        return server.getStarterCard(idGame, idClientIntoGame);
    }

    @Override
    public void playStarterCard(boolean playedFacedDown)
            throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        server.playStarterCard(idGame, idClientIntoGame, playedFacedDown);
    }

    @Override
    public ObjectiveCard getPlayerObjectiveCard() throws RemoteException {
        return server.getPlayerObjectiveCard(idGame, idClientIntoGame);
    }

    @Override
    public ArrayList<GameCard> getPlayerHand() throws RemoteException {
        return server.getPlayerHand(idGame, idClientIntoGame);
    }

    @Override
    public ObjectiveCard[] getSharedObjectiveCards() throws RemoteException {
        return server.getSharedObjectiveCards(idGame);
    }

    @Override
    public String getNextState() throws RemoteException {
        if (currentState instanceof DrawCardState) {
            if (server.getIsLastRoundStarted(idGame))
                return "LastRoundState";
            else
                return "WaitForYourTurnState";
        }
        return "Error";
    }

    @Override
    public void playCard(int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        server.playCard(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
    }

    @Override
    public ArrayList<GameCard> getVisibleCardsDeck(int deck) throws RemoteException {
        return server.getVisibleCardsDeck(idGame, deck);
    }

    @Override
    public Card getLastFromUsableCards(int deck) throws RemoteException {
        return server.getLastCardOfUsableCards(idGame, deck);
    }

    @Override
    public String getUsernamePlayerThatStoppedTheGame() throws RemoteException {
        return server.getUsernamePlayerThatStoppedTheGame(idGame);
    }

    public ArrayList<Message> getMessages(String receiver) throws RemoteException {
        return server.getMessages(receiver, this.idGame, this.username);
    }

    public void sendMessage(String receiver, String message) throws RemoteException {
        Message msg = new Message(this.username, receiver, message, this.idGame);
        server.sendMessage(idGame, msg);
    }


    @Override
    public void drawCard(int input, int inVisible) throws IOException, CardNotFoundException, InterruptedException {
        server.drawCard(idGame, idClientIntoGame, input, inVisible);
    }


    @Override
    public HashSet<Point> getAvailablePlaces() throws RemoteException {
        return server.getAvailablePlaces(idGame, idClientIntoGame);
    }

    @Override
    public String getWinner() throws RemoteException, InterruptedException {
        return server.getWinner(idGame);
    }

    @Override
    public ArrayList<TokenColor> getAvailableColors() throws RemoteException {
        return server.getAvailableColors(idGame, (GameListener) this);
    }

    @Override
    public void setTokenColor(TokenColor tokenColor) throws IOException {
        server.setTokenColor(idGame, idClientIntoGame, tokenColor);
    }

    public int getPoints() throws RemoteException {
        return server.getPoints(idGame, idClientIntoGame);
    }

    @Override
    public HashMap<Point, GameCard> getPlayerDesk() throws RemoteException {
        return this.server.getPlayerDesk(idGame, idClientIntoGame);
    }

    @Override
    public void run() throws IOException, ClassNotFoundException, InterruptedException {
        this.server.connect(this);
        if (!isGUIMode)
            inputHandler();
        else
            showState();
    }

    public boolean isGameStarted() throws RemoteException {
        return server.isGameStarted(idGame);
    }

    @Override
    public PlayerState getCurrentPlayerState() throws RemoteException {
        return server.getCurrentPlayerState(idGame, idClientIntoGame);
    }

    @Override
    public void showState() {
        currentState.display();
        currentState.promptForInput();
    }

    void inputHandler() throws IOException, ClassNotFoundException, InterruptedException {
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
        } while (currentState !=null);
        while (true) {
            if(currentState==null){
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
            }else
                input = "no display";

            if (!handleCommonInput(input)) {
                try {
                    boolean checkState;
                    if(currentState==null)
                        checkState = gameLogicInputHandler(Integer.parseInt(input));
                    else
                        checkState=true;
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
                        System.out.println("The input was not valid. You can " + server.getCurrentState(idGame, idClientIntoGame));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Please enter a number.");
                }
            }
        }
    }

    private boolean gameLogicInputHandler(int i) {
        try {
            boolean checkState;
            switch (i) {
                case 1:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.PLAY_CARD);
                    if (checkState) currentState = new PlayCardState(this, scan);
                    return checkState;

                case 2:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.DRAW);
                    if (checkState) currentState = new DrawCardState(this, scan);
                    return checkState;
                case 3:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.SHOW_DESKS);
                    if (checkState) currentState = new GetOtherPlayerDesk(this, scan);
                    return checkState;
                case 4:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.SHOW_OBJ_CARDS);
                    if (checkState) currentState = new ShowObjectiveCardsState(this, scan);
                    return checkState;
                case 5:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.SHOW_POINTS);
                    if (checkState) currentState = new ShowPointsState(this, scan);
                    return checkState;
                case 6:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.CHAT);
                    if (checkState) currentState = new ChatState(this, scan);
                    return checkState;
                case 7:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.SHOW_WINNER);
                    if (checkState) currentState = new GetWinnerState(this, scan);
                    return checkState;
                default:
                    return false;
            }
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void display() {
        System.out.println("⚔️  _________________________________  ⚔️");
        System.out.println("|                                       |");
        System.out.println("|   Please choose an action:            |");
        try{
            if (server.getCurrentPlayerState(idGame, idClientIntoGame).equals(PlayerState.PLAY_CARD))
                System.out.println("|   1. Play a card🎮                    |");

            else if (server.getCurrentPlayerState(idGame, idClientIntoGame).equals(PlayerState.DRAW))
                System.out.println("|   2. Draw a card🎴                    |");
            System.out.println("|   3. Show your desk or others' desks  |");
            System.out.println("|   4. Show the shared objective cards  |");
            System.out.println("|      and your objective card          |");
            System.out.println("|   5. Show players' points             |");
            System.out.println("|   6. Chat 💬                          |");
            if (server.getCurrentPlayerState(idGame, idClientIntoGame).equals(PlayerState.ENDGAME))
                System.out.println("|   7. Show winner                      |");
            System.out.println("|_______________________________________|\n");
        }catch(RemoteException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() throws RemoteException {
        server.removeUsername(username);
        System.exit(0);
    }

    public void removeUsername() throws RemoteException {
        server.removeUsername(username);
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public void setCurrentState(ClientState state) {
        this.currentState = state;
    }


    private boolean handleCommonInput(String input) {
        if ("exit".equals(input)) {
            try {
                System.out.println("Exiting game...");
                close();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


    @Override
    public void closeGame() throws RemoteException {
        server.closeGame(idGame);
    }


    @Override
    public int getnPlayer(int idGame) throws IOException {
        return server.getnPlayer(idGame);
    }

    @Override
    public ArrayList<Player> getPlayers(int idGame) throws IOException {
        return server.getPlayers(idGame);
    }

    /**
     *
     */
    @Override
    public void update(ReturnableObject messageToShow) throws RemoteException {
        //TODO logic to implement. I would consider to use a switch case to handle the different states

        switch (currentState.toString()) {
            case "JoinGameMenuState":
                currentState.display();
                break;
            case "WaitingForPlayersState":
                currentState.display();
                break;
            case "ColorSelection":
                currentState.display();
                break;
                case "JoinGameMenuStateGUI":
                    currentState.refresh();
                break;

            default:
                break;

        }

    }

    /**
     * @throws IOException
     */
    @Override
    public void updateSelectedColor() throws IOException {
        this.currentState.display();
    }
}
