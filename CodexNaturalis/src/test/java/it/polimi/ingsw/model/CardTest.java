package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    @Test
    void getPoints_returnsCorrectPoints() {
        Card card = new Card() {
            {
                this.points = 5;
            }
        };
        assertEquals(5, card.getPoints());
    }

    @Test
    void getPoints_returnsZeroForNoPoints() {
        Card card = new Card() {
            {
                this.points = 0;
            }
        };
        assertEquals(0, card.getPoints());
    }

    @Test
    void getPoints_returnsNegativeForNegativePoints() {
        Card card = new Card() {
            {
                this.points = -3;
            }
        };
        assertEquals(-3, card.getPoints());
    }
}