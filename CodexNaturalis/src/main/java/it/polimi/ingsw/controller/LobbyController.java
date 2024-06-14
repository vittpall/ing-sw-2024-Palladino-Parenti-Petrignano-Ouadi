package it.polimi.ingsw.controller;

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
import it.polimi.ingsw.model.observer.Observable;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class LobbyController {

    private final Set<String> usernames = new HashSet<>();
    private final Map<Integer, GameController> gameControllers;
    private final ArrayList<Integer> unusedIdGame;
    private int nextGameId;
    private final Observable lobbyListeners;


    public LobbyController() {
        gameControllers = new HashMap<>();
        unusedIdGame = new ArrayList<>();
        nextGameId = 1;
        lobbyListeners = new Observable();
    }

    public boolean checkUsername(String username, GameListener lobbyListener) {
        synchronized (this.usernames) {
            return usernames.add(username);
        }

    }

    public synchronized void removeUsername(String username) {
        synchronized (this.usernames) {
            usernames.remove(username);
        }
    }

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

    public ArrayList<Player> getAllPlayers(int gameId) {
        return gameControllers.get(gameId).getPlayers();
    }

    public ArrayList<Message> getMessages(String receiver, int gameId, String sender) {
        return gameControllers.get(gameId).getMessages(receiver, sender);
    }

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

    public ArrayList<ObjectiveCard> getObjectiveCards(int idGame, int idPlayer, GameListener playerListener) {
        return gameControllers.get(idGame).getObjectiveCards(idPlayer, playerListener);
    }

    public void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard, GameListener playerListener) throws CardNotFoundException {
        //model.getGame(idGame).getPlayers().get(idClientIntoGame).setObjectiveCard(objCard);
        gameControllers.get(idGame).setObjectiveCard(idClientIntoGame, idObjCard, playerListener);
    }

    public StarterCard getStarterCard(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getStarterCard();
    }

    public void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown, GameListener playerListener)
            throws CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        gameControllers.get(idGame).playStarterCard(idClientIntoGame, playedFacedDown, playerListener);
    }

    public ObjectiveCard getObjectiveCard(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getObjectiveCard(idClientIntoGame);
    }

    public ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPlayerHand();
    }

    public ObjectiveCard[] getSharedObjectiveCards(int idGame) {
        return gameControllers.get(idGame).getSharedObjectiveCards();
    }

    public synchronized ArrayList<TokenColor> getAvailableColors(int idGame, GameListener playerListener) {
        return gameControllers.get(idGame).getAvailableColors(playerListener);
    }

    public synchronized void setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor, GameListener playerListener) throws IOException {
        gameControllers.get(idGame).setTokenColor(idClientIntoGame, tokenColor, playerListener);
    }

    public int getPoints(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPoints();
    }

    public int getCurrentPlayer(int idGame) {
        return gameControllers.get(idGame).getCurrentPlayer();
    }

    public void playCard(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        gameControllers.get(idGame).playCard(idClientIntoGame, chosenCard, faceDown, chosenPosition);
    }

    public void drawCard(int idGame, int deckToChoose, int inVisible) throws CardNotFoundException {
        gameControllers.get(idGame).drawCard(deckToChoose, inVisible);
    }

    public boolean getIsLastRoundStarted(int idGame) {
        return gameControllers.get(idGame).getIsLastRoundStarted();
    }

    public void sendMessage(int idGame, Message msg) {
        gameControllers.get(idGame).sendMessage(msg);
    }

    public HashSet<Point> getAvailablePlaces(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPlayerDesk().getAvailablePlaces();
    }

    public ArrayList<GameCard> getVisibleCardsDeck(int idGame, int deck) {
        return gameControllers.get(idGame).getVisibleCardsDeck(deck);
    }

    public GameCard getLastCardOfUsableCards(int idGame, int deck) {
        return gameControllers.get(idGame).getLastCardOfUsableCards(deck);
    }

    public String getUsernamePlayerThatStoppedTheGame(int idGame) {
        return gameControllers.get(idGame).getUsernamePlayerThatStoppedTheGame();
    }

    public HashMap<Point, GameCard> getPlayerDesk(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPlayerDesk().getDesk();
    }

    public String getWinner(int idGame) {
        return gameControllers.get(idGame).getWinner();
    }

    public void closeGame(int idGame, String userThatLeft) throws IOException {
        //TODO:notificare i giocatori del gioco specifico che il gioco viene chiuso dato che un giocatore l'ha fatto
        gameControllers.get(idGame).closeGame(userThatLeft);
        gameControllers.remove(idGame);
    }

    public int getnPlayer(int idGame) {
        return gameControllers.get(idGame).getnPlayer();
    }

    public ArrayList<Player> getPlayers(int idGame) {
        return gameControllers.get(idGame).getPlayers();
    }

    public boolean checkState(int idGame, int idPlayerIntoGame, RequestedActions requestedActions) {
        return gameControllers.get(idGame).checkState(idPlayerIntoGame, requestedActions);
    }

    public String getCurrentState(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getCurrentState(idClientIntoGame);
    }

    public String getCurrentGameState(int idGame) {
        return gameControllers.get(idGame).getGameState();
    }

    public PlayerState getCurrentPlayerState(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayerState(idClientIntoGame);
    }


}
