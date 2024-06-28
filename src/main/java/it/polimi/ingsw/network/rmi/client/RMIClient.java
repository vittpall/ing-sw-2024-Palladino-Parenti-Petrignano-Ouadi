package it.polimi.ingsw.network.rmi.client;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.remoteInterfaces.VirtualServer;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is the RMI client that extends the BaseClient class and implements the Remote interface
 */
public class RMIClient extends BaseClient {
    public final VirtualServer server;
    private int idClientIntoGame;
    private static final int MAX_RETRIES = 2; // Maximum number of retries

    /**
     * Constructor
     *
     * @param server is a reference to the VirtualServer
     * @param mode   is the mode of the client (TUI or GUI)
     * @param stage  is the stage of the GUI
     * @throws RemoteException if the remote operation fails
     */
    public RMIClient(VirtualServer server, String mode, Stage stage) throws RemoteException {
        super(mode, stage);
        UnicastRemoteObject.exportObject(this, 0);
        this.server = server;
    }

    @Override
    public int getIdClientIntoGame() {
        return idClientIntoGame;
    }


    @Override
    public boolean checkUsername(String username) throws IOException {
        return server.checkUsername(username, this);
    }

    @Override
    public ArrayList<Integer> getNotStartedGames() throws RemoteException {
        return server.getNotStartedGames(this);
    }

    @Override
    public ArrayList<Player> getAllPlayers() throws RemoteException {
        return server.getAllPlayers(getIdGame());
    }

    @Override
    public void joinGame(int input, String username) throws IOException, InterruptedException {
        idClientIntoGame = server.joinGame(input, username, this);
        setIdGame(input);
    }

    @Override
    public ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws RemoteException {
        return server.getPlayerObjectiveCards(getIdGame(), idClientIntoGame, this);
    }

    @Override
    public void setObjectiveCard(int idCard) throws RemoteException {
        server.setObjectiveCard(getIdGame(), idClientIntoGame, idCard);
    }

    @Override
    public void createGame(String username, int nPlayers) throws IOException, InterruptedException {
        setIdGame(server.createGame(username, nPlayers, this));
        idClientIntoGame = 0;
    }

    @Override
    public StarterCard getStarterCard() throws RemoteException {
        return server.getStarterCard(getIdGame(), idClientIntoGame);
    }

    @Override
    public void playStarterCard(boolean playedFacedDown)
            throws RemoteException, RequirementsNotMetException, PlaceNotAvailableException {
        server.playStarterCard(getIdGame(), idClientIntoGame, playedFacedDown, this);
    }

    @Override
    public ObjectiveCard getPlayerObjectiveCard() throws RemoteException {
        return server.getPlayerObjectiveCard(getIdGame(), idClientIntoGame);
    }

    @Override
    public ArrayList<GameCard> getPlayerHand() throws RemoteException {
        return server.getPlayerHand(getIdGame(), idClientIntoGame);
    }

    @Override
    public ObjectiveCard[] getSharedObjectiveCards() throws RemoteException {
        return server.getSharedObjectiveCards(getIdGame());
    }

    @Override
    public int playCard(int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException {
        return server.playCard(getIdGame(), idClientIntoGame, chosenCard, faceDown, chosenPosition);
    }

    @Override
    public ArrayList<GameCard> getVisibleCardsDeck(int deck) throws RemoteException {
        return server.getVisibleCardsDeck(getIdGame(), deck);
    }

    @Override
    public GameCard getLastFromUsableCards(int deck) throws RemoteException {
        return server.getLastCardOfUsableCards(getIdGame(), deck);
    }

    @Override
    public ArrayList<Message> getMessages(String receiver) throws RemoteException {
        return server.getMessages(receiver, this.getIdGame(), getUsername());
    }

    @Override
    public void sendMessage(String receiver, String message) throws RemoteException {
        Message msg = new Message(getUsername(), receiver, message, getIdGame(), userTokenColor);
        server.sendMessage(getIdGame(), msg);
    }


    @Override
    public void drawCard(int input, int inVisible) throws IOException, InterruptedException {
        server.drawCard(getIdGame(), idClientIntoGame, input, inVisible);
    }


    @Override
    public HashSet<Point> getAvailablePlaces() throws RemoteException {
        return server.getAvailablePlaces(getIdGame(), idClientIntoGame);
    }

    @Override
    public String getWinner() throws RemoteException, InterruptedException {
        return server.getWinner(getIdGame());
    }

    @Override
    public ArrayList<TokenColor> getAvailableColors() throws RemoteException {
        return server.getAvailableColors(getIdGame(), this);
    }

    @Override
    public void setTokenColor(TokenColor tokenColor) throws IOException {
        TokenColor token = server.setTokenColor(getIdGame(), idClientIntoGame, tokenColor);
        setUserTokenColor(token);
    }

    @Override
    public int getPoints() throws RemoteException {
        return server.getPoints(getIdGame(), idClientIntoGame);
    }

    @Override
    public HashMap<Point, GameCard> getPlayerDesk() throws RemoteException {
        return this.server.getPlayerDesk(getIdGame(), idClientIntoGame);
    }

    @Override
    public void run() throws IOException {
        this.server.connect(this);
        AtomicInteger retryCount = new AtomicInteger(0);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            try {
                if (server.isAlive()) {
                    retryCount.set(0);
                } else {
                    retryCount.incrementAndGet();
                }

            } catch (RemoteException e) {
                if (retryCount.incrementAndGet() >= MAX_RETRIES) {
                    closeClient();
                }
            }
        }, 0, 5000, java.util.concurrent.TimeUnit.MILLISECONDS);

        if (!isGUIMode())
            inputHandler();
        else
            getClientCurrentState().display();
    }

    void closeClient() {
        System.out.println("The server has crashed, thanks for playing");
        System.exit(0);
    }

    @Override
    public boolean isGameStarted() throws IOException {
        return server.isGameStarted(getIdGame());
    }

    @Override
    public String getGameState() throws RemoteException {
        return server.getGameState(getIdGame());
    }

    @Override
    public PlayerState getCurrentPlayerState() throws IOException, InterruptedException {
        return server.getCurrentPlayerState(getIdGame(), idClientIntoGame);
    }

    @Override
    public String getServerCurrentState() throws RemoteException {
        return server.getServerCurrentState(getIdGame(), idClientIntoGame);
    }

    @Override
    public boolean checkState(RequestedActions requestedActions) throws RemoteException {
        return server.checkState(getIdGame(), idClientIntoGame, requestedActions);
    }

    @Override
    public void close() {
        try {
            removeUsername();
            returnToLobby();
        } catch (IOException e) {
            System.out.println("The server is already closed, no need to remove the username");
        }
        System.exit(0);
    }


    /**
     * This method returns the client to the lobby
     *
     * @throws IOException if the server is not reachable
     */
    public void returnToLobby() throws IOException {
        if (getIdGame() != null)
            this.closeGame();
        setIdGameNull();
    }


    /**
     * This method removes the username from the server
     *
     * @throws RemoteException if the remote operation fails
     */
    public void removeUsername() throws RemoteException {
        server.removeUsername(getUsername());
    }


    @Override
    public void closeGame() throws IOException {
        server.closeGame(getIdGame(), username);
    }


    @Override
    public int getnPlayer(int idGame) throws IOException {
        return server.getnPlayer(idGame);
    }

    @Override
    public ArrayList<Player> getPlayers(int idGame) throws IOException {
        ArrayList<Player> x = null;
        try {
            x = server.getPlayers(idGame);
        } catch (NullPointerException e) {
            System.out.println("Error while getting players" + e.getMessage());
        }
        return x;
    }

    @Override
    public void ping() throws RemoteException {
        //This method does nothing but just to keep track if the client has been disconnected or not
    }


}
