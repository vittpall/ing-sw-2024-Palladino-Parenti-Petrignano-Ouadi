package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class defines the deck, which could be made of gold or resource card. The UsableCard represent the Deck itself while the VisibleCard are the two cards set next to the deck.
 */
public class Deck {

    private final ArrayList<GameCard> usableCards;
    private final ArrayList<GameCard> visibleCards;

    /**
     * Default constructor, it creates the deck randomly and takes two card from it and set them as visible ones.
     *
     * @param getUsable the list of usable cards
     */
    public Deck(List<GameCard> getUsable) {
        usableCards = new ArrayList<>();
        visibleCards = new ArrayList<>();

        usableCards.addAll(getUsable);
        shuffle(usableCards);
    }

    /**
     * Makes the top two cards of the deck visible.
     */
    public void makeTopCardsVisible() {
        visibleCards.add(usableCards.getLast());
        usableCards.removeLast();
        visibleCards.add(usableCards.getLast());
        usableCards.removeLast();
    }

    /**
     * Shuffle Randomly the Deck created in the constructor
     *
     * @param deckToShuffle the deck to shuffle
     */
    private void shuffle(ArrayList<GameCard> deckToShuffle) {
        Collections.shuffle(deckToShuffle);
    }

    /**
     * Returns one of the visible cards to the user and sets another one taking it from the UsableCard
     *
     * @param card the card to be returned
     * @return the VisibleCard chosen
     */
    public GameCard drawVisibleCard(GameCard card) {
        visibleCards.remove(card);
        visibleCards.add(usableCards.removeLast());
        return card;
    }

    /**
     * Returns to the user a card taken from the UsableCard
     *
     * @return the drawn card
     */
    public GameCard drawDeckCard() {
        GameCard lastCard;
        lastCard = usableCards.getLast();
        usableCards.remove(lastCard);
        return lastCard;
    }

    /**
     * @return the list of usable cards
     */
    public List<GameCard> getUsableCards() {
        return usableCards;
    }

    /**
     * @return the list of visible cards
     */
    public ArrayList<GameCard> getVisibleCards() {
        return visibleCards;
    }
}
