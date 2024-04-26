package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getPoints_returnsCorrectPoints_whenPointsArePositive() {
        Card card = new Card(7, "/path/to/front/image", "/path/to/back/image") {
        };
        assertEquals(7, card.getPoints());
    }

    @Test
    void getPoints_returnsCorrectPoints_whenPointsAreZero() {
        Card card = new Card(0, "/path/to/front/image", "/path/to/back/image") {

        };
        assertEquals(0, card.getPoints());
    }

    @Test
    void getPoints_throwsIllegalArgumentException_whenPointsAreNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Card(-5, "/path/to/front/image", "/path/to/back/image") {
        });
        assertEquals("Points cannot be negative", exception.getMessage());
    }

    @Test
    void getImageFrontPath_returnsCorrectPath_whenPathIsSet() {
        Card card = new Card(0, "/path/to/front/image", "/path/to/back/image") {

        };
        assertEquals("/path/to/front/image", card.getImageFrontPath());
    }

    @Test
    void getImageFrontPath_returnsNull_whenPathIsNotSet() {
        Card card = new Card(0, null, "/path/to/back/image") {
        };
        assertNull(card.getImageFrontPath());
    }

    @Test
    void getImageBackPath_returnsCorrectPath_whenPathIsSet() {
        Card card = new Card(0, "/path/to/front/image", "/path/to/back/image") {

        };
        assertEquals("/path/to/back/image", card.getImageBackPath());
    }

    @Test
    void getImageBackPath_returnsNull_whenPathIsNotSet() {
        Card card = new Card(0, "/path/to/front/image", null) {
        };
        assertNull(card.getImageBackPath());
    }
}