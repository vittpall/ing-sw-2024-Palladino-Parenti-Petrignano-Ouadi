package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestedActionsTest {

    @Test
    void get_returnsCorrectTextForDraw() {
        assertEquals("Draw", RequestedActions.DRAW.get());
    }

    @Test
    void get_returnsCorrectTextForPlayCard() {
        assertEquals("Game rounds", RequestedActions.PLAY_CARD.get());
    }

    @Test
    void get_returnsCorrectTextForShowDesks() {
        assertEquals("Last round", RequestedActions.SHOW_DESKS.get());
    }

    @Test
    void get_returnsCorrectTextForShowObjCards() {
        assertEquals("End game", RequestedActions.SHOW_OBJ_CARDS.get());
    }

    @Test
    void get_returnsCorrectTextForShowPoints() {
        assertEquals("Show points", RequestedActions.SHOW_POINTS.get());
    }

    @Test
    void get_returnsCorrectTextForChat() {
        assertEquals("Chat", RequestedActions.CHAT.get());
    }

    @Test
    void get_returnsCorrectTextForShowWinner() {
        assertEquals("Show winner", RequestedActions.SHOW_WINNER.get());
    }

    @Test
    void toString_returnsCorrectTextForDraw() {
        assertEquals("Draw", RequestedActions.DRAW.toString());
    }

    @Test
    void toString_returnsCorrectTextForPlayCard() {
        assertEquals("Game rounds", RequestedActions.PLAY_CARD.toString());
    }

    @Test
    void toString_returnsCorrectTextForShowDesks() {
        assertEquals("Last round", RequestedActions.SHOW_DESKS.toString());
    }

    @Test
    void toString_returnsCorrectTextForShowObjCards() {
        assertEquals("End game", RequestedActions.SHOW_OBJ_CARDS.toString());
    }

    @Test
    void toString_returnsCorrectTextForShowPoints() {
        assertEquals("Show points", RequestedActions.SHOW_POINTS.toString());
    }

    @Test
    void toString_returnsCorrectTextForChat() {
        assertEquals("Chat", RequestedActions.CHAT.toString());
    }

    @Test
    void toString_returnsCorrectTextForShowWinner() {
        assertEquals("Show winner", RequestedActions.SHOW_WINNER.toString());
    }
}