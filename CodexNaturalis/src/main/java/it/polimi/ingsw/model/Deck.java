package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class defines the deck, which could be made of gold or resource card. The UsableCard represent the Deck itself while the VisibleCard are the two cards set next to the deck.
 */
public class Deck {
    /**
     * Final prevent from changing the reference to UsableCard or VisibleCard
     */
    private final ArrayList<GameCard> UsableCard;
    private final ArrayList<GameCard> VisibleCard;

    /**
     * Default constructor, it creates the deck randomly and takes two card from it and set them as visible ones.
     */
    public Deck(ArrayList<GameCard> GetUsable)
    {
        UsableCard = new ArrayList<>();
        VisibleCard = new ArrayList<>();

        UsableCard.addAll(GetUsable);
        Shuffle(UsableCard);

        VisibleCard.add(UsableCard.getLast());
        UsableCard.removeLast();
        VisibleCard.add(UsableCard.getLast());
        UsableCard.removeLast();
    }

    /**
     * Shuffle Randomly the Deck created in the constructor
     */
    private void Shuffle(ArrayList<GameCard> DeckToShuffle)
    {
        GameCard TempCard;
        int RandomPosition;

        for(int i = 0; i < DeckToShuffle.size(); i++)
        {
            TempCard = DeckToShuffle.get(i);
            RandomPosition = (int)Math.floor(Math.random()*40);
            DeckToShuffle.add(i, DeckToShuffle.get(RandomPosition));
            DeckToShuffle.add(RandomPosition, TempCard);
        }
    }

    /**
     * Returns one of the visible cards to the user and sets another one taking it from the UsableCard
     * @param card
     * @return the VisibleCard chosen
     */
    public GameCard drawVisibleCard(GameCard card) throws CardNotFoundException
    {
        int CardPosition = VisibleCard.indexOf(card);
        GameCard ChosenCard;
        GameCard NewVisibleCard;
        //to define the Exception thrown
        if(CardPosition == -1)
            throw new CardNotFoundException("Card not found");

        ChosenCard = VisibleCard.get(CardPosition);
        VisibleCard.remove(card);

        //get a random card from the deck
        NewVisibleCard = UsableCard.getLast();
        UsableCard.remove(NewVisibleCard);
        VisibleCard.add(NewVisibleCard);

        return ChosenCard;
    }

    /**
     *  Returns to the user a card taken from the UsableCard
     * @param card
     * @return card
     */
    public GameCard drawDeckCard(GameCard card)
    {
        GameCard LastCard;
        LastCard = UsableCard.getLast();
        UsableCard.remove(LastCard);
        return LastCard;
    }

}
