package it.polimi.ingsw.model;

import java.util.ArrayList;

import it.polimi.ingsw.model.Exceptions.*;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
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
    private boolean gameStarted;
    private ArrayList<StarterCard> starterCards;

    private ArrayList<ObjectiveCard> objectiveCards;

    /**
     * constructor
     * creates goldDeck, resourceDeck and sharedObjectiveCards
     * creates the list players and adds the first player with the username  usernamePlayer to it
     * sets gameId=id, nPlayer=n, isLastRoundStarted=false, gameStarted=false and currentPlayerIndex=0
     * @param id
     * @param n
     * @param usernamePlayer
     * @param color
     */
    public Game(int id, int n, String usernamePlayer, TokenColor color){
        //inizializzare starterCards e objectiveCards inserendoci tutte le carte iniziali e obiettivo possibili
        //queste carte andranno distribuite tra i giocatori e sul tavolo
        nPlayer=n;
        gameId=id;
        isLastRoundStarted=false;
        gameStarted=false;
        currentPlayerIndex=0;
        this.sharedObjectiveCards=new ObjectiveCard[2];
        double nRandom=Math.random()*objectiveCards.size();
        this.sharedObjectiveCards[0]=objectiveCards.remove((int)nRandom);
        nRandom=Math.random()*objectiveCards.size();
        this.sharedObjectiveCards[1]=objectiveCards.remove((int)nRandom);
        players=new ArrayList<>();
        addPlayer(color, usernamePlayer);

        //vanno creati i deck
    }

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
    public ObjectiveCard[] getSharedObjectiveCards() {return sharedObjectiveCards;}

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
     * @return gameStarted
     */
    public boolean isGameStarted() {
        return gameStarted;
    }


    /**
     * if old(players.size())<nPlayer, creates the players and adds it into players
     * if old(players.size())+1=nPlayer, starts the game
     *
     * @param color
     * @param username
     */
    public void addPlayer(TokenColor color, String username){
        //si può chiamare solo se gameStarted==false
        double nRandom=Math.random()*starterCards.size();
        StarterCard starter = starterCards.remove((int)nRandom);
        ArrayList<ObjectiveCard> playerObjectiveCard= new ArrayList<>();
        nRandom=Math.random()*objectiveCards.size();
        playerObjectiveCard.add(objectiveCards.remove((int)nRandom));
        nRandom=Math.random()*objectiveCards.size();
        playerObjectiveCard.add(objectiveCards.remove((int)nRandom));
        players.add(new Player(color, username,resourceDeck, goldDeck, playerObjectiveCard, starter));
        if(players.size()==nPlayer)
            gameStarted=true;
    }

    /**
     * plays card into the current player's desk
     * @param card
     */
    public void playCard(GameCard card, boolean faceDown, int x, int y){
        try{
            players.get(currentPlayerIndex).playCard(card, faceDown, x, y);
        }catch(CardNotFoundException e){
            //da fare
        }catch(RequirementsNotMetException e){
            //da fare
        }
    }

    /**
     * draws a card and puts it into the current player's hand
     * @param deck
     * @return the current player's points
     */
    public int drawCard(Deck deck){
        players.get(currentPlayerIndex).draw(deck);
        if(players.get(currentPlayerIndex).getPoints()>=20)
            isLastRoundStarted=true;
        getNextPlayer();
        return players.get(currentPlayerIndex).getPoints();
    }

    /**
     * draws the card sent as a parameter and puts it into the current player's hand
     * @param deck
     * @param card
     * @return the current player's points
     */
    public int drawVisibleCard(Deck deck, GameCard card){
        try {
            players.get(currentPlayerIndex).drawVisible(deck, card);
        }catch(CardNotFoundException e){
            //da fare
        }
        if(players.get(currentPlayerIndex).getPoints()>=20)
            isLastRoundStarted=true;
        getNextPlayer();
        return players.get(currentPlayerIndex).getPoints();
    }

    /**
     * change the currentPlayerIndex
     */
    private void getNextPlayer(){
        currentPlayerIndex=(currentPlayerIndex+1)%nPlayer;
    }

    /**
     * calculates if the players' objectives are met and adds the points to the players
     * calculates the winner and ends the game
     * @return the winner of the game
     */
    public Player endGame(){
        int pointsMax=0;
        int nObjectiveMetWinner=0;
        Player winner=null;
        for(Player currentPlayer : players){
            int nObjectiveMetPlayer=currentPlayer.checkObjective(sharedObjectiveCards);
            int currentPlayerPoints= currentPlayer.getPoints();
            if(currentPlayerPoints>pointsMax||
                    (currentPlayerPoints==pointsMax&&nObjectiveMetPlayer>nObjectiveMetWinner)){
                pointsMax=currentPlayerPoints;
                nObjectiveMetWinner=nObjectiveMetPlayer;
                winner=currentPlayer;
            }
        }
        return winner;
    }
}