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

/**
 * Interface that defines the methods that the VirtualView must implement
 * The methods will be implemented in order to allow the communication between the server and the client in both socket and RMI connections
 */
public interface VirtualView extends Remote {
    /**
     * Method that returns the list of the games that are not started yet
     *
     * @return ArrayList of the games that are not started yet
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ArrayList<Integer> getNotStartedGames() throws IOException, InterruptedException;

    /**
     * Method that sets the secret objective card of the player
     *
     * @param idCard the id of the secret objective card chosen
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    void setObjectiveCard(int idCard) throws IOException, InterruptedException;

    /**
     * Method that creates a new game
     *
     * @param username String that represents the username of the player that creates the game
     * @param nPlayers Integer that represents the number of players that will play the game
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    void createGame(String username, int nPlayers) throws IOException, InterruptedException;

    /**
     * Method that returns the id of the game
     *
     * @return Integer that represents the id of the game
     * @throws RemoteException if there are problems with the remote connection
     */
    Integer getIdGame() throws RemoteException;

    /**
     * Method that returns the id of the client that is playing the specified game
     *
     * @return Integer that represents the id of the client
     * @throws RemoteException if there are problems with the remote connection
     */
    int getIdClientIntoGame() throws RemoteException;

    /**
     * Method that returns a list of the players that are playing the game
     *
     * @return ArrayList of the players that are playing the game
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ArrayList<Player> getAllPlayers() throws IOException, InterruptedException;

    /**
     * Method that checks if the username is already taken
     *
     * @param username String that represents the username to check
     * @return true if the username is valid, false otherwise
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    boolean checkUsername(String username) throws IOException, InterruptedException;

    /**
     * Method that allows the player to join a game
     *
     * @param input    Integer that represents the id of the game
     * @param username String that represents the username of the player that wants to join the game
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    void joinGame(int input, String username) throws IOException, InterruptedException;

    /**
     * Method that returns the list of the objective cards of the player
     *
     * @return ArrayList of the objective cards of the player
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws IOException, InterruptedException;

    /**
     * Method that get the starter card of the player
     *
     * @return StarterCard that represents the starter card of the player
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    StarterCard getStarterCard() throws IOException, InterruptedException;

    /**
     * Method that plays the starter card of the player
     *
     * @param playedFacedDown boolean that represents if the card is played faced down
     * @throws IOException                 if there are problems with I/O operations
     * @throws RequirementsNotMetException if the requirements of the card are not met
     * @throws PlaceNotAvailableException  if the place where the client wants to put the card is not available
     * @throws InterruptedException        if the thread is interrupted
     */
    void playStarterCard(boolean playedFacedDown) throws IOException, RequirementsNotMetException, PlaceNotAvailableException, InterruptedException;

    /**
     * Method that returns the secret objective card of the player
     *
     * @return ObjectiveCard that represents the secret objective card of the player
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ObjectiveCard getPlayerObjectiveCard() throws IOException, InterruptedException;

    /**
     * Method that returns the hand of the player
     *
     * @return ArrayList of the cards in the hand of the player
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ArrayList<GameCard> getPlayerHand() throws IOException, InterruptedException;

    /**
     * Method that returns the shared objective cards of the game
     *
     * @return ObjectiveCard[] that represents the shared objective cards of the game
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ObjectiveCard[] getSharedObjectiveCards() throws IOException, InterruptedException;

    /**
     * Method that plays a card of the player
     *
     * @param chosenCard     the GameCard that the player wants to play
     * @param faceDown       boolean that represents if the card is played faced down
     * @param chosenPosition Point that represents the position where the player wants to put the card
     * @return Integer that represents the points that the player earns playing the card
     * @throws IOException                 if there are problems with I/O operations
     * @throws PlaceNotAvailableException  if the place where the client wants to put the card is not available
     * @throws RequirementsNotMetException if the requirements of the card are not met
     * @throws InterruptedException        if the thread is interrupted
     */
    int playCard(int chosenCard, boolean faceDown, Point chosenPosition)
            throws IOException, PlaceNotAvailableException, RequirementsNotMetException, InterruptedException;

    /**
     * Method that draws a card from the deck and puts it into the player's hand
     *
     * @param input     Integer that represents the deck from which the player wants to draw the card
     * @param inVisible Integer that which card of the deck to draw
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    void drawCard(int input, int inVisible) throws IOException, InterruptedException;

    /**
     * Method that returns the messages of the player
     *
     * @param receiver String that represents the receiver of the messages
     * @return ArrayList of the messages of the player
     * @throws IOException          if a I/O problem occurs
     * @throws InterruptedException if the thread running is interrupted
     */
    ArrayList<Message> getMessages(String receiver) throws IOException, InterruptedException;

    /**
     * Method that sends a message to the receiver
     *
     * @param receiver String that represents the receiver of the message
     * @param input    String that represents the message to send
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    void sendMessage(String receiver, String input) throws IOException, InterruptedException;

    /**
     * Method that returns the available places where the player can put the card
     *
     * @return HashSet of the available places where the player can put the card
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    HashSet<Point> getAvailablePlaces() throws IOException, InterruptedException;

    /**
     * Method that returns the player's desk
     *
     * @return HashMap of the player's desk
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    HashMap<Point, GameCard> getPlayerDesk() throws IOException, InterruptedException;

    /**
     * Method that returns the available colors for the player
     *
     * @return ArrayList of the available colors for the player
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ArrayList<TokenColor> getAvailableColors() throws IOException, InterruptedException;

    /**
     * Method that sets the token color of the player as the chosen one
     *
     * @param tokenColor TokenColor chosen by the player
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    void setTokenColor(TokenColor tokenColor) throws IOException, InterruptedException;

    /**
     * Method that returns the points of the player
     *
     * @return Integer that represents the points of the player
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    int getPoints() throws IOException, InterruptedException;

    /**
     * Method that returns the visible card of the deck sent as a parameter
     *
     * @param deck Integer that represents the deck from which to get the visible card
     * @return ArrayList of the visible cards of the deck
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ArrayList<GameCard> getVisibleCardsDeck(int deck) throws IOException, InterruptedException;

    /**
     * Method that returns the last card of the usable cards of the deck sent as a parameter
     *
     * @param deck Integer that represents the deck from which to get the last card
     * @return GameCard that represents the last card of the usable cards of the deck
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    GameCard getLastFromUsableCards(int deck) throws IOException, InterruptedException;

    /**
     * Method that returns the winner of the game
     *
     * @return String that represents the winner of the game
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    String getWinner() throws IOException, InterruptedException;

    /**
     * Method that closes the game
     *
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    void closeGame() throws IOException, InterruptedException;

    /**
     * Method that returns the number of players of the game
     *
     * @param idGame Integer that represents the id of the game
     * @return Integer that represents the number of players of the game
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    int getnPlayer(int idGame) throws IOException, InterruptedException;

    /**
     * Method that returns the players of the game
     *
     * @param idGame Integer that represents the id of the game
     * @return ArrayList of the players of the game
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    ArrayList<Player> getPlayers(int idGame) throws IOException, InterruptedException;

    /**
     * Method used to check if the client is still running
     *
     * @throws RemoteException if there are problems with the remote connection
     */
    void ping() throws RemoteException;

    /**
     * Method that runs the client
     *
     * @throws IOException if there are problems with I/O operations
     */
    void run() throws IOException;

    /**
     * Method that returns the username of the player
     *
     * @return String that represents the username of the player
     * @throws RemoteException if there are problems with the remote connection
     */
    String getUsername() throws RemoteException;

    /**
     * Method that check if the game has started
     *
     * @return true if the game has started, false otherwise
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    boolean isGameStarted() throws IOException, InterruptedException;

    /**
     * Method return the state in which the game is
     *
     * @return String that represents the state in which the game is
     * @throws InterruptedException if the thread is interrupted
     * @throws RemoteException      if there are problems with the remote connection
     */
    String getGameState() throws InterruptedException, RemoteException;

    /**
     * Method that closes the connection
     *
     * @throws RemoteException if there are problems with the remote connection
     */
    void close() throws RemoteException;

    /**
     * Method that returns the current state of the player
     *
     * @return PlayerState that represents the current state of the player
     * @throws IOException          if there are problems with I/O operations
     * @throws InterruptedException if the thread is interrupted
     */
    PlayerState getCurrentPlayerState() throws IOException, InterruptedException;
}
