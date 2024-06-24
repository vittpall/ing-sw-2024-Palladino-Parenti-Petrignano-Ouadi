package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.GameState;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GameController {
    private final Game model;
    private final int nPlayers;
    private GameState gameState;
    private final String winner;
    private final HashMap<String, Observable> listeners;

    /**
     * Constructor of the class
     *
     * @param nPlayers
     */
    public GameController(int nPlayers) {
        this.nPlayers = nPlayers;
        model = new Game(nPlayers);
        gameState = GameState.WAITING_FOR_PLAYERS;
        winner = "No winner";
        listeners = new HashMap<>();
    }

    /**
     * This method checks if the player can perform the requested action
     * @param idPlayerIntoGame
     * @param requestedActions
     * @return true if the player can perform the requested action, false otherwise
     */
    public boolean checkState(int idPlayerIntoGame, RequestedActions requestedActions) {
        return switch (requestedActions) {
            case RequestedActions.DRAW ->
                    (gameState == GameState.ROUNDS || gameState == GameState.FINISHING_ROUND_BEFORE_LAST)
                            && getPlayerState(idPlayerIntoGame) == PlayerState.DRAW;
            case RequestedActions.PLAY_CARD ->
                    (gameState == GameState.ROUNDS || gameState == GameState.LAST_ROUND || gameState == GameState.FINISHING_ROUND_BEFORE_LAST)
                            && getPlayerState(idPlayerIntoGame) == PlayerState.PLAY_CARD;
            case RequestedActions.SHOW_WINNER -> gameState == GameState.ENDGAME;
            case RequestedActions.SHOW_DESKS, RequestedActions.SHOW_OBJ_CARDS,
                 RequestedActions.SHOW_POINTS, RequestedActions.CHAT -> true;
        };
    }

    /**
     * This method returns the current state of the game
     * @param idClientIntoGame
     * @return the current state of the game
     */
    public String getCurrentState(int idClientIntoGame) {
        return getGameState() + " " + getPlayerState(idClientIntoGame).toString();
    }

    /**
     * This method returns the state of the player
     * @param idClientIntoGame
     * @return the state of the player
     */
    public PlayerState getPlayerState(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getPlayerState();
    }

    /**
     * This method returns the state of the game
     * @return the state of the game
     */
    public String getGameState() {
        return gameState.toString();
    }

    /**
     * This method lets the player create a game and join one
     * @param username
     * @param playerListener
     * @return the id of the player
     * @throws InterruptedException
     * @throws IOException
     */
    public synchronized int joinGame(String username, GameListener playerListener) throws InterruptedException, IOException {
        Player player = new Player(username);
        int idPlayer = model.addPlayer(player);
        if (model.getPlayers().size() == nPlayers) {
            model.setUpGame();
            gameState = GameState.SETUP_GAME;
            for (int i = 0; i < nPlayers; i++) {
                model.getPlayers().get(i).setPlayerState(PlayerState.SETUP_GAME);
            }
        }

        addListenerList("Chat", playerListener);
        addListenerList("WaitingForPlayersState", playerListener);
        listeners.get("WaitingForPlayersState").notifyJoinedGame(model.getPlayers(), nPlayers - model.getPlayers().size(), playerListener.getUsername());

        return idPlayer;
    }

    /**
     * This method lets the player create a game
     * @param state
     * @param listener
     */
    private void addListenerList(String state, GameListener listener) {
        if (!listeners.containsKey(state))
            listeners.put(state, new Observable());
        listeners.get(state).subscribeListener(listener);
    }

    /**
     * This method remove a listener from an observable
     * @param state
     * @param listener
     */
    private void removeListenerList(String state, GameListener listener) {
        if (listeners.containsKey(state))
            listeners.get(state).unSubscribeListener(listener);
    }

    /**
     * This method gets the objective cards of the player
     * @param idPlayer
     * @param playerListener
     * @return the objective cards of the player
     */
    public ArrayList<ObjectiveCard> getObjectiveCards(int idPlayer, GameListener playerListener) {
        addListenerList("GameStarted", playerListener);
        return model.getPlayers().get(idPlayer).getDrawnObjectiveCards();
    }

    /**
     * This method sets the objective card of the player
     * @param idClientIntoGame
     * @param idObjCard
     */
    public void setObjectiveCard(int idClientIntoGame, int idObjCard) {
        model.setObjectiveCards(idClientIntoGame, idObjCard);
    }

    /**
     * This method closes the game and notifies the players that the game has been closed
     * @param userThatLeft
     * @throws IOException
     */
    public void closeGame(String userThatLeft) throws IOException {
        String msg = "The game has been closed because " + userThatLeft + " finished the game";
        for (String s : listeners.keySet())
            listeners.get(s).notifyCloseGame(msg);
    }

    /**
     * This method gets the starter card of the player
     * @param idClientIntoGame the id of the player into the game
     * @return the starter card of the player
     */
    public StarterCard getStarterCard(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getStarterCard();
    }

    /**
     * This method lets the player play the starter card and notifies the other players that the starter card has been played
     * @param idClientIntoGame
     * @param playedFacedDown
     * @param playerListener
     * @throws RequirementsNotMetException
     * @throws PlaceNotAvailableException
     */
    public synchronized void playStarterCard(int idClientIntoGame, boolean playedFacedDown, GameListener playerListener)
            throws RequirementsNotMetException, PlaceNotAvailableException {
        GameCard starterCard = model.getPlayers().get(idClientIntoGame).getStarterCard();
        model.getPlayers().get(idClientIntoGame).playCard(starterCard, playedFacedDown, new Point(0, 0));
        if (model.getCurrentPlayerIndex() == idClientIntoGame)
            model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.PLAY_CARD);
        else
            model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.WAITING);
        if (model.getPlayers().stream().allMatch(player -> (!player.getPlayerState().equals(PlayerState.SETUP_GAME))))
            gameState = GameState.ROUNDS;
        removeListenerList("GameStarted", playerListener);
        addListenerList("GameRounds", playerListener);
        String message = "\n----------------------------------\n" +
                "Player " + model.getPlayers().get(idClientIntoGame).getUsername() + " played the starter card";
        HashMap<String, Integer> playersPoints = new HashMap<>();
        for (Player player : model.getPlayers()) {
            playersPoints.put(player.getUsername(), player.getPoints());
        }
        listeners.get("GameRounds").notifyPlayedCard(message, playersPoints, model.getPlayers().get(idClientIntoGame).getUsername());
    }

    /**
     * This method gets the objective card of the player
     * @param idClientIntoGame the id of the player into the game
     * @return the objective card of the player
     */
    public ObjectiveCard getObjectiveCard(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getObjectiveCard();
    }

    /**
     * This method gets the hand of the player
     * @param idClientIntoGame the id of the player into the game
     * @return the hand of the player
     */
    public ArrayList<GameCard> getPlayerHand(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getPlayerHand();
    }

    /**
     * This method gets the shared objective cards
     * @return the shared objective cards
     */
    public ObjectiveCard[] getSharedObjectiveCards() {
        return model.getSharedObjectiveCards();
    }

    /**
     * This method gets the available colors for the player
     * @param playerListener the listener of the player
     * @return the available colors for the player
     */
    public synchronized ArrayList<TokenColor> getAvailableColors(GameListener playerListener) {
        removeListenerList("WaitingForPlayersState", playerListener);
        addListenerList("ColorSelection", playerListener);

        return model.getAvailableColors();
    }

    /**
     * This method sets the token color of the player
     * @param idClientIntoGame the id of the player into the game
     * @param tokenColor the token color of the player
     * @param playerListener the listener of the player
     * @return the token color of the player
     * @throws IOException
     */
    public synchronized TokenColor setTokenColor(int idClientIntoGame, TokenColor tokenColor, GameListener playerListener) throws IOException {
        model.setTokenColor(idClientIntoGame, tokenColor);
        String message = "\n------------------------------------\n" +
                "Player " + model.getPlayers().get(idClientIntoGame).getUsername() +
                " chose the color " + model.getPlayers().get(idClientIntoGame).getTokenColor();
        ArrayList<TokenColor> avColors = model.getAvailableColors();
        listeners.get("ColorSelection").notifyColorSelection(message, avColors);
        return tokenColor;
    }

    /**
     * This method gets all the player of the game
     */
    public ArrayList<Player> getAllPlayers() {
        return model.getPlayers();
    }

    /**
     * This method returns the message of the chat
     * @param receiver
     * @param sender
     * @return the message of the chat
     */
    public ArrayList<Message> getMessages(String receiver, String sender) {
        if (receiver == null)
            return model.getChats().getGlobalChat();
        else
            return model.getChats().getPrivateChat(receiver, sender);
    }

    /**
     * This method sends a message
     * @param msg
     */
    public void sendMessage(Message msg) {
        model.getChats().addMessage(msg);
        listeners.get("Chat").notifyChat(msg);
    }

    /**
     * This method returns the current index of a player inside the game
     * @return the current index of a player inside the game
     */
    public int getCurrentPlayer() {
        return model.getCurrentPlayerIndex();
    }

    /**
     * This method plays a card
     * @param idClientIntoGame the id of the player into the game
     * @param chosenCard the card chosen by the player
     * @param faceDown the face of the card
     * @param chosenPosition the position chosen by the player
     * @return the points of the player
     * @throws PlaceNotAvailableException
     * @throws RequirementsNotMetException
     */
    public int playCard(int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws PlaceNotAvailableException, RequirementsNotMetException {
        String content;
        model.playCard(chosenCard, faceDown, chosenPosition);
        int points = model.getPlayers().get(idClientIntoGame).getPoints();
        model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.DRAW);
        if (gameState == GameState.LAST_ROUND) {
            content = "\n----------------------------------\n" +
                    "Player " + model.getPlayers().get(idClientIntoGame).getUsername() + " played his last card\n" +
                    "Now is " + model.getPlayers().get(getCurrentPlayer()).getUsername() + " turn.";
            model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.ENDGAME);
            if (nPlayers != idClientIntoGame + 1) {
                String usernameSender = model.getPlayers().get(idClientIntoGame).getUsername();
                model.advanceToNextPlayer();
                model.getPlayers().get(model.getCurrentPlayerIndex()).setPlayerState(PlayerState.PLAY_CARD);
                listeners.get("GameRounds").notifyChangeTurn(content, model.getPlayers().get(model.getCurrentPlayerIndex()).getUsername(), usernameSender);
            } else {
                gameState = GameState.ENDGAME;
                HashMap<String, Integer> playersPoints = new HashMap<>();
                HashMap<String, TokenColor> playersTokens = new HashMap<>();


                String winner = model.endGame();

                for (Player player : model.getPlayers()) {
                    playersPoints.put(player.getUsername(), player.getPoints());
                    playersTokens.put(player.getUsername(), player.getTokenColor());
                }
                listeners.get("GameRounds").notifyEndGame(winner, playersPoints, playersTokens);
            }
        } else {
            String message = "\n----------------------------------\n" +
                    "Player " + model.getPlayers().get(idClientIntoGame).getUsername() + " played a card";
            HashMap<String, Integer> playersPoints = new HashMap<>();
            for (Player player : model.getPlayers()) {
                playersPoints.put(player.getUsername(), player.getPoints());
            }
            listeners.get("GameRounds").notifyPlayedCard(message, playersPoints, model.getPlayers().get(idClientIntoGame).getUsername());
        }
        return points;
    }

    /**
     * This method draws a card from a deck and notifies the other players that a card has been drawn by a player and that it is now the turn of another player
     * @param deckToChoose the deck to choose
     * @param inVisible the visibility of the card
     */
    public synchronized void drawCard(int deckToChoose, int inVisible)  {
        Deck chosenDeck;
        if (deckToChoose == 1)
            chosenDeck = model.getResourceDeck();
        else
            chosenDeck = model.getGoldDeck();
        if (inVisible == 3)
            model.drawCard(chosenDeck);
        else {
            GameCard chosenCard = null;
            if (inVisible == 1)
                chosenCard = chosenDeck.getVisibleCards().getFirst();
            else if (inVisible == 2)
                chosenCard = chosenDeck.getVisibleCards().get(1);
            model.drawVisibleCard(chosenDeck, chosenCard);
        }
        if (gameState != GameState.FINISHING_ROUND_BEFORE_LAST && model.getPlayers().get(model.getCurrentPlayerIndex()).getPoints() >= 20) {
            if (model.getCurrentPlayerIndex() == nPlayers - 1)
                gameState = GameState.LAST_ROUND;
            else
                gameState = GameState.FINISHING_ROUND_BEFORE_LAST;
            String username = model.getPlayers().get(model.getCurrentPlayerIndex()).getUsername();
            listeners.get("GameRounds").notifyLastTurnSet(username);
        }
        if (gameState == GameState.FINISHING_ROUND_BEFORE_LAST && model.getCurrentPlayerIndex() == nPlayers - 1) {
            gameState = GameState.LAST_ROUND;
        }
        String message = "\n----------------------------------\n" +
                "Player " + model.getPlayers().get(model.getCurrentPlayerIndex()).getUsername() + " drew a card";
        String usernameSender = model.getPlayers().get(model.getCurrentPlayerIndex()).getUsername();
        model.getCurrentPlayer().setPlayerState(PlayerState.WAITING);
        model.advanceToNextPlayer();
        message += "\nNow is " + model.getPlayers().get(model.getCurrentPlayerIndex()).getUsername() + " turn.";
        model.getCurrentPlayer().setPlayerState(PlayerState.PLAY_CARD);
        listeners.get("GameRounds").notifyChangeTurn(message, model.getPlayers().get(model.getCurrentPlayerIndex()).getUsername(), usernameSender);
    }

    /**
     * This method tells if the last round has started
     * @return true if the last round has started, false otherwise
     */
    public boolean getIsLastRoundStarted() {
        return model.getIsLastRoundStarted();
    }

    /**
     * This method gets the available places for the player
     * @param idClientIntoGame
     * @return the available places for the player
     */
    public HashSet<Point> getAvailablePlaces(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getPlayerDesk().getAvailablePlaces();
    }

    /**
     * This method gets the visible cards of a deck
     * @param deck
     * @return the visible cards of a deck
     */
    public ArrayList<GameCard> getVisibleCardsDeck(int deck) {
        if (deck == 1)
            return model.getResourceDeck().getVisibleCards();
        return model.getGoldDeck().getVisibleCards();
    }

    /**
     * This method gets the last card of the usable cards of a deck
     * @param deck
     * @return the last card of the usable cards of a deck
     */
    public GameCard getLastCardOfUsableCards(int deck) {
        if (deck == 1)
            return model.getResourceDeck().getUsableCards().getLast();
        return model.getGoldDeck().getUsableCards().getLast();
    }

    /**
     * This method gets the username of the player that stopped the game
     * @return the username of the player that stopped the game
     */
    public String getUsernamePlayerThatStoppedTheGame() {
        return model.getUsernamePlayerThatStoppedTheGame();
    }

    /**
     * This method gets the winner of the game
     * @return the winner of the game
     */
    public synchronized String getWinner() {
        return winner;
    }

    /**
     * This method gets the players of the game
     * @return the players of the game
     */
    public ArrayList<Player> getPlayers() {
        return model.getPlayers();
    }

    /**
     * This method gets the number of players inside the game instead of returning an arraylist of players
     * @return the number of players inside the game
     */
    public int getnPlayer() {
        return nPlayers;
    }


}
