package it.polimi.ingsw.network.RemoteInterfaces;

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

import java.awt.*;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface VirtualServer extends Remote {

    void connect(VirtualView client) throws RemoteException;

    boolean checkUsername(String username, GameListener playerListener) throws RemoteException;

    ArrayList<Integer> getNotStartedGames(GameListener lobbyListener) throws RemoteException;

    ArrayList<Player> getAllPlayers(int gameId) throws RemoteException;

    ArrayList<Message> getMessages(String receiver, int gameId, String sender) throws RemoteException;

    void sendMessage(int idGame, Message msg) throws RemoteException;

    int joinGame(int id, String username, GameListener playerListener) throws IOException, InterruptedException;

    int createGame(String username, int nPlayers, GameListener playerListener) throws IOException, InterruptedException;

    void removeUsername(String username) throws RemoteException;

    ArrayList<ObjectiveCard> getPlayerObjectiveCards(int idGame, int idPlayer, GameListener playerListener) throws RemoteException;

    void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard, GameListener gameListener) throws RemoteException, CardNotFoundException;

    StarterCard getStarterCard(int idGame, int idClientIntoGame) throws RemoteException;

    void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown, GameListener playerListener)
            throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException;

    ObjectiveCard getPlayerObjectiveCard(int idGame, int idClientIntoGame) throws RemoteException;

    ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) throws RemoteException;

    ObjectiveCard[] getSharedObjectiveCards(int idGame) throws RemoteException;

    void playCard(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException;

    void drawCard(int idGame, int idClientIntoGame, int deckToChoose, int inVisible) throws IOException, CardNotFoundException, InterruptedException;

    boolean getIsLastRoundStarted(int idGame) throws RemoteException;

    HashSet<Point> getAvailablePlaces(int idGame, int idClientIntoGame) throws RemoteException;

    ArrayList<TokenColor> getAvailableColors(int idGame, GameListener playerListener) throws RemoteException;

    TokenColor setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor, GameListener playerListener) throws IOException;

    int getPoints(int idGame, int idClientIntoGame) throws RemoteException;

    ArrayList<GameCard> getVisibleCardsDeck(int idGame, int deck) throws RemoteException;

    GameCard getLastCardOfUsableCards(int idGame, int deck) throws RemoteException;

    String getUsernamePlayerThatStoppedTheGame(int idGame) throws RemoteException;

    HashMap<Point, GameCard> getPlayerDesk(int idGame, int idClientIntoGame) throws RemoteException;

    String getWinner(int idGame) throws RemoteException, InterruptedException;

    void closeGame(int idGame, String userThatLeft) throws IOException;

    int getnPlayer(int idGame) throws RemoteException;

    ArrayList<Player> getPlayers(int idGame) throws RemoteException;

    String getServerCurrentState(int idGame, int idClientIntoGame) throws RemoteException;

    boolean checkState(int idGame, int idClientIntoGame, RequestedActions requestedActions) throws RemoteException;

    boolean isGameStarted(int idGame) throws RemoteException;

    PlayerState getCurrentPlayerState(int idGame, int idClientIntoGame) throws RemoteException;

    void closeGameWhenEnded(Integer idGame) throws RemoteException;
}
