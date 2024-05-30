package it.polimi.ingsw.model;

import it.polimi.ingsw.tui.PrintContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getPoints_returnsCorrectPoints_whenPointsArePositive() {
        Card card = new Card(7, "/path/to/front/image", "/path/to/back/image") {
            @Override
            public void print(PrintContext context, boolean faceDown) {

            }
        };
        assertEquals(7, card.getPoints());
    }

    @Test
    void getPoints_returnsCorrectPoints_whenPointsAreZero() {
        Card card = new Card(0, "/path/to/front/image", "/path/to/back/image") {

            @Override
            public void print(PrintContext context, boolean faceDown) {

            }
        };
        assertEquals(0, card.getPoints());
    }

    @Test
    void getPoints_throwsIllegalArgumentException_whenPointsAreNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Card(-5, "/path/to/front/image", "/path/to/back/image") {
            @Override
            public void print(PrintContext context, boolean faceDown) {

            }
        });
        assertEquals("Points cannot be negative", exception.getMessage());
    }

    @Test
    void getImageFrontPath_returnsCorrectPath_whenPathIsSet() {
        Card card = new Card(0, "/path/to/front/image", "/path/to/back/image") {

            @Override
            public void print(PrintContext context, boolean faceDown) {

            }
        };
        assertEquals("/path/to/front/image", card.getImageFrontPath());
    }

    @Test
    void getImageFrontPath_returnsNull_whenPathIsNotSet() {
        Card card = new Card(0, null, "/path/to/back/image") {
            @Override
            public void print(PrintContext context, boolean faceDown) {

            }
        };
        assertNull(card.getImageFrontPath());
    }

    @Test
    void getImageBackPath_returnsCorrectPath_whenPathIsSet() {
        Card card = new Card(0, "/path/to/front/image", "/path/to/back/image") {

            @Override
            public void print(PrintContext context, boolean faceDown) {

            }
        };
        assertEquals("/path/to/back/image", card.getImageBackPath());
    }

    @Test
    void getImageBackPath_returnsNull_whenPathIsNotSet() {
        Card card = new Card(0, "/path/to/front/image", null) {
            @Override
            public void print(PrintContext context, boolean faceDown) {

            }
        };
        assertNull(card.getImageBackPath());
    }
}