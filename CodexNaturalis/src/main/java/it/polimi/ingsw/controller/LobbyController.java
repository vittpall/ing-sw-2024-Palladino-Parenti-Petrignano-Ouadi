package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.model.observer.Observable;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 * Class that represents the controller of the lobby: it manages all the games
 */
public class LobbyController {

    private final Set<String> usernames = new HashSet<>();
    private final Map<Integer, GameController> gameControllers;
    private final ArrayList<Integer> unusedIdGame;
    private int nextGameId;
    private final Observable lobbyListeners;

    /**
     * Controller of the lobby
     */
    public LobbyController() {
        gameControllers = new HashMap<>();
        unusedIdGame = new ArrayList<>();
        nextGameId = 1;
        lobbyListeners = new Observable();
    }

    /**
     * Check if the username is already used. If not, add it to the list of usernames
     *
     * @param username String that represent the username of the player
     * @return true if the username is not already used, false otherwise
     */
    public boolean checkUsername(String username) {
        synchronized (this.usernames) {
            return usernames.add(username);
        }

    }

    /**
     * Remove the username from the list of usernames
     *
     * @param username String that represent the username of the player
     */
    public void removeUsername(String username) {
        synchronized (this.usernames) {
            usernames.remove(username);
        }
    }

    /**
     * Get the list of the visible games and subscribe the listener to the lobby
     *
     * @param lobbyListener Listener of the lobby
     * @return ArrayList of the visible games
     */
    public ArrayList<Integer> getVisibleGames(GameListener lobbyListener) {
        ArrayList<Integer> visibleGameControllers = new ArrayList<>();
        for (int id : gameControllers.keySet()) {
            if (gameControllers.get(id).getPlayers().size() < gameControllers.get(id).getnPlayer()) {
                visibleGameControllers.add(id);
            }
        }
        lobbyListeners.subscribeListener(lobbyListener);
        return visibleGameControllers;
    }

    /**
     * Get the list of the all the players in the specified game
     *
     * @param gameId Integer that represent the id of the game
     * @return ArrayList of the players in the game
     */
    public ArrayList<Player> getAllPlayers(int gameId) {
        return gameControllers.get(gameId).getPlayers();
    }

    /**
     * Get the list of all the messages between a sender and a receiver
     *
     * @param receiver String that represent the username of the receiver of the messages
     * @param gameId   Integer that represent the id of the game
     * @param sender   String that represent the username of the sender of the messages
     * @return ArrayList of the messages between the sender and the receiver
     */
    public ArrayList<Message> getMessages(String receiver, int gameId, String sender) {
        return gameControllers.get(gameId).getMessages(receiver, sender);
    }

    /**
     * Join the player to the specified game and remove its listener from the list of the lobby listeners
     *
     * @param id             Integer that represent the id of the game in which the player wants to join
     * @param username       String that represent the username of the player
     * @param playerListener Listener representing the player
     * @return Integer that represent the id of the player in the game
     * @throws InterruptedException if the thread running is interrupted
     * @throws IOException          if an I/O error occurs
     */
    public int joinGame(int id, String username, GameListener playerListener) throws InterruptedException, IOException {
        String msg;
        msg = "\n--------------------------------------";
        if (gameControllers.get(id).getPlayers().isEmpty())
            msg += "\nThe game " + id + " has been created.";
        else
            msg += "\nA player has joined the game " + id;
        int nPlayer = gameControllers.get(id).joinGame(username, playerListener);
        HashMap<Integer, Integer[]> availableGames = new HashMap<>();
        for (int idGame : gameControllers.keySet()) {
            if (gameControllers.get(id).getPlayers().size() < gameControllers.get(id).getnPlayer()) {
                availableGames.put(idGame, new Integer[]{gameControllers.get(idGame).getnPlayer(),
                        (gameControllers.get(idGame).getnPlayer() - gameControllers.get(idGame).getPlayers().size())});
            }
        }
        lobbyListeners.unSubscribeListener(playerListener);
        lobbyListeners.notifyJoinedGameToOutsider(msg, availableGames);
        return nPlayer;
    }

    /**
     * Create a new game with the specified number of players and add the player into the game
     *
     * @param username       String that represent the username of the player
     * @param nPlayers       Integer that represent the number of players in the game to create
     * @param playerListener Listener representing the player
     * @return Integer that represent the id of the game
     * @throws InterruptedException if the thread running is interrupted
     * @throws IOException          if an I/O error occurs
     */
    public int createGame(String username, int nPlayers, GameListener playerListener) throws InterruptedException, IOException {
        int id;
        if (!unusedIdGame.isEmpty()) {
            id = unusedIdGame.getFirst();
            unusedIdGame.removeFirst();
        } else {
            id = nextGameId;
            nextGameId++;
        }
        GameController gameController = new GameController(nPlayers);
        gameControllers.put(id, gameController);
        int nPlayer = this.joinGame(id, username, playerListener);
        lobbyListeners.unSubscribeListener(playerListener);
        if (nPlayer == 0) {
            return id;
        }
        return -1;
    }

    /**
     * Get the list of the player's objective cards
     *
     * @param idGame         Integer that represent the id of the game
     * @param idPlayer       Integer that represent the id of the player into the game
     * @param playerListener Listener representing the player
     * @return ArrayList of the player's objective cards
     */
    public ArrayList<ObjectiveCard> getObjectiveCards(int idGame, int idPlayer, GameListener playerListener) {
        return gameControllers.get(idGame).getObjectiveCards(idPlayer, playerListener);
    }

    /**
     * Set the objective card of the player
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @param idObjCard        Integer that represent the id of the chosen objective card
     */
    public void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard) {
        gameControllers.get(idGame).setObjectiveCard(idClientIntoGame, idObjCard);
    }

    /**
     * Get the starter card of the player
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @return StarterCard of the player
     */
    public StarterCard getStarterCard(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getStarterCard();
    }

    /**
     * Play the starter card of the player on the side chosen
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @param playedFacedDown  Boolean that represent if the card is played faced down or not
     * @param playerListener   Listener representing the player
     * @throws RequirementsNotMetException if the requirements are not met
     * @throws PlaceNotAvailableException  if the place is not available
     */
    public void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown, GameListener playerListener)
            throws RequirementsNotMetException, PlaceNotAvailableException {
        gameControllers.get(idGame).playStarterCard(idClientIntoGame, playedFacedDown, playerListener);
    }

    /**
     * Get the secret objective card of the player
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @return the secret ObjectiveCard of the player
     */
    public ObjectiveCard getObjectiveCard(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getObjectiveCard(idClientIntoGame);
    }

    /**
     * Get the specific player's hand
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @return ArrayList of the player hand's cards
     */
    public ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPlayerHand();
    }

    /**
     * Get the shared objective cards of the specified game
     *
     * @param idGame Integer that represent the id of the game
     * @return Array of the shared objective cards
     */
    public ObjectiveCard[] getSharedObjectiveCards(int idGame) {
        return gameControllers.get(idGame).getSharedObjectiveCards();
    }

    /**
     * Get the available colors for the token of the player
     *
     * @param idGame         Integer that represent the id of the game
     * @param playerListener Listener representing the player
     * @return ArrayList of the available colors for the token of the player
     */
    public synchronized ArrayList<TokenColor> getAvailableColors(int idGame, GameListener playerListener) {
        return gameControllers.get(idGame).getAvailableColors(playerListener);
    }

    /**
     * Set the token color of the player
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @param tokenColor       TokenColor that represent the color chosen
     * @return TokenColor that represent the color chosen
     * @throws IOException if an I/O error occurs
     */
    public synchronized TokenColor setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor) throws IOException {
        return gameControllers.get(idGame).setTokenColor(idClientIntoGame, tokenColor);
    }

    /**
     * Get the points of the player
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @return Integer that represent the points of the player
     */
    public int getPoints(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPoints();
    }

    /**
     * Get the current player of the specified game
     *
     * @param idGame Integer that represent the id of the game
     * @return Integer that represent the current player of the game
     */
    public int getCurrentPlayer(int idGame) {
        return gameControllers.get(idGame).getCurrentPlayer();
    }

    /**
     * Play the card chosen by the player in the specified position and on the specified side
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @param chosenCard       Integer that represent the id of the card chosen
     * @param faceDown         Boolean that represent if the card is played faced down or not
     * @param chosenPosition   Point that represent the deck's position chosen
     * @return Integer that represent the points of the player
     * @throws PlaceNotAvailableException  if the position is not available
     * @throws RequirementsNotMetException if the requirements are not met
     */
    public int playCard(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws PlaceNotAvailableException, RequirementsNotMetException {
        return gameControllers.get(idGame).playCard(idClientIntoGame, chosenCard, faceDown, chosenPosition);
    }

    /**
     * Draw a random or visible card from the deck chosen
     *
     * @param idGame       Integer that represent the id of the game
     * @param deckToChoose Integer that represent the deck chosen
     * @param inVisible    Integer that represent the id of the visible chosen card or 3 if the card has to be drawn randomly
     */
    public void drawCard(int idGame, int deckToChoose, int inVisible) {
        gameControllers.get(idGame).drawCard(deckToChoose, inVisible);
    }

    /**
     * Get if the last round is started
     *
     * @param idGame Integer that represent the id of the game
     * @return true if the last round is started, false otherwise
     */
    public boolean getIsLastRoundStarted(int idGame) {
        return gameControllers.get(idGame).getIsLastRoundStarted();
    }

    /**
     * Send a message in the specified game
     *
     * @param idGame Integer representing the id of the game
     * @param msg    Message representing the message to send
     */
    public void sendMessage(int idGame, Message msg) {
        gameControllers.get(idGame).sendMessage(msg);
    }

    /**
     * Get the available places of the desk for the player
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @return HashSet of the available places of the desk for the player
     */
    public HashSet<Point> getAvailablePlaces(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPlayerDesk().getAvailablePlaces();
    }

    /**
     * Get the visible cards of the deck chosen
     *
     * @param idGame Integer that represent the id of the game
     * @param deck   Integer that represent the deck chosen
     * @return ArrayList of the visible cards of the deck chosen
     */
    public ArrayList<GameCard> getVisibleCardsDeck(int idGame, int deck) {
        return gameControllers.get(idGame).getVisibleCardsDeck(deck);
    }

    /**
     * Get the last card of the cards of the deck chosen
     *
     * @param idGame Integer that represent the id of the game
     * @param deck   Integer that represent the deck chosen
     * @return GameCard that represent the last card of the deck chosen
     */
    public GameCard getLastCardOfUsableCards(int idGame, int deck) {
        return gameControllers.get(idGame).getLastCardOfUsableCards(deck);
    }

    /**
     * Get the player's desk of the player
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @return HashMap of the player's desk of the player
     */
    public HashMap<Point, GameCard> getPlayerDesk(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPlayerDesk().getDesk();
    }

    /**
     * Get the winner of the specified game
     *
     * @param idGame Integer that represent the id of the game
     * @return String that represent the winner of the game
     */
    public String getWinner(int idGame) {
        return gameControllers.get(idGame).getWinner();
    }

    /**
     * Close the specified game and remove it from the list of the game controllers
     *
     * @param idGame       Integer that represent the id of the game
     * @param userThatLeft String that represent the username of the player that left the game
     * @throws IOException if an I/O error occurs
     */
    public void closeGame(int idGame, String userThatLeft) throws IOException {
        if (gameControllers.containsKey(idGame) && !gameControllers.get(idGame).getGameState().equals("End game"))
            gameControllers.get(idGame).closeGame(userThatLeft);
        gameControllers.remove(idGame);
    }

    /**
     * Get the number of the players in the specified game
     *
     * @param idGame Integer that represent the id of the game
     * @return Integer that represent the number of the players in the game
     */
    public int getnPlayer(int idGame) {
        return gameControllers.get(idGame).getnPlayer();
    }

    /**
     * Get a list of all the players in the specified game
     *
     * @param idGame Integer that represent the id of the game
     * @return ArrayList of all the players in the game
     */
    public ArrayList<Player> getPlayers(int idGame) {
        return gameControllers.get(idGame).getPlayers();
    }

    /**
     * Check if the player can do the requested action
     *
     * @param idGame           Integer that represent the id of the game in which the player is
     * @param idPlayerIntoGame Integer that represent the id of the player into the game
     * @param requestedActions RequestedActions that represent the action requested by the player
     * @return true if the player can do the requested action, false otherwise
     */
    public boolean checkState(int idGame, int idPlayerIntoGame, RequestedActions requestedActions) {
        return gameControllers.get(idGame).checkState(idPlayerIntoGame, requestedActions);
    }

    /**
     * Get the current state of the player
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @return String that represent the text of the current state of the player in the game
     */
    public String getCurrentState(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getCurrentState(idClientIntoGame);
    }

    /**
     * Get the current state of the game
     *
     * @param idGame Integer that represent the id of the game
     * @return String that represent the text of the current state of the game
     */
    public String getCurrentGameState(int idGame) {
        return gameControllers.get(idGame).getGameState();
    }

    /**
     * Get the current player state
     *
     * @param idGame           Integer that represent the id of the game
     * @param idClientIntoGame Integer that represent the id of the player into the game
     * @return PlayerState of the specified player
     */
    public PlayerState getCurrentPlayerState(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayerState(idClientIntoGame);
    }

}
