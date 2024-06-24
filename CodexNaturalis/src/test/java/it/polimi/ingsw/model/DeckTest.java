package it.polimi.ingsw.model;

import it.polimi.ingsw.util.GameCardLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DeckTest {

    private Deck deck;

    @BeforeEach
    public void setUp() {
        List<GameCard> initialUsableCards = new ArrayList<>();
        GameCardLoader gameCardLoader = new GameCardLoader();

        for (int i = 0; i < 10; i++) {
            initialUsableCards.add(gameCardLoader.loadGameCards().get(i));
        }

        deck = new Deck(initialUsableCards);
    }


    @Test
    void makeTopCardsVisible_makesTwoCardsVisible() {
        deck.makeTopCardsVisible();
        assertEquals(2, deck.getVisibleCards().size());
        assertEquals(8, deck.getUsableCards().size());
    }

    @Test
    void drawVisibleCard_removesCardFromVisible() {
        deck.makeTopCardsVisible();
        GameCard card1 = deck.getVisibleCards().getFirst();
        GameCard drawnCard = deck.drawVisibleCard(card1);
        assertEquals(card1, drawnCard);
        assertEquals(2, deck.getVisibleCards().size());
        assertFalse(deck.getVisibleCards().contains(card1));
    }

    @Test
    void drawDeckCard_removesCardFromDeck() {
        GameCard drawnCard = deck.drawDeckCard();
        assertFalse(deck.getUsableCards().contains(drawnCard));
    }

}