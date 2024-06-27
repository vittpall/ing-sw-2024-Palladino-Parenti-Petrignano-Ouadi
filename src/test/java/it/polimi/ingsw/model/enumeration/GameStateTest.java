package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {

    @Test
    void get_returnsCorrectTextForWaitingForPlayers() {
        assertEquals("Waiting for players", GameState.WAITING_FOR_PLAYERS.get());
    }

    @Test
    void get_returnsCorrectTextForSetupGame() {
        assertEquals("Setup game", GameState.SETUP_GAME.get());
    }

    @Test
    void get_returnsCorrectTextForRounds() {
        assertEquals("Game rounds", GameState.ROUNDS.get());
    }

    @Test
    void get_returnsCorrectTextForFinishingRoundBeforeLast() {
        assertEquals("Finishing round before last", GameState.FINISHING_ROUND_BEFORE_LAST.get());
    }

    @Test
    void get_returnsCorrectTextForLastRound() {
        assertEquals("Last round", GameState.LAST_ROUND.get());
    }

    @Test
    void get_returnsCorrectTextForEndGame() {
        assertEquals("End game", GameState.ENDGAME.get());
    }

    @Test
    void toString_returnsCorrectTextForWaitingForPlayers() {
        assertEquals("Waiting for players", GameState.WAITING_FOR_PLAYERS.toString());
    }

    @Test
    void toString_returnsCorrectTextForSetupGame() {
        assertEquals("Setup game", GameState.SETUP_GAME.toString());
    }

    @Test
    void toString_returnsCorrectTextForRounds() {
        assertEquals("Game rounds", GameState.ROUNDS.toString());
    }

    @Test
    void toString_returnsCorrectTextForFinishingRoundBeforeLast() {
        assertEquals("Finishing round before last", GameState.FINISHING_ROUND_BEFORE_LAST.toString());
    }

    @Test
    void toString_returnsCorrectTextForLastRound() {
        assertEquals("Last round", GameState.LAST_ROUND.toString());
    }

    @Test
    void toString_returnsCorrectTextForEndGame() {
        assertEquals("End game", GameState.ENDGAME.toString());
    }
}