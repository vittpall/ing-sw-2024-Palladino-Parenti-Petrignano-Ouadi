package it.polimi.ingsw.model;

import java.util.ArrayList;
import it.polimi.ingsw.model.enumeration.TokenColor;

public class Game {
    private final int gameId;
    private final int nPlayer;
    private ArrayList<Player> players;
    private final ObjectiveCard[] sharedObjectiveCards;
    private Deck resourceDeck;
    private Deck goldDeck;
    private int currentPlayerIndex;
    private boolean isLastRoundStarted;

    /**
     * constructor
     * creates goldDeck, resourceDeck and sharedObjectiveCards
     * creates the list players and adds the first player with the username  usernamePlayer to it
     * sets gameId=id, nPlayer=n, isLastRoundStarted=0 and currentPlayerIndex=0
     * @param id
     * @param n
     * @param usernamePlayer
     */
    public Game(int id, int n, String usernamePlayer){}

    /**
     * @return gameId
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @return nPlayer
     */
    public int getnPlayer() {
        return nPlayer;
    }

    /**
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    /**
     * @return sharedObjectiveCards
     */
    public ObjectiveCard[] getSharedObjectiveCards() {
    }

    /**
     * @return resourceDeck
     */
    public Deck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * @return goldDeck
     */
    public Deck getGoldDeck() {
        return goldDeck;
    }

    /**
     * @return currentPlayerIndex
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * @return isLastRoundStarted
     */
    public boolean getIsLastRoundStarted() {
        return isLastRoundStarted;
    }

    /**
     * @param playerIndex
     * @return the points of the player sent as an attribute
     */
    public int getPoint(int playerIndex){
        return players.get(playerIndex).getPoints();
    }

    /**
     * sets the player that creates the game as the first
     * @param player
     */
    private void setFirstPlayer(int player){

    }

    /**
     * if old(players.size())<nPlayer, creates the players and adds it into players
     * if old(players.size())+1=nPlayer, starts the game
     * @param color
     * @param username
     */
    public void addPlayer(TokenColor color, String username){

    }

    /**
     * plays card into the current player's desk
     * @param card
     */
    public void playCard(GameCard card){}

    /**
     * draws a card and puts it into the current player's hand
     * @param deck
     * @return the current player's points
     */
    public int drawCard(Deck deck){}

    /**
     * draws the card sent as a parameter and puts it into the current player's hand
     * @param deck
     * @param card
     * @return the current player's points
     */
    public int drawVisibleCard(Deck deck, GameCard card){}

    /**
     * change the currentPlayerIndex
     */
    private void getNextPlayer(){
        if(currentPlayerIndex==nPlayer-1)
            currentPlayerIndex=0;
        else
            currentPlayerIndex++;
    }

    /**
     * calculates if the players' objectives are met and adds the points to the players
     * calculates the winner and ends the game
     */
    public void endGame(){}
}