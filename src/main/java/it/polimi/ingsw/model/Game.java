package it.polimi.ingsw.model;

import it.polimi.ingsw.model.chat.Chat;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.controller.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.controller.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.util.GameCardLoader;
import it.polimi.ingsw.util.ObjectiveCardLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class defines and describes a game in the application.
 * nPlayer the number of players in the game.
 * players a list of all the players in the game.
 * sharedObjectiveCards an array containing the shared ObjectiveCard that all the player can meet.
 * resourceDeck and GoldDeck represents the decks of the game
 * currentPlayerIndex the index of the player who is playing.
 * starterCards and objectiveCards are lists of all the possible starter and objective cards that are not played or drawn yet.
 * chats are the chats of the game
 */
public class Game {
    private final int nPlayer;
    private final ArrayList<Player> players;
    private ObjectiveCard[] sharedObjectiveCards;
    private Deck resourceDeck;
    private Deck goldDeck;
    private int currentPlayerIndex;
    private final ArrayList<StarterCard> starterCards;
    private final ArrayList<ObjectiveCard> objectiveCards;
    private final Chat chats;

    /**
     * The constructor for the Game class. It initializes the fields with the provided parameters and default values.
     *
     * @param n the number of players in the game.
     */
    public Game(int n) {
        nPlayer = n;
        currentPlayerIndex = 0;
        starterCards = new ArrayList<>();
        players = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        chats = new Chat();
    }

    /**
     * This method sets up the game. It creates the goldDeck and resourceDeck, initializes the sharedObjectiveCards,
     * and sets up each player's hand, starterCard, and drawnObjectiveCards. It is called when the game starts.
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
        //initializing objectiveCard with all the possible objective cards
        ObjectiveCardLoader objectiveCardLoader = new ObjectiveCardLoader();
        objectiveCards.addAll(objectiveCardLoader.loadObjectiveCards());
        //initialize the sharedObjectiveCards with 2 random objective cards
        this.sharedObjectiveCards = new ObjectiveCard[2];
        double nRandom = Math.random() * objectiveCards.size();
        this.sharedObjectiveCards[0] = objectiveCards.remove((int) nRandom);
        nRandom = Math.random() * objectiveCards.size();
        this.sharedObjectiveCards[1] = objectiveCards.remove((int) nRandom);
        //initialize the players' hand, starterCard and drawnObjectiveCards
        for (Player player : players) {
            player.setPlayerHand(resourceDeck, goldDeck);
            nRandom = Math.random() * starterCards.size();
            player.setStarterCard(starterCards.remove((int) nRandom));
            ArrayList<ObjectiveCard> playerObjCards = new ArrayList<>();
            nRandom = Math.random() * objectiveCards.size();
            playerObjCards.add(objectiveCards.remove((int) nRandom));
            nRandom = Math.random() * objectiveCards.size();
            playerObjCards.add(objectiveCards.remove((int) nRandom));
            player.setDrawnObjectiveCards(playerObjCards);
        }
    }

    /**
     * method that returns the available colors for the players
     *
     * @return the list of available colors that the players can choose
     */
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

    /**
     * This method sets the token  for a player.
     *
     * @param idPlayer the ID of the player.
     * @param color    the token to set.
     */
    public void setTokenColor(int idPlayer, TokenColor color) {
        players.get(idPlayer).setTokenColor(color);
    }

    /**
     * This method sets the objective cards for a player.
     *
     * @param idPlayer   the ID of the player.
     * @param chosenCard the chosen card.
     */
    public void setObjectiveCards(int idPlayer, int chosenCard) {
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
     * @return player who is playing
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * @return chats of the game
     */
    public Chat getChats() {
        return chats;
    }


    /**
     * adds the player playerToAdd to the list of players of the game
     * it can be called until the game has not started
     *
     * @param playerToAdd the player to add
     * @return the index of the player in the list of players
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
     * @throws RequirementsNotMetException when the card's requirements are not met into the player's desk
     */
    public void playCard(int idCard, boolean faceDown, Point point)
            throws RequirementsNotMetException, PlaceNotAvailableException {
        GameCard card = players.get(currentPlayerIndex).getPlayerHand().get(idCard);
        players.get(currentPlayerIndex).playCard(card, faceDown, point);
    }

    /**
     * draws a card and puts it into the current player's hand
     * sets isLastRoundStarted as true if the current player's points are greater or equal to 20
     *
     * @param deck the deck from which the card is drawn
     */
    public void drawCard(Deck deck) {
        players.get(currentPlayerIndex).draw(deck);
    }

    /**
     * draws the card sent as a parameter and puts it into the current player's hand
     *
     * @param deck the deck from which the card is drawn
     * @param card the card to draw
     */
    public void drawVisibleCard(Deck deck, GameCard card) {
        players.get(currentPlayerIndex).drawVisible(deck, card);
    }

    /**
     * change the currentPlayerIndex as the next one
     */
    public void advanceToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % nPlayer;
    }

    /**
     * calculates if the players' objectives are met and adds the points of the met objectives to the players
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
                if(winner==null)
                    winner= currentPlayer.getUsername();
                else
                    winner += " and " + currentPlayer.getUsername();
            }
        }
        if (winner == null) return "No winner";
        return winner;
    }
}