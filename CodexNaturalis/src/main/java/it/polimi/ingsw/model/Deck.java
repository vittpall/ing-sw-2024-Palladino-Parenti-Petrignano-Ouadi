package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class defines the deck, which could be made of gold or resource card. The UsableCard represent the Deck itself while the VisibleCard are the two cards set next to the deck.
 */
public class Deck {
    /**
     * Final prevent from changing the reference to UsableCard or VisibleCard
     */
    private final ArrayList<GameCard> UsableCards;
    private final ArrayList<GameCard> VisibleCards;

    /**
     * Default constructor, it creates the deck randomly and takes two card from it and set them as visible ones.
     */
    public Deck(ArrayList<GameCard> GetUsable) {
        UsableCards = new ArrayList<>();
        VisibleCards = new ArrayList<>();

        UsableCards.addAll(GetUsable);
        Shuffle(UsableCards);
    }

    public void makeTopCardsVisible() {
        VisibleCards.add(UsableCards.getLast());
        UsableCards.removeLast();
        VisibleCards.add(UsableCards.getLast());
        UsableCards.removeLast();
    }

    /**
     * Shuffle Randomly the Deck created in the constructor
     */
    private void Shuffle(ArrayList<GameCard> DeckToShuffle) {
        Collections.shuffle(DeckToShuffle);
    }

    /**
     * Returns one of the visible cards to the user and sets another one taking it from the UsableCard
     *
     * @param card
     * @return the VisibleCard chosen
     */
    public GameCard drawVisibleCard(GameCard card) throws CardNotFoundException {
        if (VisibleCards.indexOf(card) == -1)
            throw new CardNotFoundException("Card not found");

        VisibleCards.remove(card);
        VisibleCards.add(UsableCards.removeLast());
        return card;
    }

    /**
     * Returns to the user a card taken from the UsableCard
     *
     * @return card
     */
    public GameCard drawDeckCard() {
        GameCard LastCard;
        LastCard = UsableCards.getLast();
        UsableCards.remove(LastCard);
        return LastCard;
    }

    public ArrayList<GameCard> getUsableCards() {
        return UsableCards;
    }

    public ArrayList<GameCard> getVisibleCards() {
        return VisibleCards;
    }
}
