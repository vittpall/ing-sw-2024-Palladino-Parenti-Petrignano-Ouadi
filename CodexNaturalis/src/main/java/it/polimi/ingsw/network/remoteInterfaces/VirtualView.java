package it.polimi.ingsw.network.remoteInterfaces;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface VirtualView extends Remote {

    ArrayList<Integer> getNotStartedGames() throws IOException, InterruptedException;

    void setObjectiveCard(int idCard) throws IOException, InterruptedException;

    void createGame(String username, int nPlayers) throws IOException, InterruptedException;

    Integer getIdGame() throws RemoteException;

    int getIdClientIntoGame() throws RemoteException;

    ArrayList<Player> getAllPlayers() throws IOException, InterruptedException;

    boolean checkUsername(String username) throws IOException, InterruptedException;

    void joinGame(int input, String username) throws IOException, InterruptedException;

    ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws IOException, InterruptedException;

    StarterCard getStarterCard() throws IOException, InterruptedException;

    void playStarterCard(boolean playedFacedDown) throws IOException, RequirementsNotMetException, PlaceNotAvailableException, InterruptedException;

    ObjectiveCard getPlayerObjectiveCard() throws IOException, InterruptedException;

    ArrayList<GameCard> getPlayerHand() throws IOException, InterruptedException;

    ObjectiveCard[] getSharedObjectiveCards() throws IOException, InterruptedException;

    int playCard(int chosenCard, boolean faceDown, Point chosenPosition)
            throws IOException, PlaceNotAvailableException, RequirementsNotMetException, InterruptedException;

    void drawCard(int input, int inVisible) throws IOException, InterruptedException;

    ArrayList<Message> getMessages(String receiver) throws IOException, InterruptedException;

    void sendMessage(String receiver, String input) throws IOException, InterruptedException;

    HashSet<Point> getAvailablePlaces() throws IOException, InterruptedException;

    HashMap<Point, GameCard> getPlayerDesk() throws IOException, InterruptedException;

    ArrayList<TokenColor> getAvailableColors() throws IOException, InterruptedException;

    void setTokenColor(TokenColor tokenColor) throws IOException, InterruptedException;

    int getPoints() throws IOException, InterruptedException;

    ArrayList<GameCard> getVisibleCardsDeck(int deck) throws IOException, InterruptedException;

    GameCard getLastFromUsableCards(int deck) throws IOException, InterruptedException;

    String getWinner() throws IOException, InterruptedException;

    void closeGame() throws IOException, InterruptedException;

    int getnPlayer(int idGame) throws IOException, InterruptedException;

    ArrayList<Player> getPlayers(int idGame) throws IOException, InterruptedException;

    void ping() throws RemoteException;

    void run() throws IOException;

    String getUsername() throws RemoteException;


    boolean isGameStarted() throws IOException, InterruptedException;
    String getGameState() throws InterruptedException, RemoteException;

    void close() throws RemoteException;

    PlayerState getCurrentPlayerState() throws IOException, InterruptedException;
}
