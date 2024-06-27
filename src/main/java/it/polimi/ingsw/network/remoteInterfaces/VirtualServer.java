package it.polimi.ingsw.network.remoteInterfaces;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.controller.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.controller.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This interface is used to define the methods that can be called remotely by the client
 */
public interface VirtualServer extends Remote {

    /**
     * Connect the client to the server
     *
     * @param client is the client to connect
     * @throws RemoteException if there is a connection error
     */
    void connect(VirtualView client) throws RemoteException;

    /**
     * Check if the username is already taken
     *
     * @param username       is the username to check
     * @param playerListener is the listener of the player
     * @return true if the username is available, false otherwise
     * @throws RemoteException if there is a connection error
     */
    boolean checkUsername(String username, GameListener playerListener) throws RemoteException;

    /**
     * Get the list of the games that are not started yet
     *
     * @param lobbyListener is the listener of the lobby
     * @return the list of the games that are not started yet
     * @throws RemoteException if there is a connection error
     */
    ArrayList<Integer> getNotStartedGames(GameListener lobbyListener) throws RemoteException;

    /**
     * Get the list of the players in a game
     *
     * @param gameId is the identification number of the game
     * @return the list of the players in a game
     * @throws RemoteException if there is a connection error
     */
    ArrayList<Player> getAllPlayers(int gameId) throws RemoteException;

    /**
     * Get the list of the messages
     *
     * @param receiver the name of the receiver of the messages
     * @param gameId   the id of the game
     * @param sender   the name of the sender of the messages
     * @return the list of the messages
     * @throws RemoteException if there is a connection error
     */
    ArrayList<Message> getMessages(String receiver, int gameId, String sender) throws RemoteException;

    /**
     * Send a message during the game
     * @param idGame is the id of the game
     * @param msg    is the message to send
     * @throws RemoteException if there is a connection error
     */
    void sendMessage(int idGame, Message msg) throws RemoteException;

    /**
     * this method puts the player into the game
     *
     * @param id                is the id of the game
     * @param username          is the name of the player that wants to join the game
     * @param playerListener    is the listener of the player
     * @return the id of the player into the game
     * @throws IOException          if there is an I/O error
     * @throws InterruptedException if there is an interruption error
     */
    int joinGame(int id, String username, GameListener playerListener) throws IOException, InterruptedException;

    /**
     * Create a new game
     *
     * @param username       is the name of the player that wants to create the game
     * @param nPlayers       is the number of players that the player wants in the game
     * @param playerListener is the listener of the player
     * @return the id of the created game
     * @throws IOException if there is an I/O error
     * @throws InterruptedException if there is an interruption error
     */
    int createGame(String username, int nPlayers, GameListener playerListener) throws IOException, InterruptedException;

    /**
     * Remove the username from the list of the players when the player leaves the game
     *
     * @param username the username of the player to remove
     * @throws RemoteException if there is a connection error
     */
    void removeUsername(String username) throws RemoteException;

    /**
     * get the objective cards for the player
     *
     * @param idGame         the id of the game
     * @param idPlayer       the id of the player
     * @param playerListener the listener of the player
     * @return the list of the objective cards
     * @throws RemoteException if there is a connection error
     */
    ArrayList<ObjectiveCard> getPlayerObjectiveCards(int idGame, int idPlayer, GameListener playerListener) throws RemoteException;

    /**
     * Set the objective card for the player
     *
     * @param idGame           the id of the game
     * @param idClientIntoGame the id of the player
     * @param idObjCard        the id of the objective card
     * @throws RemoteException if there is a connection error
     */
    void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard) throws RemoteException;

    /**
     * Get the starter card for the player
     *
     * @param idGame           the id of the game
     * @param idClientIntoGame the id of the player
     * @return the starter card
     * @throws RemoteException if there is a connection error
     */
    StarterCard getStarterCard(int idGame, int idClientIntoGame) throws RemoteException;

    /**
     * placed the starer card on the desk, face up or face down
     *
     * @param idGame             the id of the game
     * @param idClientIntoGame   the id of the player
     * @param playedFacedDown    true if the card is played face down, false otherwise
     * @param playerListener     the listener of the player
     * @throws RemoteException if there is a connection error
     * @throws RequirementsNotMetException if the requirements are not met
     * @throws PlaceNotAvailableException  if the place is not available
     */
    void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown, GameListener playerListener)
            throws RemoteException, RequirementsNotMetException, PlaceNotAvailableException;

    /**
     * get the objective card chosen by the player
     *
     * @param idGame           the id of the game
     * @param idClientIntoGame the id of the player
     * @return the objective card chosen by the player
     * @throws RemoteException if there is a connection error
     */
    ObjectiveCard getPlayerObjectiveCard(int idGame, int idClientIntoGame) throws RemoteException;

    /**
     * get the hand of the player
     *
     * @param idGame           the id of the game
     * @param idClientIntoGame the id of the player
     * @return the hand of the player
     * @throws RemoteException if there is a connection error
     */
    ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) throws RemoteException;

    /**
     * get the objective cards the same for all the players in the same game
     *
     * @param idGame the id of the game
     * @return the array of the objective cards
     * @throws RemoteException if there is a connection error
     */
    ObjectiveCard[] getSharedObjectiveCards(int idGame) throws RemoteException;

    /**
     * handles the play of a card
     *
     * @param idGame            the id of the game
     * @param idClientIntoGame  the id of the player that wants to play the card
     * @param chosenCard        the id of the card chosen by the player
     * @param faceDown          true if the card is played face down, false otherwise
     * @param chosenPosition    the position chosen by the player
     * @return the id of the card played
     * @throws RemoteException             if there is a connection error
     * @throws PlaceNotAvailableException  if the place is not available
     * @throws RequirementsNotMetException if the requirements are not met
     */
    int playCard(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException;

    /**
     * draw a card from one of the two decks
     *
     * @param idGame            the id of the game
     * @param idClientIntoGame  the id of the player that wants to draw the card
     * @param deckToChoose      the deck to choose: Resources Deck or Gold Deck
     * @param inVisible         1 if the card is drawn face down, 0 otherwise (if the player can see the card)
     * @throws IOException      if there is an I/O error
     * @throws InterruptedException if there is an interruption error
     */
    void drawCard(int idGame, int idClientIntoGame, int deckToChoose, int inVisible) throws IOException, InterruptedException;

    /**
     * get the available places on the desk where the player can play the card
     *
     * @param idGame           the id of the game
     * @param idClientIntoGame the id of the player
     * @return the available places on the desk
     * @throws RemoteException if there is a connection error
     */
    HashSet<Point> getAvailablePlaces(int idGame, int idClientIntoGame) throws RemoteException;

    /**
     * get the available colors for the token that the player can choose
     *
     * @param idGame         the id of the game
     * @param playerListener the listener of the player
     * @return the available token colors
     * @throws RemoteException if there is a connection error
     */
    ArrayList<TokenColor> getAvailableColors(int idGame, GameListener playerListener) throws RemoteException;

    /**
     * associates the token color for the player
     *
     * @param idGame            the id of the game
     * @param idClientIntoGame  the id of the player
     * @param tokenColor        the token color chosen by the player
     * @return the token color chosen by the player
     * @throws IOException if there is an I/O error
     */
    TokenColor setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor) throws IOException;

    /**
     * get the points of the player
     *
     * @param idGame           is the id of the game
     * @param idClientIntoGame is the id of the player
     * @return the points of the player
     * @throws RemoteException if there is a connection error
     */
    int getPoints(int idGame, int idClientIntoGame) throws RemoteException;

    /**
     * get the two visible cards of the deck (Resources Deck or Gold Deck)
     *
     * @param idGame is the id of the game
     * @param deck   (Resources Deck or Gold Deck)
     * @return the two visible cards of the deck
     * @throws RemoteException if there is a connection error
     */
    ArrayList<GameCard> getVisibleCardsDeck(int idGame, int deck) throws RemoteException;

    /**
     * Recognize the last card of the usable cards
     *
     * @param idGame  the id of the game
     * @param deck    the deck to choose: Resources Deck or Gold Deck
     * @return the last card of the usable cards
     * @throws RemoteException if there is a connection error
     */
    GameCard getLastCardOfUsableCards(int idGame, int deck) throws RemoteException;

    /**
     * Get the desk of the player
     * @param idGame Integer representing the id of the game
     * @param idClientIntoGame Integer representing the id of the player into the game
     * @return HashMap<Point, GameCard> representing the desk of the player
     * @throws RemoteException if there is a connection error
     */
    HashMap<Point, GameCard> getPlayerDesk(int idGame, int idClientIntoGame) throws RemoteException;

    /**
     * get the winner of the game
     *
     * @param idGame is the id of the game
     * @return the username of the winner of the game
     * @throws RemoteException      if there is a connection error
     * @throws InterruptedException if there is an interruption error
     */
    String getWinner(int idGame) throws RemoteException, InterruptedException;

    /**
     * close the game when the player leaves the game
     *
     * @param idGame       is the id of the game
     * @param userThatLeft is the username of the player that left the game
     * @throws IOException if there is an I/O error
     */
    void closeGame(int idGame, String userThatLeft) throws IOException;

    /**
     * get the number of the players in the game
     *
     * @param idGame is the id of the game
     * @return the number of the players in the game
     * @throws RemoteException if there is a connection error
     */
    int getnPlayer(int idGame) throws RemoteException;

    /**
     * get the players in the game
     *
     * @param idGame is the id of the game
     * @return the list of the players in the game
     * @throws RemoteException if there is a connection error
     */
    ArrayList<Player> getPlayers(int idGame) throws RemoteException;

    /**
     * get the current state of the player in the game and of the game
     *
     * @param idGame           is the id of the game
     * @param idClientIntoGame is the id of the player
     * @return String representing the current state of the game and of the player
     * @throws RemoteException if there is a connection error
     */
    String getServerCurrentState(int idGame, int idClientIntoGame) throws RemoteException;

    /**
     * check the state of the game to know if the player can do the requested action
     * @param idGame            is the id of the game
     * @param idClientIntoGame  is the id of the player
     * @param requestedActions  is the action requested by the player
     * @return true if the player can do the requested action, false otherwise
     * @throws RemoteException if there is a connection error
     */
    boolean checkState(int idGame, int idClientIntoGame, RequestedActions requestedActions) throws RemoteException;

    /**
     * check if the game is started
     *
     * @param idGame is the id of the game
     * @return true if the game is started, false otherwise
     * @throws RemoteException if there is a connection error
     */
    boolean isGameStarted(int idGame) throws RemoteException;

    /**
     * get the current state of the player in the game
     *
     * @param idGame            is the id of the game
     * @param idClientIntoGame  is the id of the player
     * @return the current state of the player in the game
     * @throws RemoteException if there is a connection error
     */
    PlayerState getCurrentPlayerState(int idGame, int idClientIntoGame) throws RemoteException;

    /**
     * get the state of the game
     *
     * @param idGame is the id of the game
     * @return the state of the game
     * @throws RemoteException if there is a connection error
     */
    String getGameState(int idGame) throws RemoteException;
}
