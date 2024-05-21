package it.polimi.ingsw.network.RemoteInterfaces;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface VirtualServer extends Remote {
    void connect(VirtualView client) throws RemoteException;


    boolean checkUsername(String username) throws RemoteException;

    ArrayList<Integer> getNotStartedGames() throws RemoteException;

    ArrayList<Player> getAllPlayers(int gameId) throws RemoteException;

    ArrayList<Message> getMessages(String receiver, int gameId, String sender) throws RemoteException;

    void sendMessage(int idGame, Message msg) throws RemoteException;

    int joinGame(int id, String username) throws RemoteException, InterruptedException;

    int createGame(String username, int nPlayers) throws RemoteException, InterruptedException;

    void removeUsername(String username) throws RemoteException;

    ArrayList<ObjectiveCard> getPlayerObjectiveCards(int idGame, int idPlayer) throws RemoteException;

    void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard) throws RemoteException, CardNotFoundException;

    StarterCard getStarterCard(int idGame, int idClientIntoGame) throws RemoteException;

    void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown)
            throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException;

    ObjectiveCard getPlayerObjectiveCard(int idGame, int idClientIntoGame) throws RemoteException;

    ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) throws RemoteException;

    ObjectiveCard[] getSharedObjectiveCards(int idGame) throws RemoteException;

    int getCurrentPlayer(int idGame) throws RemoteException;

    void playCard(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException;

    void drawCard(int idGame, int idClientIntoGame, int deckToChoose, int inVisible) throws IOException, CardNotFoundException, InterruptedException;

    void waitForYourTurn(int idGame, int idClientIntoGame) throws RemoteException, InterruptedException;

    boolean getIsLastRoundStarted(int idGame) throws RemoteException;

    HashSet<Point> getAvailablePlaces(int idGame, int idClientIntoGame) throws RemoteException;

    ArrayList<TokenColor> getAvailableColors(int idGame) throws RemoteException;

    void setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor) throws RemoteException;

    int getPoints(int idGame, int idClientIntoGame) throws RemoteException;

    ArrayList<GameCard> getVisibleCardsDeck(int idGame, int deck) throws RemoteException;

    Card getLastCardOfUsableCards(int idGame, int deck) throws RemoteException;

    String getUsernamePlayerThatStoppedTheGame(int idGame) throws RemoteException;

    HashMap<Point, GameCard> getPlayerDesk(int idGame, int idClientIntoGame) throws RemoteException;

    String getWinner(int idGame, int idClientIntoGame) throws RemoteException, InterruptedException;

    void playLastTurn(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException;

    void closeGame(int idGame) throws RemoteException;

    int getnPlayer(int idGame) throws RemoteException;

    ArrayList<Player> getPlayers(int idGame) throws RemoteException;

    String getCurrentState(int idGame, int idClientIntoGame) throws RemoteException;

    boolean checkState(int idGame, int idClientIntoGame, RequestedActions requestedActions) throws RemoteException;

    boolean isGameStarted(int idGame) throws RemoteException;
}
