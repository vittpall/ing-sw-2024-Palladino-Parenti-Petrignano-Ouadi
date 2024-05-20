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
import java.util.ArrayList;
import java.util.HashSet;

public class GameController {
    Game model;
    int nPlayers;
    int nPlayersPlaying;
    GameState gameState;
    String winner;

    //come creare il gioco e la classe
    public GameController(int idGame, int nPlayers) {
        this.nPlayers = nPlayers;
        model = new Game(idGame, nPlayers);
        nPlayersPlaying = 0;
        gameState = GameState.WAITING_FOR_PLAYERS;
        winner = "No winner";
    }

    public boolean checkState(int idPlayerIntoGame, RequestedActions requestedActions) {
        return switch (requestedActions) {
            case RequestedActions.DRAW ->
                    (gameState == GameState.ROUNDS || gameState == GameState.FINISHING_ROUND_BEFORE_LAST)
                            && model.getPlayers().get(idPlayerIntoGame).getPlayerState() == PlayerState.DRAW;
            case RequestedActions.PLAY_CARD ->
                    (gameState == GameState.ROUNDS || gameState == GameState.LAST_ROUND || gameState == GameState.FINISHING_ROUND_BEFORE_LAST)
                            && model.getPlayers().get(idPlayerIntoGame).getPlayerState() == PlayerState.PLAY_CARD;
            case RequestedActions.SHOW_WINNER -> gameState == GameState.ENDGAME;
            case RequestedActions.SHOW_DESKS, RequestedActions.SHOW_OBJ_CARDS,
                 RequestedActions.SHOW_POINTS, RequestedActions.CHAT -> true;
        };
    }

    public String getCurrentState(int idClientIntoGame) {
        return gameState.toString() + " " + model.getPlayers().get(idClientIntoGame).getPlayerState().toString();
    }

    public String getCurrentState() {
        return gameState.toString();
    }

    public synchronized int joinGame(String username) throws InterruptedException {
        Player player = new Player(username);
        int idPlayer = model.addPlayer(player);
        nPlayersPlaying++;
        if (model.getPlayers().size() == model.getnPlayer()) {
            model.setUpGame();
            gameState = GameState.SETUP_GAME;
            for (int i = 0; i < model.getnPlayer(); i++) {
                model.getPlayers().get(i).setPlayerState(PlayerState.SETUP_GAME);
            }
        }
        return idPlayer;
    }

    public ArrayList<ObjectiveCard> getObjectiveCards(int idPlayer) {
        return model.getPlayers().get(idPlayer).getDrawnObjectiveCards();
    }

    public void setObjectiveCard(int idClientIntoGame, int idObjCard) throws CardNotFoundException {
        //model.getGame(idGame).getPlayers().get(idClientIntoGame).setObjectiveCard(objCard);
        model.setObjectiveCards(idClientIntoGame, idObjCard);
    }


    public StarterCard getStarterCard(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getStarterCard();
    }

    public synchronized void playStarterCard(int idClientIntoGame, boolean playedFacedDown)
            throws CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        GameCard starterCard = model.getPlayers().get(idClientIntoGame).getStarterCard();
        model.getPlayers().get(idClientIntoGame).playCard(starterCard, playedFacedDown, new Point(0, 0));
        if (model.getCurrentPlayerIndex() == idClientIntoGame)
            model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.PLAY_CARD);
        else
            model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.WAITING);
        if (model.getPlayers().stream().allMatch(player -> (!player.getPlayerState().equals(PlayerState.SETUP_GAME))))
            gameState = GameState.ROUNDS;
    }

    public ObjectiveCard getObjectiveCard(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getObjectiveCard();
    }

    public ArrayList<GameCard> getPlayerHand(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getPlayerHand();
    }

    public ObjectiveCard[] getSharedObjectiveCards() {
        return model.getSharedObjectiveCards();
    }

    public synchronized ArrayList<TokenColor> getAvailableColors() {
        return model.getAvailableColors();
    }

    public synchronized void setTokenColor(int idClientIntoGame, TokenColor tokenColor) {
        model.setTokenColor(idClientIntoGame, tokenColor);
    }

    public ArrayList<Player> getAllPlayers() {
        return model.getPlayers();
    }

    public ArrayList<Message> getMessages(String receiver, String sender) {
        if (receiver == null)
            return model.getChats().getGlobalChat();
        else
            return model.getChats().getPrivateChat(receiver, sender);
    }

    public void sendMessage(Message msg) {
        model.getChats().addMessage(msg);
    }

    public int getCurrentPlayer() {
        return model.getCurrentPlayerIndex();
    }


    public void playCard(int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        model.playCard(chosenCard, idClientIntoGame, faceDown, chosenPosition);
        model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.DRAW);
        if (gameState == GameState.LAST_ROUND) {
            model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.ENDGAME);
            if (model.getnPlayer() != idClientIntoGame + 1)
                model.advanceToNextPlayer();
            else {
                gameState = GameState.ENDGAME;
                winner = model.endGame();
            }
        }
    }

    public synchronized void playLastTurn(int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {

        this.playCard(idClientIntoGame, chosenCard, faceDown, chosenPosition);
        model.getPlayers().get(idClientIntoGame).setPlayerState(PlayerState.ENDGAME);
        if (model.getnPlayer() != idClientIntoGame + 1)
            model.advanceToNextPlayer();
        else {
            gameState = GameState.ENDGAME;
            winner = model.endGame();
        }
        // TODO: cambiare stato game
        //TODO: mandare messaggino ai client notificando che è cambiato il turno (vedi notifiche chat)
    }

    public synchronized void drawCard(int idClientIntoGame, int deckToChoose, int inVisible) throws CardNotFoundException {
        //TODO: fare il check che sia il suo turno
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
        if (gameState != GameState.LAST_ROUND && model.getPlayers().get(model.getCurrentPlayerIndex()).getPoints() >= 20) {
            if (model.getCurrentPlayerIndex() == model.getnPlayer() - 1)
                gameState = GameState.LAST_ROUND;
            else
                gameState = GameState.FINISHING_ROUND_BEFORE_LAST;
        }
        if (gameState == GameState.FINISHING_ROUND_BEFORE_LAST && model.getCurrentPlayerIndex() == model.getnPlayer() - 1) {
            gameState = GameState.LAST_ROUND;
        }
        model.getCurrentPlayer().setPlayerState(PlayerState.WAITING);
        model.advanceToNextPlayer();
        model.getCurrentPlayer().setPlayerState(PlayerState.PLAY_CARD);
        //TODO: mandare messaggino ai client notificando che è cambiato il turno (vedi notifiche chat)
    }


    public boolean getIsLastRoundStarted() {
        return model.getIsLastRoundStarted();
    }

    public HashSet<Point> getAvailablePlaces(int idClientIntoGame) {
        return model.getPlayers().get(idClientIntoGame).getPlayerDesk().getAvailablePlaces();
    }

    public ArrayList<GameCard> getVisibleCardsDeck(int deck) {
        if (deck == 1)
            return model.getResourceDeck().getVisibleCards();
        return model.getGoldDeck().getVisibleCards();
    }

    public Card getLastCardOfUsableCards(int deck) {
        if (deck == 1)
            return model.getResourceDeck().getUsableCards().getLast();
        return model.getGoldDeck().getUsableCards().getLast();
    }

    public String getUsernamePlayerThatStoppedTheGame() {
        return model.getUsernamePlayerThatStoppedTheGame();
    }

    public synchronized String getWinner(int idClientIntoGame) throws InterruptedException {
        return winner;
    }


    public ArrayList<Player> getPlayers() {
        return model.getPlayers();
    }

    public int getnPlayer() {
        return model.getnPlayer();
    }

    public Deck getResourceDeck() {
        return model.getResourceDeck();
    }

    public Deck getGoldDeck() {
        return model.getGoldDeck();
    }
    //gestire la chiusura
}
