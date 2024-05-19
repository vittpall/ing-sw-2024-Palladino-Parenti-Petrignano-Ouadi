package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.util.*;

public class LobbyController {

    private final Set<String> usernames = new HashSet<>();
    private final Map<Integer, GameController> gameControllers;
    private final ArrayList<Integer> unusedIdGame;
    private int nextGameId;


    public LobbyController() {
        gameControllers = new HashMap<>();
        unusedIdGame = new ArrayList<>();
        nextGameId = 1;
    }

    public boolean checkUsername(String username) {
        synchronized (this.usernames) {
            return usernames.add(username);
        }

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

    public int joinGame(int id, String username) throws InterruptedException {
        synchronized (gameControllers.get(id)){
            int nPlayer = gameControllers.get(id).joinGame(username);
            //int nPlayer = model.joinGame(id, player);
            if (gameControllers.get(id).getPlayers().size() < gameControllers.get(id).getnPlayer()) {
                while (gameControllers.get(id).getPlayers().size() < gameControllers.get(id).getnPlayer()) gameControllers.get(id).wait();
            } else {
                gameControllers.get(id).notifyAll();
            }
            return nPlayer;
        }
    }

    public int createGame(String username, int nPlayers) throws InterruptedException {
        int id;
        if (!unusedIdGame.isEmpty()) {
            id = unusedIdGame.getFirst();
            unusedIdGame.removeFirst();
        } else {
            id = nextGameId;
            nextGameId++;
        }
        GameController gameController = new GameController(id, nPlayers);
        gameControllers.put(id, gameController);

        //int newGameId = model.createNewGame(nPlayers);

        int nPlayer = this.joinGame(id, username);
        if (nPlayer == 0) {
            return id;
        }
        return -1;
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

    public synchronized ArrayList<TokenColor> getAvailableColors(int idGame) {
        return gameControllers.get(idGame).getAvailableColors();
    }

    public synchronized void setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor) {
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

    public void playLastTurn(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        gameControllers.get(idGame).playLastTurn(chosenCard, idClientIntoGame, faceDown, chosenPosition);
    }

    public void drawCard(int idGame, int idClientIntoGame, int deckToChoose, int inVisible) throws CardNotFoundException {
        gameControllers.get(idGame).drawCard(idClientIntoGame, deckToChoose, inVisible);
    }

    /*public void waitForYourTurn(int idGame, int idClientIntoGame) throws InterruptedException {
        synchronized (model.getGame(idGame)) {
            while (model.getGame(idGame).getCurrentPlayerIndex() != idClientIntoGame) model.getGame(idGame).wait();
        }
    }*/

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

    public String getUsernamePlayerThatStoppedTheGame(int idGame) {
        return gameControllers.get(idGame).getUsernamePlayerThatStoppedTheGame();
    }

    public HashMap<Point, GameCard> getPlayerDesk(int idGame, int idClientIntoGame) {
        return gameControllers.get(idGame).getPlayers().get(idClientIntoGame).getPlayerDesk().getDesk();
    }

    public String getWinner(int idGame, int idClientIntoGame) throws InterruptedException {
        return gameControllers.get(idGame).getWinner(idClientIntoGame);
    }

    /*public Player getNextPlayer(int idGame) {
        model.getGame(idGame).advanceToNextPlayer();
        return model.getGame(idGame).getCurrentPlayer();
    }*/

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
}
