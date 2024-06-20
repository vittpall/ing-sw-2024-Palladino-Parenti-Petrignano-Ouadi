package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class defines the player
 * points are the points that the player has
 * playerHand are the card that the player has but has not played yet
 * objectiveCard is the ObjectiveCard chosen from the player
 * drawnObjectiveCards are the ObjectiveCard assigned to the player
 * color is the TokenColor assigned to the player
 * starterCard is the StarterCard assigned to the player
 * playerDesk is a reference to the assigned PlayerDesk
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


    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    /*    /**
     * constructor
     * it sets points to 0 and assigned the player's token and username. it creates the objectiveCard, starterCard,
     * playerHand, playerDesk and drawnObjectiveCards and sets them as null
     *
     * @param color
     * @param username

   public Player(TokenColor color, String username) {
        this.color = color;
        this.username = username;
        this.points = 0;
        this.objectiveCard = null;
        this.starterCard = null;
        this.playerHand = new ArrayList<>();
        this.playerDesk = new PlayerDesk();
        this.drawnObjectiveCards = new ArrayList<>();
    }*/

    public void setTokenColor(TokenColor color) {
        this.color = color;
    }

    /**
     * constructor
     * it sets points to 0 and assigned the player's token and username. it creates the objectiveCard, starterCard,
     * playerHand, playerDesk and drawnObjectiveCards and sets them as null
     *
     * @param username is the player's username
     */
    public Player(String username) {
        this.username = username;
        this.points = 19;
        this.objectiveCard = null;
        this.starterCard = null;
        this.playerHand = new ArrayList<>();
        this.playerDesk = new PlayerDesk();
        this.drawnObjectiveCards = new ArrayList<>();
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
     * set starterCard as the parameter passed
     *
     * @param starter is the StarterCard that the player has chosen
     */
    public void setStarterCard(StarterCard starter) {
        this.starterCard = starter;
    }

    /**
     * set drawnObjectiveCard as the list passed as a parameter
     *
     * @param objectiveCards
     */
    public void setDrawnObjectiveCards(ArrayList<ObjectiveCard> objectiveCards) {
        this.drawnObjectiveCards = new ArrayList<>(objectiveCards);
    }

    /**
     * sets objectiveCard as the parameter and set drawnObjectiveCards as null
     *
     * @param chosenObjectiveCard is the index of the chosenObjectiveCard in the drawnObjectiveCards
     * @throws CardNotFoundException if the chosenObjectiveCard is not in the drawnObjectiveCards
     */
    public void setObjectiveCard(int chosenObjectiveCard) throws CardNotFoundException {
        ObjectiveCard chosenCard = drawnObjectiveCards.get(chosenObjectiveCard);
        if (!drawnObjectiveCards.contains(chosenCard))
            throw new CardNotFoundException("The ObjectiveCard is not in the drawnObjectiveCards");
        this.objectiveCard = new ObjectiveCard(chosenCard);
        this.drawnObjectiveCards = null;
        //le carte 2 carte obiettivo da cui sceglierla si gestiscono nel game per ogni player
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
     * draws a card from the chosenDeck and puts it into the playerHand
     *
     * @param chosenDeck is the deck from which the player draws the card
     */
    public void draw(Deck chosenDeck) {
        GameCard card;
        card = chosenDeck.drawDeckCard();
        playerHand.add(card);
    }

    /**
     * moves the chosenCard from the chosenDeck to the playerHand
     *
     * @param chosenDeck is the deck from which the player draws the card
     * @param chosenCard is the card that the player wants to draw
     */
    public void drawVisible(Deck chosenDeck, GameCard chosenCard) throws CardNotFoundException {
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
     * @throws CardNotFoundException       if the card sent is not into the playerHand
     * @throws RequirementsNotMetException when the player puts a gold card and the requirements are not met
     */
    public void playCard(GameCard card, boolean faceDown, Point point)
            throws CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        if (card instanceof GoldCard goldCard && !faceDown) {
            playerDesk.checkRequirements(goldCard.getRequirements());
        }
        if (!(card instanceof StarterCard)) {
            boolean cardRemoved = playerHand.remove(card);
            if (!cardRemoved) {
                throw new CardNotFoundException("Card not found in hand.");
            }
        }
        card.setPlayedFaceDown(faceDown);
        int pointsToAdd = playerDesk.addCard(card, point);
        this.setPoints(pointsToAdd);
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
