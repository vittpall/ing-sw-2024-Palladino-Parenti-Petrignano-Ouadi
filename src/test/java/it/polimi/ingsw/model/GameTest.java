package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    public void setUp() {
        game = new Game(4);
        game.setUpGame();
    }

    @Test
    void setUpGame_initializesGameCorrectly() {
        game.setUpGame();
        assertEquals(4, game.getnPlayer());
        assertEquals(2, game.getSharedObjectiveCards().length);
        assertEquals(2, game.getSharedObjectiveCards().length);
        assertNotNull(game.getResourceDeck());
        assertNotNull(game.getGoldDeck());
    }

    @Test
    void addPlayer_increasesPlayerCount() {
        Player player = new Player("Codex");
        int index = game.addPlayer(player);
        assertEquals(1, game.getPlayers().size());
        assertEquals(0, index);
    }
    @Test
    void setTokenColor_setsCorrectColor() {
        int id = game.addPlayer(new Player("Player1"));
        game.setTokenColor(id, TokenColor.RED);
        assertEquals(TokenColor.RED, game.getPlayers().get(id).getTokenColor());
        assertFalse(game.getAvailableColors().contains(TokenColor.RED));
    }
    @Test
    void getAvailableColors_returnsCorrectColors() {
        assertEquals(4, game.getAvailableColors().size());
        assertTrue(game.getAvailableColors().contains(TokenColor.RED));
        assertTrue(game.getAvailableColors().contains(TokenColor.GREEN));
        assertTrue(game.getAvailableColors().contains(TokenColor.BLUE));
        assertTrue(game.getAvailableColors().contains(TokenColor.YELLOW));

        int id = game.addPlayer(new Player("Player1"));
        game.setTokenColor(id, TokenColor.RED);
        assertEquals(3, game.getAvailableColors().size());
        assertFalse(game.getAvailableColors().contains(TokenColor.RED));
    }
    @Test
    void setObjectiveCards_setsCorrectCard() {
        int id = game.addPlayer(new Player("Player1"));
        game.setUpGame();
        ArrayList<ObjectiveCard> objCards= game.getPlayers().get(id).getDrawnObjectiveCards();
        assertEquals(2, objCards.size());
        ObjectiveCard card = objCards.getFirst();
        game.setObjectiveCards(id, 0);
        assertEquals(card, game.getPlayers().get(id).getObjectiveCard());
    }

    @Test
    public void testPlayCardWithRequirementsNotMet() {
        Game game = new Game(1);
        game.addPlayer(new Player("Player1"));
        game.setUpGame();
        Exception exception = assertThrows(RequirementsNotMetException.class, () -> {
            game.playCard(2, false, new Point(0, 0));
        });
        assertEquals("requirements not met for", exception.getMessage().substring(0, 24));
    }
    @Test
    public void testPlayCardWithPlaceNotAvailable() {
        Game game = new Game(1);
        game.addPlayer(new Player("Player1"));
        game.setUpGame();
        Exception exception = assertThrows(PlaceNotAvailableException.class, () -> {
            game.playCard(0, false, new Point(4, 0));
        });
        assertEquals("Placejava.awt.Point[x=4,y=0] not available", exception.getMessage());
    }
@Test
    public void drawCard() {
        Game game = new Game(1);
        game.addPlayer(new Player("Player1"));
        game.setUpGame();
        GameCard card = game.getGoldDeck().getUsableCards().getLast();
        game.drawCard(game.getGoldDeck());
        assertTrue(game.getPlayers().getFirst().getPlayerHand().contains(card));
    }

    @Test
    public void drawVisibleCard(){
        Game game = new Game(1);
        game.addPlayer(new Player("Player1"));
        game.setUpGame();
        GameCard card = game.getGoldDeck().getVisibleCards().get(0);
        game.drawVisibleCard(game.getGoldDeck(), card);
        assertTrue(game.getPlayers().getFirst().getPlayerHand().contains(card));
    }
    @Test
    public void endGame_testIfTheWinnerIsCorrect() {
        Game game = new Game(2);
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.setUpGame();
        player1.setObjectiveCard(0);
        player2.setObjectiveCard(1);
        player1.setPoints(21);
        assertEquals("Player1", game.endGame());
    }
}