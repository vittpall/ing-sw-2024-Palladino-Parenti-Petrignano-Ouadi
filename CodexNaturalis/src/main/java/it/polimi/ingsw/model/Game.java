package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.chat.Chat;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.util.GameCardLoader;
import it.polimi.ingsw.util.ObjectiveCardLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class defines and describes the match
 * players is a list of all the players of the match
 * sharedObjectiveCards is an array containing the shared ObjectiveCard that all the player can meet
 * resourceDeck and GoldDeck represents the decks of that game
 * isLastRoundStarted is set as true when one player achieves 20 points and indicates that there will be one last complete round
 * gameStarted is set as true as soon as nPlayer players have joined the game
 * starterCards and objectiveCards are lists of all the possible starter and objective cards that are not played or drawn yet
 */
public class Game {
    private final int nPlayer;
    private final ArrayList<Player> players;
    private ObjectiveCard[] sharedObjectiveCards;
    private Deck resourceDeck;
    private Deck goldDeck;
    private int currentPlayerIndex;
    private boolean isLastRoundStarted;
    private int playerWhoStoppedTheGame;
    private final ArrayList<StarterCard> starterCards;
    private final ArrayList<ObjectiveCard> objectiveCards;
    private final Chat chats;

    /**
     * constructor
     * creates the starterCards, players and objectiveCards lists as empty
     * sets  nPlayer, isLastRoundStarted, gameStarted and currentPlayerIndex
     *
     * @param n the number of player chosen
     */
    public Game(int n) {
        nPlayer = n;
        isLastRoundStarted = false;
        currentPlayerIndex = 0;
        playerWhoStoppedTheGame = 0;
        starterCards = new ArrayList<>();
        players = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        chats = new Chat();
    }

    /**
     * it sets up the game:
     * creates the goldDeck and resourceDeck
     * it initializes the sharedObjectiveCards and every player's hand, starterCard and drawnObjectiveCards
     * it is called when gameStarted is set as true
     */
    public void setUpGame() {
        ArrayList<GameCard> usableGoldCard = new ArrayList<>();
        ArrayList<GameCard> usableResourceCard = new ArrayList<>();

        GameCardLoader gameCardLoader = new GameCardLoader();
        for (GameCard card : gameCardLoader.loadGameCards()) {
            if (card instanceof GoldCard) {
                usableGoldCard.add(card);
            } else if (card instanceof StarterCard starterCard) {
                starterCards.add(starterCard);
            } else if (card instanceof ResourceCard) {
                usableResourceCard.add(card);
            }
        }
        resourceDeck = new Deck(usableResourceCard);
        goldDeck = new Deck(usableGoldCard);
        resourceDeck.makeTopCardsVisible();
        goldDeck.makeTopCardsVisible();
        //inizializzazione objectiveCard che conterrà tutte le carte obiettivo possibili
        ObjectiveCardLoader objectiveCardLoader = new ObjectiveCardLoader();
        objectiveCards.addAll(objectiveCardLoader.loadObjectiveCards());
        //inizializzazione delle 2 carte obiettivo condivise
        this.sharedObjectiveCards = new ObjectiveCard[2];
        double nRandom = Math.random() * objectiveCards.size();
        this.sharedObjectiveCards[0] = objectiveCards.remove((int) nRandom);
        nRandom = Math.random() * objectiveCards.size();
        this.sharedObjectiveCards[1] = objectiveCards.remove((int) nRandom);
        //viene settata la mano iniziale e la starter card del player
        for (Player player : players) {
            player.setPlayerHand(resourceDeck, goldDeck);
            nRandom = Math.random() * starterCards.size();
            player.setStarterCard(starterCards.remove((int) nRandom));
            //playerObjCards le 2 carte obiettivo da cui i giocatori sceglieranno quella specifica
            //questa specifica viene settata chiamando setObjectiveCard dal controller su ogni player
            ArrayList<ObjectiveCard> playerObjCards = new ArrayList<>();
            nRandom = Math.random() * objectiveCards.size();
            playerObjCards.add(objectiveCards.remove((int) nRandom));
            nRandom = Math.random() * objectiveCards.size();
            playerObjCards.add(objectiveCards.remove((int) nRandom));
            player.setDrawnObjectiveCards(playerObjCards);
        }
    }

    public ArrayList<TokenColor> getAvailableColors() {
        ArrayList<TokenColor> usedColors = new ArrayList<>();
        for (Player player : players) {
            if (player.getTokenColor() != null)
                usedColors.add(player.getTokenColor());
        }
        ArrayList<TokenColor> availableColors = new ArrayList<>();
        for (TokenColor color : TokenColor.values()) {
            if (!usedColors.contains(color)) {
                availableColors.add(color);
            }
        }
        return availableColors;
    }

    public void setTokenColor(int idPlayer, TokenColor color) {
        players.get(idPlayer).setTokenColor(color);
    }


    public void setObjectiveCards(int idPlayer, int chosenCard) throws CardNotFoundException {
        players.get(idPlayer).setObjectiveCard(chosenCard);
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
        return new ArrayList<>(players);
    }

    /**
     * @return sharedObjectiveCards
     */
    public ObjectiveCard[] getSharedObjectiveCards() {
        return Arrays.copyOf(sharedObjectiveCards, sharedObjectiveCards.length);
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

    public Chat getChats() {
        return chats;
    }


    /**
     * adds the player playerToAdd to the list of players of the game
     * it can be called until gameStarted==false
     *
     * @param playerToAdd
     */
    public int addPlayer(Player playerToAdd) {
        players.add(playerToAdd);
        return players.size() - 1;
    }


    /**
     * places the card into the current player's desk at (x,y) position
     *
     * @param idCard   card the user wants to play
     * @param faceDown how the user wants to play it
     * @param point    coordinates of desk where the user wants to play the card
     * @throws CardNotFoundException       when the card sent is not part of the current player's hand
     * @throws RequirementsNotMetException when the card's requirements are not met into the player's desk
     */
    public int playCard(int idCard, int idClient, boolean faceDown, Point point)
            throws CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        if (idClient != currentPlayerIndex) throw new CardNotFoundException("Not your turn");
        GameCard card = players.get(currentPlayerIndex).getPlayerHand().get(idCard);
        return players.get(currentPlayerIndex).playCard(card, faceDown, point);
    }

    /**
     * draws a card and puts it into the current player's hand
     * sets isLastRoundStarted as true if the current player's points are greater or equal to 20
     *
     * @param deck
     */
    public void drawCard(Deck deck) {
        players.get(currentPlayerIndex).draw(deck);
        if (players.get(currentPlayerIndex).getPoints() >= 20) {
            isLastRoundStarted = true;
            playerWhoStoppedTheGame = currentPlayerIndex;
        }
    }

    /**
     * draws the card sent as a parameter and puts it into the current player's hand
     *
     * @param deck
     * @param card
     * @throws CardNotFoundException when card is not part of the deck's visible cards list
     */
    public void drawVisibleCard(Deck deck, GameCard card) throws CardNotFoundException {
        players.get(currentPlayerIndex).drawVisible(deck, card);
        if (players.get(currentPlayerIndex).getPoints() >= 20)
            isLastRoundStarted = true;
    }

    /**
     * change the currentPlayerIndex as the next one
     */
    public void advanceToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % nPlayer;
    }


    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }


    /**
     * calculates if the players' objectives are met and adds the points of the met objectives  to the players
     * calculates the winner and ends the game
     *
     * @return the winner of the game
     */
    public String endGame() {
        int pointsMax = 0;
        int nObjectiveMetWinner = 0;
        String winner = null;
        for (Player currentPlayer : players) {
            int nObjectiveMetPlayer = currentPlayer.checkObjective(sharedObjectiveCards);
            int currentPlayerPoints = currentPlayer.getPoints();
            if (currentPlayerPoints > pointsMax ||
                    (currentPlayerPoints == pointsMax && nObjectiveMetPlayer > nObjectiveMetWinner)) {
                pointsMax = currentPlayerPoints;
                nObjectiveMetWinner = nObjectiveMetPlayer;
                winner = currentPlayer.getUsername();
            } else if (currentPlayerPoints == pointsMax && nObjectiveMetPlayer == nObjectiveMetWinner) {
                winner = winner + " and " + currentPlayer.getUsername();
            }
        }
        if (winner == null) return "No winner";
        return winner;
    }

    public String getUsernamePlayerThatStoppedTheGame() {
        return players.get(playerWhoStoppedTheGame).getUsername();
    }
}