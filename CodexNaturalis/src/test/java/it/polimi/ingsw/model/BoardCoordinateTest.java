package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BoardCoordinateTest {

    @Test
    public void testConstructorAndFieldAccess() {
        BoardCoordinate coordinate = new BoardCoordinate("1", "A", 10);
        assertEquals("1", coordinate.x());
        assertEquals("A", coordinate.y());
        assertEquals(10, coordinate.score());
    }

    @Test
    public void testEqualsTrue() {
        BoardCoordinate coordinate1 = new BoardCoordinate("1", "A", 10);
        BoardCoordinate coordinate2 = new BoardCoordinate("1", "A", 10);
        assertEquals(coordinate1, coordinate2);
    }

    @Test
    public void testEqualsFalseDifferentX() {
        BoardCoordinate coordinate1 = new BoardCoordinate("1", "A", 10);
        BoardCoordinate coordinate2 = new BoardCoordinate("2", "A", 10);
        assertNotEquals(coordinate1, coordinate2);
    }

    @Test
    public void testEqualsFalseDifferentY() {
        BoardCoordinate coordinate1 = new BoardCoordinate("1", "A", 10);
        BoardCoordinate coordinate2 = new BoardCoordinate("1", "B", 10);
        assertNotEquals(coordinate1, coordinate2);
    }

    @Test
    public void testEqualsFalseDifferentScore() {
        BoardCoordinate coordinate1 = new BoardCoordinate("1", "A", 10);
        BoardCoordinate coordinate2 = new BoardCoordinate("1", "A", 20);
        assertNotEquals(coordinate1, coordinate2);
    }

    @Test
    public void testEqualsNull() {
        BoardCoordinate coordinate = new BoardCoordinate("1", "A", 10);
        assertNotEquals(coordinate, null);
    }
}