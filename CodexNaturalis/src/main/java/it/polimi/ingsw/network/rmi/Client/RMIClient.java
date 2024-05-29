package it.polimi.ingsw.network.rmi.Client;

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
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.tui.DrawCardState;
import it.polimi.ingsw.tui.MainMenuState;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class RMIClient extends BaseClient {
    public final VirtualServer server;
    private int idGame;
    private int idClientIntoGame;
    private final boolean isGUIMode;

    public RMIClient(VirtualServer server, String mode, Stage stage) throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        this.server = server;
        switch (mode) {
            case "GUI":
                isGUIMode = true;
                setCurrentState(new MainMenuStateGUI(stage, this));
                break;
            case "TUI":
                isGUIMode = false;
                setScan(new Scanner(System.in));
                setCurrentState(new MainMenuState(this, getScan()));
                break;
            default:
                throw new IllegalArgumentException("Unsupported mode");
        }
    }


    public int getIdGame() {
        return idGame;
    }

    public int getIdClientIntoGame() {
        return idClientIntoGame;
    }


    @Override
    public boolean checkUsername(String username) throws IOException {
        return server.checkUsername(username, this);
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
        if (getClientCurrentState() instanceof DrawCardState) {
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
        return server.getMessages(receiver, this.idGame, getUsername());
    }

    public void sendMessage(String receiver, String message) throws RemoteException {
        Message msg = new Message(getUsername(), receiver, message, this.idGame);
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
        return server.getAvailableColors(idGame, this);
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

    public boolean isGameStarted() throws IOException, InterruptedException {
        return server.isGameStarted(idGame);
    }

    @Override
    public PlayerState getCurrentPlayerState() throws IOException, InterruptedException {
        return server.getCurrentPlayerState(idGame, idClientIntoGame);
    }

    @Override
    public String getServerCurrentState() throws RemoteException {
        return server.getServerCurrentState(idGame, idClientIntoGame);
    }

    @Override
    public void showState() {
        getClientCurrentState().display();
        getClientCurrentState().promptForInput();
    }


    @Override
    public boolean checkState(RequestedActions requestedActions) throws RemoteException {
        return server.checkState(idGame, idClientIntoGame, requestedActions);
    }


    public void close() throws RemoteException {
        server.removeUsername(getUsername());
        System.exit(0);
    }

    public void removeUsername() throws RemoteException {
        server.removeUsername(getUsername());
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
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


    @Override
    public void onTokenColorSelected(String msg) throws RemoteException {
        if (!isGUIMode) {
            System.out.println(msg);
            getClientCurrentState().display();
        } else {
            getClientCurrentState().refresh(msg);
        }
    }

    @Override
    public void onGameJoined(String msg) throws RemoteException {
        if (!isGUIMode)
            System.out.println(msg);
        else
            getClientCurrentState().refresh(msg);

    }


    @Override
    public void onGameCreated() throws RemoteException {

    }

    @Override
    public void onChatMessageReceived() throws RemoteException {

    }
}
