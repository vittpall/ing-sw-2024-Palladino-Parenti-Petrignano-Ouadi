package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerStateTest {

    @Test
    void get_returnsCorrectTextForSetupGame() {
        assertEquals("Setup game", PlayerState.SETUP_GAME.get());
    }

    @Test
    void get_returnsCorrectTextForWaiting() {
        assertEquals("Waiting", PlayerState.WAITING.get());
    }

    @Test
    void get_returnsCorrectTextForPlayCard() {
        assertEquals("Playing card", PlayerState.PLAY_CARD.get());
    }

    @Test
    void get_returnsCorrectTextForDraw() {
        assertEquals("Draw", PlayerState.DRAW.get());
    }

    @Test
    void get_returnsCorrectTextForEndGame() {
        assertEquals("End game", PlayerState.ENDGAME.get());
    }

    @Test
    void toString_returnsCorrectTextForSetupGame() {
        assertEquals("Setup game", PlayerState.SETUP_GAME.toString());
    }

    @Test
    void toString_returnsCorrectTextForWaiting() {
        assertEquals("Waiting", PlayerState.WAITING.toString());
    }

    @Test
    void toString_returnsCorrectTextForPlayCard() {
        assertEquals("Playing card", PlayerState.PLAY_CARD.toString());
    }

    @Test
    void toString_returnsCorrectTextForDraw() {
        assertEquals("Draw", PlayerState.DRAW.toString());
    }

    @Test
    void toString_returnsCorrectTextForEndGame() {
        assertEquals("End game", PlayerState.ENDGAME.toString());
    }
}