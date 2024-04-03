package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;

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
    private final String username;
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
     * @param drawnObjectiveCard
     * @param starter
     */
    public Player(TokenColor color, String username, Deck resourceDeck, Deck goldDeck,
                  ArrayList<ObjectiveCard> drawnObjectiveCard, StarterCard starter) {
        this.color=color;
        this.username=username;
        this.points=0;
        this.starterCard=starter;

        this.objectiveCards=new ArrayList<>(drawnObjectiveCard);

        this.playerHand=new ArrayList<>();
        for(int i=0;i<2;i++){
            this.draw(resourceDeck);
        }
        this.draw(goldDeck);
        this.playerDesk= new PlayerDesk();
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
        if(card instanceof GoldCard){
            GoldCard goldCard=(GoldCard) card;
            ArrayList<Resource> requirements= goldCard.getRequirements();
            int nPlant=0, nAnimal=0, nFungi=0, nInsect=0;
            for(Resource res:requirements){
                if(res.equals(Resource.PLANT_KINGDOM)){
                    nPlant++;
                }else if(res.equals(Resource.ANIMAL_KINGDOM)){
                    nAnimal++;
                }else if(res.equals(Resource.FUNGI_KINGDOM)){
                    nFungi++;
                }else if(res.equals(Resource.INSECT_KINGDOM)){
                    nInsect++;
                }
            }
            boolean requirementsMet=true;
            if(nPlant>0){
                requirementsMet=playerDesk.checkRequirements(nPlant, Resource.PLANT_KINGDOM);
                if(!requirementsMet) throw new RequirementsNotMetException("requirements not met");
            }
            if(nAnimal>0){
                requirementsMet=playerDesk.checkRequirements(nAnimal, Resource.ANIMAL_KINGDOM);
                if(!requirementsMet) throw new RequirementsNotMetException("requirements not met");
            }
            if(nFungi>0){
                requirementsMet=playerDesk.checkRequirements(nFungi, Resource.FUNGI_KINGDOM);
                if(!requirementsMet) throw new RequirementsNotMetException("requirements not met");
            }
            if(nInsect>0){
                requirementsMet=playerDesk.checkRequirements(nInsect, Resource.INSECT_KINGDOM);
                if(!requirementsMet) throw new RequirementsNotMetException("requirements not met");
            }
        }
        boolean checkRemove=this.playerHand.remove(card);
        if(!checkRemove) throw new CardNotFoundException("card not found");
        card.setPlayedFaceDown(faceDown);
        int pointsToAdd=playerDesk.addCard(card, x, y);
        this.setPoints(pointsToAdd);
    }

    /**
     * adds the pointsToAdd to points
     *
     * @param pointsToAdd
     */
    private void setPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    /**
     * checks if the shared and secret objective are met and determines the corresponding points to add
     * to the player's points
     *
     * @param sharedObjectiveCard are the ObjectiveCard shared by all the players
     * @return the number of objective that the player has met
     */
    public int checkObjective(ObjectiveCard[] sharedObjectiveCard) {
        int pointsToAdd=0;
        int nObjectiveMet=0;
        int objectiveMet;

        objectiveMet=objectiveCards.getFirst().verifyObjective(playerDesk);
        nObjectiveMet+=objectiveMet;
        pointsToAdd+=((objectiveCards.getFirst().getPoints())*objectiveMet);

        for(ObjectiveCard element : sharedObjectiveCard){
            objectiveMet=element.verifyObjective(playerDesk);
            nObjectiveMet+=objectiveMet;
            pointsToAdd+=((objectiveCards.getFirst().getPoints())*objectiveMet);
        }
        this.setPoints(pointsToAdd);
        return nObjectiveMet;
    }
}
