package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveStrategy;
import it.polimi.ingsw.model.strategyPatternObjective.ResourceStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Deck resourceDeck;
    private Deck goldDeck;
    private ArrayList<ObjectiveCard> objCards;

    @BeforeEach
    public void setUpTests() {
        player = new Player("Test");
        player.setPlayerState(PlayerState.SETUP_GAME);
        ArrayList<GameCard> resourceCards = new ArrayList<>();
        Corner[] corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }
        for (int i = 0; i < 3; i++) {
            resourceCards.add(new ResourceCard(Resource.ANIMAL_KINGDOM, null, null, 1, PointType.GENERAL, null, corners));
        }
        resourceDeck = new Deck(resourceCards);
        ArrayList<GameCard> goldCards = new ArrayList<>();
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        requirements.put(Resource.ANIMAL_KINGDOM, 1);
        goldCards.add(new GoldCard(Resource.ANIMAL_KINGDOM, null, null, 2, PointType.GENERAL, null, corners, requirements));
        goldDeck = new Deck(goldCards);
        objCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ObjectiveStrategy objectStrategy = new ResourceStrategy(Resource.ANIMAL_KINGDOM, 1);
            objCards.add(new ObjectiveCard(objectStrategy, 1, null, null));
        }
    }

    @Test
    void getDrawnObjectiveCards_beforeAndAfterSettingIt() {
        assertEquals(new ArrayList<>(), player.getDrawnObjectiveCards());
        ArrayList<ObjectiveCard> playerDrawnObjCards = player.getDrawnObjectiveCards();
        playerDrawnObjCards.addAll(objCards);
        assertNotEquals(playerDrawnObjCards, player.getDrawnObjectiveCards());
        player.setDrawnObjectiveCards(objCards);
        assertEquals(new HashSet<>(objCards), new HashSet<>(player.getDrawnObjectiveCards()));
    }

    @Test
    void getPoints_test() {
        int playerPoints = player.getPoints();
        assertEquals(0, playerPoints);
        playerPoints = 3;
        assertNotEquals(playerPoints, player.getPoints());
    }

    @Test
    void getUsername_returnsCorrectUsername() {
        assertEquals("Test", player.getUsername());
    }

    @Test
    void getPlayerState_returnsCorrectState() {
        assertEquals(PlayerState.SETUP_GAME, player.getPlayerState());
    }

    @Test
    void setPlayerState_setsCorrectState() {
        player.setPlayerState(PlayerState.DRAW);
        assertEquals(PlayerState.DRAW, player.getPlayerState());
    }


    @Test
    void getStarterCard_beforeSettingIt() {
        assertNull(player.getStarterCard());
    }

    @Test
    void getStarterCard_afterSettingIt() {
        Corner[] corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(true);
        }
        StarterCard starter = new StarterCard(Resource.ANIMAL_KINGDOM, null, null, 1, PointType.GENERAL, null, corners);
        player.setStarterCard(starter);
        assertEquals(starter, player.getStarterCard());
        starter = new StarterCard(Resource.FUNGI_KINGDOM, null, null, 1, PointType.GENERAL, null, corners);
        assertNotEquals(starter, player.getStarterCard());
    }

    @Test
    void getObjectiveCard() {
        assertThrows(NullPointerException.class, () -> player.getObjectiveCard());
        player.setDrawnObjectiveCards(objCards);
        ObjectiveCard objCard = objCards.getFirst();
        player.setObjectiveCard(0);
        assertEquals(objCard, player.getObjectiveCard());
        assertEquals(objCard.getStrategy(), player.getObjectiveCard().getStrategy());
        assertEquals(objCard.getPoints(), player.getObjectiveCard().getPoints());
        assertEquals(objCard.getImageFrontPath(), player.getObjectiveCard().getImageFrontPath());
        assertEquals(objCard.getImageBackPath(), player.getObjectiveCard().getImageBackPath());
    }

    @Test
    void getPlayerHandTest_beforeAndAfterSettingIt() {
        ArrayList<GameCard> expectedPlayerHand = new ArrayList<>();
        assertEquals(expectedPlayerHand, player.getPlayerHand());
        ArrayList<GameCard> playerHand = player.getPlayerHand();
        Corner[] corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(true);
        }
        playerHand.add(new ResourceCard(Resource.ANIMAL_KINGDOM, null, null, 1, PointType.GENERAL, null, corners));
        assertNotEquals(playerHand, player.getPlayerHand());

        expectedPlayerHand.add(resourceDeck.getUsableCards().getLast());
        expectedPlayerHand.add(resourceDeck.getUsableCards().getLast());
        expectedPlayerHand.addAll(goldDeck.getUsableCards());
        player.setPlayerHand(resourceDeck, goldDeck);
        assertEquals(expectedPlayerHand, player.getPlayerHand());
    }

    @Test
    void getTokenColor_test() {
        player.setTokenColor(TokenColor.BLUE);
        assertEquals(TokenColor.BLUE, player.getTokenColor());
    }

    @Test
    void getPlayerDesk_test() {
        PlayerDesk copyPlayerDesk = player.getPlayerDesk();
        assertNotEquals(copyPlayerDesk, player.getPlayerDesk());
        assertEquals(copyPlayerDesk.getDesk(), player.getPlayerDesk().getDesk());
        assertEquals(copyPlayerDesk.getAvailablePlaces(), player.getPlayerDesk().getAvailablePlaces());
        assertEquals(copyPlayerDesk.getForbiddenPlaces(), player.getPlayerDesk().getForbiddenPlaces());
        assertEquals(copyPlayerDesk.getTotalResources(), player.getPlayerDesk().getTotalResources());
        assertEquals(copyPlayerDesk.getTotalObjects(), player.getPlayerDesk().getTotalObjects());

    }

    @Test
    void playCard_throwsPlaceNotAvailableExceptionWhenPlaceIsNotAvailable() {
        Corner[] corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(true);
        }
        GameCard card = new ResourceCard(Resource.ANIMAL_KINGDOM, null, null, 1, PointType.GENERAL, null, corners);
        assertThrows(PlaceNotAvailableException.class, () -> player.playCard(card, false, new Point(0, -3)));
    }

    @Test
    void playCard_throwsRequirementsNotMetExceptionWhenRequirementsAreNotMet() {
        Corner[] corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(true);
        }
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        requirements.put(Resource.FUNGI_KINGDOM, 1);
        GameCard card = new GoldCard(Resource.ANIMAL_KINGDOM, null, null, 1, PointType.GENERAL, null, corners, requirements);
        assertThrows(RequirementsNotMetException.class, () -> player.playCard(card, false, new Point(0, 0)));
    }

    @Test
    void playCard_returnsTrueWhenCardIsPlayed() throws PlaceNotAvailableException, RequirementsNotMetException {
        Corner[] corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(true);
        }
        GameCard card = new ResourceCard(Resource.ANIMAL_KINGDOM, null, null, 1, PointType.GENERAL, null, corners);
        player.playCard(card, false, new Point(0, 0));
        assertEquals(1, player.getPoints());
    }

    @Test
    void draw_test() {
        ArrayList<GameCard> playerHand = player.getPlayerHand();
        assertEquals(new HashSet<>(playerHand), new HashSet<>(player.getPlayerHand()));
        GameCard drawnCard = goldDeck.getUsableCards().getFirst();
        player.draw(goldDeck);
        assertNotEquals(new HashSet<>(playerHand), new HashSet<>(player.getPlayerHand()));
        assertTrue(player.getPlayerHand().containsAll(playerHand));
        assertFalse(playerHand.containsAll(player.getPlayerHand()));
        assertTrue(player.getPlayerHand().contains(drawnCard));
    }

    @Test
    void drawVisible() {
        ArrayList<GameCard> playerHand = player.getPlayerHand();
        assertEquals(new HashSet<>(playerHand), new HashSet<>(player.getPlayerHand()));
        resourceDeck.makeTopCardsVisible();
        GameCard drawnCard = resourceDeck.getVisibleCards().getFirst();
        player.drawVisible(resourceDeck, drawnCard);
        assertNotEquals(new HashSet<>(playerHand), new HashSet<>(player.getPlayerHand()));
        assertTrue(player.getPlayerHand().containsAll(playerHand));
        assertFalse(playerHand.containsAll(player.getPlayerHand()));
        assertTrue(player.getPlayerHand().contains(drawnCard));
    }


    @Test
    void checkObjective_returnsCorrectPoints() {
        ObjectiveCard[] sharedObjectiveCard = new ObjectiveCard[2];
        sharedObjectiveCard[0] = objCards.getFirst();
        sharedObjectiveCard[1] = objCards.getLast();
        player.setDrawnObjectiveCards(objCards);
        player.setObjectiveCard(0);
        assertEquals(0, player.checkObjective(sharedObjectiveCard));
    }
}