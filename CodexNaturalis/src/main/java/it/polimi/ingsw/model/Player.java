package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * this class defined the player
 * points are the points that the player has
 * playerHand are the card that the player has but has not played yet
 * objectiveCards are the ObjectiveCard assigned to the player
 * color is the TokenColor assigned to the player
 * starterCard is the StarterCard assigned to the player
 * playerDesk is a reference to the assigned PlayerDesk
 */
public class Player {
    private int points;
    private ArrayList<GameCard> playerHand;
    private ArrayList<ObjectiveCard> objectiveCards;
    private final TokenColor color;
    private final StarterCard starterCard;
    private final PlayerDesk playerDesk;

    /**
     * costructor
     * it creates a list of 3 GameCard choosen randomly from the decks, a list of 2 ObjectiveCard
     * created randomly and the palyerDesk; it sets points to 0 and creates randomly the starterCard
     *
     * @param color rapresents the color choosen for the player
     */
    public Player(TokenColor color) {
    }


    /**
     * eliminates the objectiveCard that the user had discarded
     *
     * @param choosenObjectiveCard
     */
    public void setObjectiveCard(int choosenObjectiveCard) {
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
    }

    /**
     *
     * @return objectiveCards
     */
    public ArrayList<objectiveCard> getObjectiveCards() {
    }

    /**
     *
     * @return playerHard
     */
    public ArrayList<PlayerHand> getPlayerHand() {
    }

    /**
     *
     * @return color
     */
    public TokenColor getTokenColor() {
    }

    /**
     *
     * @return playerDesk
     */
    public Desk getPlayerDesk() {
    }

    /**
     * draws a card from the choosenDeck and puts it into the playerHand
     * @param choosenDeck
     */
    public void draw(Deck choosenDeck) {
    }

    /**
     * moves the choosenCard from the choosenDeck to the playerHand
     * @param choosenDeck
     * @param choosenCard
     */
    public void drawVisible(Deck choosenDeck, GameCard choosenCard) {
    }


    /**
     * eliminates the card from the playerHand and puts it
     * into the desk at the choosen position if the requirements are met
     *
     * @param card is the card that the user chose from the playerHands
     * @param faceDown
     * @param x    is the x-coordinate of the choosen position
     * @param y    is the y-coordinate of the choosen position
     * @throws CardNotFoundException
     * @throws RequirementsNotMetException
     */
    public void playCard(GameCard card, boolan faceDown int x, int y)
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
     * checks if the shared and secret objective are met and determines the corrisponding points to add
     * to the player's points
     *
     * @param sharedObjectiveCard
     * @return
     */
    public int checkObjective(ArrayList<ObjectiveCard> sharedObjectiveCard) {
    }
}
