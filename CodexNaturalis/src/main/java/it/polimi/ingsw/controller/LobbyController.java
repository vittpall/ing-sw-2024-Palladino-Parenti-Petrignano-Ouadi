package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LobbyController {

    private final Lobby model;
    private final Set<String> usernames = new HashSet<>();


    public LobbyController() {
        model = new Lobby();
    }

    public synchronized boolean checkUsername(String username) {
        return usernames.add(username);
    }

    public HashMap<Integer, Game> getVisibleGames() {
        HashMap<Integer, Game> allGames = model.listAllGames();
        HashMap<Integer, Game> visibleGames = new HashMap<>();
        for (int id : allGames.keySet()) {
            if (allGames.get(id).getPlayers().size() < allGames.get(id).getnPlayer()) {
                visibleGames.put(id, allGames.get(id));
            }
        }
        return visibleGames;
    }

    public synchronized int joinGame(int id, String username) throws InterruptedException {
        Game game = model.getGame(id);
        ArrayList<TokenColor> usedColors = new ArrayList<>();
        TokenColor chosenColor = TokenColor.BLUE;
        for (Player player : game.getPlayers()) {
            usedColors.add(player.getTokenColor());
        }
        for (TokenColor color : TokenColor.values()) {
            if (!usedColors.contains(color)) {
                chosenColor = color;
                break;
            }
        }
        Player player = new Player(chosenColor, username);
        int nPlayer = model.joinGame(id, player);
        if (model.getGame(id).getPlayers().size() < model.getGame(id).getnPlayer()) {
            while (model.getGame(id).getPlayers().size() < model.getGame(id).getnPlayer()) wait();
        } else {
            this.notifyAll();
            model.getGame(id).setUpGame();
        }
        return nPlayer;
    }

    public int createGame(String username, int nPlayers) throws InterruptedException {
        int newGameId = model.createNewGame(nPlayers);
        int nPlayer = this.joinGame(newGameId, username);
        if (nPlayer == 0) {
            return newGameId;
        }
        return -1;
    }

    public ArrayList<ObjectiveCard> getObjectiveCards(int idGame, int idPlayer) {
        return model.getGame(idGame).getPlayers().get(idPlayer).getDrawnObjectiveCards();
    }

    public void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard) throws CardNotFoundException {
        //model.getGame(idGame).getPlayers().get(idClientIntoGame).setObjectiveCard(objCard);
        model.getGame(idGame).setObjectiveCards(idClientIntoGame, idObjCard);
    }

    public StarterCard getStarterCard(int idGame, int idClientIntoGame) {
        return model.getGame(idGame).getPlayers().get(idClientIntoGame).getStarterCard();
    }

    public void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown)
            throws CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        GameCard starterCard = model.getGame(idGame).getPlayers().get(idClientIntoGame).getStarterCard();
        model.getGame(idGame).getPlayers().get(idClientIntoGame).playCard(starterCard, playedFacedDown, new Point(0,0));
    }

    public ObjectiveCard getObjectiveCard(int idGame, int idClientIntoGame) {
        return model.getGame(idGame).getPlayers().get(idClientIntoGame).getObjectiveCard();
    }

    public ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) {
        return model.getGame(idGame).getPlayers().get(idClientIntoGame).getPlayerHand();
    }

    public ObjectiveCard[] getSharedObjectiveCards(int idGame) {
        return model.getGame(idGame).getSharedObjectiveCards();
    }
    public synchronized ArrayList<TokenColor> getAvailableColors(int idGame){
        return model.getGame(idGame).getAvailableColors();
    }
    public synchronized void setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor){
        model.getGame(idGame).setTokenColor(idClientIntoGame, tokenColor);
    }

    public int getCurrentPlayer(int idGame) {
        return model.getGame(idGame).getCurrentPlayerIndex();
    }

    public void playCard(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        model.getGame(idGame).playCard(chosenCard, idClientIntoGame, faceDown, chosenPosition);
    }

    public  void drawCard(int idGame, int idClientIntoGame, int deckToChoose, int inVisible) throws CardNotFoundException{
        //TODO: completare il metodo e vedere come mettere meglio il synchronized
        synchronized(model.getGame(idGame)){
            Deck chosenDeck=null;//da decidere
            GameCard chosenCard=null;
            int currentPlayerPoints;
            if(inVisible==3)
                currentPlayerPoints=model.getGame(idGame).drawCard(chosenDeck);
            else
                currentPlayerPoints= model.getGame(idGame).drawVisibleCard(chosenDeck, chosenCard);
            model.getGame(idGame).notifyAll();
        }
    }

    public void waitForYourTurn(int idGame, int idClientIntoGame) throws InterruptedException{
        synchronized (model.getGame(idGame)) {
            while (model.getGame(idGame).getCurrentPlayerIndex() != idClientIntoGame) model.getGame(idGame).wait();
        }
    }

    public boolean getIsLastRoundStarted(int idGame) {
        return model.getGame(idGame).getIsLastRoundStarted();
    }
}
