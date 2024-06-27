package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.controller.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.controller.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class defines the player
 * username is the player's username
 * points are the points that the player has
 * playerHand are the card that the player has but has not played yet
 * objectiveCard is the ObjectiveCard chosen from the player
 * drawnObjectiveCards are the ObjectiveCard assigned to the player
 * color is the TokenColor of the player
 * starterCard is the StarterCard assigned to the player
 * playerDesk is a reference to the assigned PlayerDesk
 * playerState is the state of the player in the game
 */
public class Player implements Serializable {
    private final String username;
    private int points;
    private final ArrayList<GameCard> playerHand;
    private ObjectiveCard objectiveCard;
    private ArrayList<ObjectiveCard> drawnObjectiveCards;
    private TokenColor color;
    private StarterCard starterCard;
    private final PlayerDesk playerDesk;
    private PlayerState playerState;

    /**
     * constructor
     * it sets points to 0 and assigned the player's username. it creates the
     * playerHand, playerDesk and drawnObjectiveCards
     *
     * @param username is the player's username
     */
    public Player(String username) {
        this.username = username;
        this.points = 0;
        this.objectiveCard = null;
        this.starterCard = null;
        this.playerHand = new ArrayList<>();
        this.playerDesk = new PlayerDesk();
        this.drawnObjectiveCards = new ArrayList<>();
    }

    /**
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return starterCard
     */
    public StarterCard getStarterCard() {
        return starterCard;
    }

    /**
     * @return objectiveCard
     */
    public ObjectiveCard getObjectiveCard() {
        return new ObjectiveCard(objectiveCard);
    }

    /**
     * @return drawnObjectiveCards
     */
    public ArrayList<ObjectiveCard> getDrawnObjectiveCards() {
        return new ArrayList<>(drawnObjectiveCards);
    }

    /**
     * @return playerHard
     */
    public ArrayList<GameCard> getPlayerHand() {
        return new ArrayList<>(playerHand);
    }

    /**
     * @return color
     */
    public TokenColor getTokenColor() {
        return color;
    }

    /**
     * @return playerDesk
     */
    public PlayerDesk getPlayerDesk() {
        return new PlayerDesk(playerDesk);
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the player's state in the game
     */
    public PlayerState getPlayerState() {
        return playerState;
    }

    /**
     * sets the playerState as the parameter passed
     *
     * @param playerState represents the state of the player in the game
     */
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * Sets color as the parameter passed
     *
     * @param color is the TokenColor that the player has chosen
     */
    public void setTokenColor(TokenColor color) {
        this.color = color;
    }

    /**
     * put into the playerHand two resourceCard and one goldCard randomly chosen from the decks
     *
     * @param resourceDeck the deck of resourceCard
     * @param goldDeck     the deck of goldCard
     */
    public void setPlayerHand(Deck resourceDeck, Deck goldDeck) {
        for (int i = 0; i < 2; i++) {
            this.draw(resourceDeck);
        }
        this.draw(goldDeck);
    }

    /**
     * Sets starterCard as the parameter passed
     *
     * @param starter is the StarterCard that the player has chosen
     */
    public void setStarterCard(StarterCard starter) {
        this.starterCard = starter;
    }

    /**
     * Sets drawnObjectiveCard as the list passed as a parameter
     *
     * @param objectiveCards is the list of ObjectiveCard drawn for the player
     */
    public void setDrawnObjectiveCards(ArrayList<ObjectiveCard> objectiveCards) {
        this.drawnObjectiveCards = new ArrayList<>(objectiveCards);
    }

    /**
     * Sets objectiveCard as the card in the position passed and set drawnObjectiveCards as null
     *
     * @param chosenObjectiveCard is the index of the chosenObjectiveCard in the drawnObjectiveCards
     */
    public void setObjectiveCard(int chosenObjectiveCard) {
        ObjectiveCard chosenCard = drawnObjectiveCards.get(chosenObjectiveCard);

        this.objectiveCard = new ObjectiveCard(chosenCard);
        this.drawnObjectiveCards = null;
    }


    /**
     * adds the pointsToAdd to points
     *
     * @param pointsToAdd is the number of points to add
     */
    private void setPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    /**
     * draws a card from the chosenDeck and puts it into the playerHand
     *
     * @param chosenDeck is the deck from which the player draws the card
     */
    public void draw(Deck chosenDeck) {
        GameCard card;
        card = chosenDeck.drawDeckCard();
        if(card!=null)
            playerHand.add(card);
    }

    /**
     * moves the chosenCard from the chosenDeck to the playerHand
     *
     * @param chosenDeck is the deck from which the player draws the card
     * @param chosenCard is the card that the player wants to draw
     */
    public void drawVisible(Deck chosenDeck, GameCard chosenCard) {
        GameCard card;
        card = chosenDeck.drawVisibleCard(chosenCard);
        playerHand.add(card);
    }

    /**
     * eliminates the card from the playerHand and puts it
     * into the desk at the chosen position if the requirements are met
     * calls the update of the player desk
     *
     * @param card     is the card that the user chose from the playerHands
     * @param faceDown defines if the card is played face down or not
     * @param point    is the position where the user wants to put the card
     * @throws RequirementsNotMetException when the player tries to put a gold card and the requirements are not met
     */
    public void playCard(GameCard card, boolean faceDown, Point point)
            throws RequirementsNotMetException, PlaceNotAvailableException {
        if (card instanceof GoldCard goldCard && !faceDown) {
            playerDesk.checkRequirements(goldCard.getRequirements());
        }
        if (!(card instanceof StarterCard))
            playerHand.remove(card);

        card.setPlayedFaceDown(faceDown);
        int pointsToAdd = playerDesk.addCard(card, point);
        this.setPoints(pointsToAdd);
    }

    /**
     * checks if the shared and secret objective are met and, if it happens, it adds the corresponding
     * point to the player's points
     *
     * @param sharedObjectiveCard are the ObjectiveCard shared by all the players
     * @return the number of objective that the player has met
     */
    public int checkObjective(ObjectiveCard[] sharedObjectiveCard) {
        int pointsToAdd = 0;
        int nObjectiveMet = 0;
        int objectiveMet;
        objectiveMet = objectiveCard.verifyObjective(playerDesk);
        nObjectiveMet += objectiveMet;
        pointsToAdd += ((objectiveCard.getPoints()) * objectiveMet);
        for (ObjectiveCard element : sharedObjectiveCard) {
            objectiveMet = element.verifyObjective(playerDesk);
            nObjectiveMet += objectiveMet;
            pointsToAdd += ((objectiveCard.getPoints()) * objectiveMet);
        }
        this.setPoints(pointsToAdd);
        return nObjectiveMet;
    }

}
