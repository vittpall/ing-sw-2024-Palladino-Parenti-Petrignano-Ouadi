package it.polimi.ingsw.model.observer;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.notifications.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Observable {
    private final HashSet<GameListener> listeners;

    /**
     * Constructor of the class Observable that initializes the HashSet of listeners
     */
    public Observable() {
        listeners = new HashSet<>();
    }

    /**
     * Constructor
     *
     * @param listener is the listener that will receive the notifications
     */
    public void subscribeListener(GameListener listener) {
        listeners.add(listener);
    }

    /**
     * Constructor
     *
     * @param listener is the listener that will no longer receive the notifications
     */
    public void unSubscribeListener(GameListener listener) {
        listeners.remove(listener);
    }

    /**
     * This method notifies the listeners that a player has selected a color
     *
     * @param msg             is the message that will be shown to the players
     * @param availableColors is the list of the available colors
     */
    public void notifyColorSelection(String msg, ArrayList<TokenColor> availableColors) {
        notifyListeners(new TokenColorTakenNotification(msg, availableColors), null);
    }

    /**
     * This method is used to notify the listeners the number of players that have joined the game and the number of missing players.
     *
     * @param players           is the list of the players that have joined the game
     * @param nOfMissingPlayers is the number of missing players
     * @param usernameSender    is the username of the player that has joined the game
     */
    public void notifyJoinedGame(ArrayList<Player> players, int nOfMissingPlayers, String usernameSender) {
        notifyListeners(new GameJoinedNotification(players, nOfMissingPlayers), usernameSender);
    }

    /**
     * this method notifies the listener that a player has joined one of the games
     *
     * @param msg            is the message that will be shown to the players
     * @param availableGames is the list of the available games
     */
    public void notifyJoinedGameToOutsider(String msg, HashMap<Integer, Integer[]> availableGames) {
        notifyListeners(new GameJoinedNotificationToOutsiders(msg, availableGames), null);
    }

    /**
     * this method notifies that one player has drawn a card and that it's the turn of the next player
     *
     * @param message        is the message that will be shown to the listener
     * @param username       is the username of the player that has drawn the card
     * @param usernameSender is the username of the player that has just finished his turn
     */
    public void notifyChangeTurn(String message, String username, String usernameSender) {
        notifyListeners(new ChangeTurnNotification(message, username), usernameSender);
    }

    /**
     * this method notifies the listener that a player has played a card
     *
     * @param message       is the message that will be shown to the listener
     * @param playersPoints is the list of the points of the players
     * @param username      is the username of the player that has played the card
     */
    public void notifyPlayedCard(String message, HashMap<String, Integer> playersPoints, String username) {
        notifyListeners(new PlayedCardNotification(message, playersPoints, username), username);
    }

    /**
     * this method notifies the listener that the last turn has started.
     * This happens when one player has reached 20 points or when each the deck are empty
     *
     * @param username  is the username of the player that has reached 20 points or that cant draw a card
     * @param gameState is the state of the game. the stage where players can play their cards
     */
    public void notifyLastTurnSet(String username, GameState gameState) {
        notifyListeners(new LastTurnSetNotification(username, gameState), null);
    }

    /**
     * this method notifies the listener that someone has sent a message in the chat
     *
     * @param msg is the message that will be shown to the listener
     */
    public void notifyChat(Message msg) {
        notifyListeners(new ChatNotification(msg), null);
    }

    /**
     * this method notifies the listener that the game has ended, who is the winner and the scores of the players
     *
     * @param winner        is the username of the winner
     * @param scores        is the list of the scores of the players
     * @param playersTokens is the list of the tokens of the players
     */
    public void notifyEndGame(String winner, HashMap<String, Integer> scores, HashMap<String, TokenColor> playersTokens) {
        notifyListeners(new EndGameNotification(winner, scores, playersTokens), null);
    }

    /**
     * this method notifies the listener that the game has been closed
     *
     * @param msg is the message that will be shown to the listener
     */
    public void notifyCloseGame(String msg) {
        notifyListeners(new CloseGameNotification(msg), null);
    }

    /**
     * this method sets up the notifies to the listeners
     *
     * @param notification   is the notification that will be sent to the listeners
     * @param usernameSender is the username of the player that has sent the notification
     */
    private void notifyListeners(ServerNotification notification, String usernameSender) {
        for (GameListener listener : listeners) {
            new Thread(() -> {
                try {
                    if (usernameSender == null || !(listener.getUsername()).equals(usernameSender))
                        listener.update(notification);
                } catch (IOException e) {
                    System.out.println("This listener cannot be reached");
                }
            }).start();
        }
    }


}
