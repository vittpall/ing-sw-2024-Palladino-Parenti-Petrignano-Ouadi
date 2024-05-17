package it.polimi.ingsw.network.rmi.Client;

import it.polimi.ingsw.gui.MainMenuStateGUI;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RMIClient extends UnicastRemoteObject implements VirtualView {
    public final VirtualServer server;
    ClientState currentState;
    private String username;
    private Scanner scan;
    private int idGame;
    private int idClientIntoGame;
    private boolean isGUIMode = false;

    public RMIClient(VirtualServer server, String mode, Stage stage) throws RemoteException {

        this.server = server;
        switch (mode) {
            case "GUI":
                isGUIMode = true;
                setCurrentState(new MainMenuStateGUI(stage, this));
                break;
            case "TUI":
                this.scan = new Scanner(System.in);
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
    public boolean checkUsername(String username) throws IOException {
        return server.checkUsername(username);
    }

    @Override
    public ArrayList<Integer> getNotStartedGames() throws RemoteException {
        return server.getNotStartedGames();
    }

    public ArrayList<Player> getAllPlayers(int gameId) throws RemoteException {
        return server.getAllPlayers(gameId);
    }

    @Override
    public void joinGame(int input, String username) throws RemoteException, InterruptedException {
        idClientIntoGame = server.joinGame(input, username);
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
    public void createGame(String username, int nPlayers) throws RemoteException, InterruptedException {
        idGame = server.createGame(username, nPlayers);
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
        if (currentState instanceof InitializeStarterCardState) {
            if (server.getCurrentPlayer(idGame) == idClientIntoGame)
                return "PlayCardState";
            else
                return "WaitForYourTurnState";
        } else if (currentState instanceof DrawCardState) {
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
    public void playLastTurn(int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        server.playLastTurn(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
    }

    @Override
    public ArrayList<GameCard> getVisibleCardsDeck(int deck) throws RemoteException {
        return server.getVisibleCardsDeck(idGame, deck);
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
        server.sendMessage(msg);
    }

    @Override
    public ClientState getCurrentState() throws RemoteException {
        return currentState;
    }

    @Override
    public void drawCard(int input, int inVisible) throws IOException, CardNotFoundException, InterruptedException {
        server.drawCard(idGame, idClientIntoGame, input, inVisible);
    }

    @Override
    public void waitForYourTurn() throws RemoteException, InterruptedException {
        server.waitForYourTurn(idGame, idClientIntoGame);
    }

    @Override
    public HashSet<Point> getAvailablePlaces() throws RemoteException {
        return server.getAvailablePlaces(idGame, idClientIntoGame);
    }

    @Override
    public String getWinner() throws RemoteException, InterruptedException {
        return server.getWinner(idGame, idClientIntoGame);
    }

    @Override
    public ArrayList<TokenColor> getAvailableColors() throws RemoteException {
        return server.getAvailableColors(idGame);
    }

    @Override
    public void setTokenColor(TokenColor tokenColor) throws RemoteException {
        server.setTokenColor(idGame, idClientIntoGame, tokenColor);
    }

    public int getPoints() throws RemoteException {
        return server.getPoints(idGame, idClientIntoGame);
    }

    @Override
    public HashMap<Point, GameCard> getPlayerDesk() throws RemoteException {
        return this.server.getPlayerDesk(idGame, idClientIntoGame);
    }

    public void run() throws IOException, ClassNotFoundException, InterruptedException {
        this.server.connect(this);
        if (!isGUIMode)
            setCurrentState(new MainMenuState(this, scan));

        new Thread(() -> {
            try {
                inputHandler();
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    void showState() {
        currentState.display();
        currentState.promptForInput();
    }

    void inputHandler() throws IOException, ClassNotFoundException, InterruptedException {
        boolean correctInput = false;
        String input = "";
        do{
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
        }while(currentState instanceof ColorSelection);
        while(true){
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

            if (!handleCommonInput(input)) {
                try {
                    boolean checkState=gameLogicInputHandler(Integer.parseInt(input));
                    if(checkState){
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
                                //TODO: vedere se cambiare qui il current state oppure nel gameLogicInputHandler
                                currentState.inputHandler(Integer.parseInt(input));
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input: Please enter a number.");
                            }
                        }
                    }else{
                        System.out.println("The input was not valid. You can "+ server.getCurrentState(idGame, idClientIntoGame));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Please enter a number.");
                }
            }
        }
    }

    private boolean gameLogicInputHandler(int i) {
        try{
            boolean checkState = false;
            switch (i) {
                //probabilmente è meglio mandare l'input al server e poi è il GameController che gestisce lo stato di richiesta
                //per questo ho commentato la chiamata al metodo checkState
                case 1:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.DRAW);
                    if (checkState) currentState = new DrawCardState(this, scan);
                    return checkState;
                case 2:
                    checkState = server.checkState(idGame, idClientIntoGame, RequestedActions.PLAY_CARD);
                    if (checkState) currentState = new PlayCardState(this, scan);
                    return checkState;
                case 3:
                    //TODO: da fare simile
                case 4:
                    //TODO: da fare simile
                case 5:
                    //TODO: da fare simile
                case 6:
                    //TODO: da fare simile
                case 7:
                    //TODO: da fare simile
                default:
                    return false;
            }
        }catch(RemoteException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void display(){
        System.out.println("1- Draw a card");
        System.out.println("2- Play a card");
        System.out.println("3- Show your desk and others' desks");
        System.out.println("4- Show the shared objective cards and your objective card");
        System.out.println("5- Show players' points");
        System.out.println("6- Chat");
        System.out.println("8- Set your token color");
        System.out.println("9- Set your objective card");
        System.out.println("10- Set your starter card");
    }
    public void close() throws RemoteException {
        server.removeUsername(username);
        System.exit(0);
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
    public void notifyYourTurn() throws RemoteException {
        setCurrentState(new PlayCardState(this, scan));
    }

    @Override
    public int getnPlayer(int idGame) throws IOException, InterruptedException {
        return server.getnPlayer(idGame);
    }

    @Override
    public ArrayList<Player> getPlayers(int idGame) throws IOException, InterruptedException {
        return server.getPlayers(idGame);
    }

}
