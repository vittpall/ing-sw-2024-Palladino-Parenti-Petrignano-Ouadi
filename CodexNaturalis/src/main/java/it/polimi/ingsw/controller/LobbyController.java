package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Card;
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
import it.polimi.ingsw.model.observer.Observer;
import it.polimi.ingsw.model.observer.Observable;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class LobbyController implements Observable {

    private final Set<String> usernames = new HashSet<>();
    private final Map<Integer, GameController> gameControllers;
    private final ArrayList<Integer> unusedIdGame;
    private final Map<String, ArrayList<GameListener>> listeners;
    private int nextGameId;
    private ArrayList<GameListener> lobbyListeners;


    public LobbyController() {
        this.listeners = new HashMap<>();
        gameControllers = new HashMap<>();
        unusedIdGame = new ArrayList<>();
        nextGameId = 1;
        lobbyListeners = new ArrayList<>();
    }

    public boolean checkUsername(String username, GameListener lobbyListener) {
        synchronized (this.usernames) {
            boolean addedUsername = usernames.add(username);
            if(addedUsername){
                registerLobbyListener(lobbyListener);
            }
            return addedUsername;
        }

    }

    private void registerLobbyListener(GameListener lobbyListener) {
    }

    public synchronized void removeUsername(String username) {
        synchronized (this.usernames) {
            usernames.remove(username);
        }

    }

    public ArrayList<Integer> getVisibleGames() {
        ArrayList<Integer> visibleGameControllers = new ArrayList<>();
        for (int id : gameControllers.keySet()) {
            if (gameControllers.get(id).getPlayers().size() < gameControllers.get(id).getnPlayer()) {
                visibleGameControllers.add(id);
            }
        }
        return visibleGameControllers;
    }

    public ArrayList<Player> getAllPlayers(int gameId) {
        return gameControllers.get(gameId).getPlayers();
    }

    public ArrayList<Message> getMessages(String receiver, int gameId, String sender) {
        return gameControllers.get(gameId).getMessages(receiver, sender);
    }

    public int joinGame(int id, String username, GameListener playerListener) throws InterruptedException, RemoteException {
        return gameControllers.get(id).joinGame(username, playerListener);
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

        //to move the user to the another list, 'cause he is not the in the lobby anymore
        unregisterLobbyListener(playerListener);
        notifyLobbyListeners();

        int nPlayer = this.joinGame(id, username, playerListener);
        if (nPlayer == 0) {
            return id;
        }
        return -1;
    }

    private void notifyLobbyListeners() {
    }

    private void unregisterLobbyListener(GameListener playerListener) {
    }

    public ArrayList<ObjectiveCard> getObjectiveCards(int idGame, int idPlayer) {
        return gameControllers.get(idGame).getObjectiveCards(idPlayer);
    }

    public void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard) throws CardNotFoundException {
        //model.getGame(idGame).getPlayers().get(idClientIntoGame).setObjectiveCard(objCard);
        gameControllers.get(idGame).setObjectiveCard(idClientIntoGame, idObjCard);
    }

    public StarterCard getStarterCard(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getStarterCard();
    }

    public void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown)
            throws CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        gameControllers.get(idGame).playStarterCard(idClientIntoGame, playedFacedDown);
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

    public synchronized void setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor) throws IOException {
        gameControllers.get(idGame).setTokenColor(idClientIntoGame, tokenColor);
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

    public Card getLastCardOfUsableCards(int idGame, int deck) {
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

    public void closeGame(int idGame) {
        //TODO:notificare i giocatori del gioco specifico che il gioco viene chiuso dato che un giocatore l'ha fatto
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
        return gameControllers.get(idGame).getCurrentState();
    }

    public PlayerState getCurrentPlayerState(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayerState(idClientIntoGame);
    }


    @Override
    public void subscribeListener(GameListener listener, String eventToListen) {
        listeners.computeIfAbsent(eventToListen, k -> new ArrayList<>());
        listeners.get(eventToListen).add(listener);
    }

    @Override
    public void unSubscribeListener(GameListener listener, String eventToListen) {
        listeners.get(eventToListen).remove(listener);
    }

    @Override
    public void notifyObserver(String eventToListen, ReturnableObject messageToShow) {

    }
}
