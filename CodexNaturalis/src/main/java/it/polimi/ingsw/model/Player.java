package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.TokenColor;

import java.util.ArrayList;

/**
 * this class defines the player
 * points are the points that the player has
 * playerHand are the card that the player has but has not played yet
 * objectiveCards are the ObjectiveCard assigned to the player
 * color is the TokenColor assigned to the player
 * starterCard is the StarterCard assigned to the player
 * playerDesk is a reference to the assigned PlayerDesk
 */
public class Player {
    private String username;
    private int points;
    private ArrayList<GameCard> playerHand;
    private ArrayList<ObjectiveCard> objectiveCards;
    private final TokenColor color;
    private final StarterCard starterCard;
    private final PlayerDesk playerDesk;

    /**
     * constructor
     * it creates a list of 3 GameCard chosen randomly from the decks, a list of 2 ObjectiveCard
     * created randomly and the playerDesk; it sets points to 0 and creates randomly the starterCard
     * @param color
     * @param username
     * @param resourceDeck
     * @param goldDeck
     */
    public Player(TokenColor color, String username, Deck resourceDeck, Deck goldDeck) {
        ObjectiveCard objective;
        GameCard card;
        this.color=color;
        this.username=username;
        this.points=0;
        this.objectiveCards=new ArrayList<ObjectiveCard>();
        this.playerHand=new ArrayList<GameCard>();
        for(int i=0;i<2;i++){
            this.draw(resourceDeck);
        }
        this.draw(goldDeck);
        this.playerDesk= new PlayerDesk();
        //aggiungere objectiveCards e starterCard
    }


    /**
     * eliminates the objectiveCard that the user had discarded
     *
     * @param chosenObjectiveCard
     */
    public void setObjectiveCard(ObjectiveCard chosenObjectiveCard) {
        for(ObjectiveCard element: objectiveCards){
            if(!(element.equals(chosenObjectiveCard)))
                objectiveCards.remove(element);
        }
    }

    /**
     *
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     *
     * @return starterCard
     */
    public StarterCard getStarterCard() {
        return starterCard;
    }

    /**
     * @return objectiveCards
     */
    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return new ArrayList<ObjectiveCard>(objectiveCards);
    }

    /**
     * @return playerHard
     */
    public ArrayList<GameCard> getPlayerHand() {
        return new ArrayList<GameCard>(playerHand);

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
        return playerDesk;
    }

    /**
     * draws a card from the chosenDeck and puts it into the playerHand
     * @param chosenDeck
     */
    public void draw(Deck chosenDeck) {
        GameCard card;
        card=chosenDeck.drawDeckCard();
        playerHand.add(card);
    }

    /**
     * moves the chosenCard from the chosenDeck to the playerHand
     * @param chosenDeck
     * @param chosenCard
     */
    public void drawVisible(Deck chosenDeck, GameCard chosenCard) throws CardNotFoundException {
        GameCard card;
        card=chosenDeck.drawVisibleCard(chosenCard);
        playerHand.add(card);
    }


    /**
     * eliminates the card from the playerHand and puts it
     * into the desk at the chosen position if the requirements are met
     *
     * @param card is the card that the user chose from the playerHands
     * @param faceDown
     * @param x    is the x-coordinate of the chosen position
     * @param y    is the y-coordinate of the chosen position
     * @throws CardNotFoundException
     * @throws RequirementsNotMetException
     */
    public void playCard(GameCard card, boolean faceDown, int x, int y)
            throws CardNotFoundException, RequirementsNotMetException {
    }

    /**
     * adds the pointsToAdd to points
     *
     * @param pointsToAdd
     */
    public void setPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    /**
     * checks if the shared and secret objective are met and determines the corresponding points to add
     * to the player's points
     *
     * @param sharedObjectiveCard
     * @return the player's total points
     */
    public int checkObjective(ArrayList<ObjectiveCard> sharedObjectiveCard) {
        int pointsToAdd=0;
        boolean objectiveMet;

        objectiveMet=objectiveCards.get(0).verifyObjective(playerDesk);
        if(objectiveMet==true)
            pointsToAdd+=objectiveCards.get(0).getPoints();
        for(ObjectiveCard element : sharedObjectiveCard){
            objectiveMet=element.verifyObjective(playerDesk);
            if(objectiveMet==true)
                pointsToAdd+=element.getPoints();
        }
        this.setPoints(pointsToAdd);
        return this.points;
    }
}
