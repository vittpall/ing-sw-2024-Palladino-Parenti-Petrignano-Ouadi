package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
//TODO fix this one
/*
    @Test
    public void testPlayCardWithRequirementsNotMet() {
        Game game = new Game(1);
        game.setUpGame();
        Exception exception = assertThrows(RequirementsNotMetException.class, () -> {
            game.playCard(0, false, new Point(0, 0));
        });
        String expectedMessage = "Requirements not met";
    }*/

}