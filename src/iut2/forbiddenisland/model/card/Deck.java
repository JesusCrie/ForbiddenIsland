package iut2.forbiddenisland.model.card;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Represent any deck of cards.
 *
 * @param <T> - The type of cards to hold.
 */
public abstract class Deck<T extends Card> {

    protected final LinkedList<T> deck = new LinkedList<>();
    protected final LinkedList<T> discardingDeck = new LinkedList<>();

    public Deck(final Collection<T> initialCards) {
        deck.addAll(initialCards);
        shuffle();
    }

    /**
     * Pop a card from this deck and returns it.
     *
     * @return The next card.
     */
    public T drawCard() {
        if (deck.isEmpty())
            reset();

        return deck.pop();
    }

    /**
     * Discard a card into the discarding deck.
     *
     * @param card - The card to discard.
     */
    public void discardCard(final T card) {
        discardingDeck.push(card);
    }

    /**
     * Reset the deck by placing the discarding deck on top
     * of it and shuffling.
     */
    public void reset() {
        deck.addAll(discardingDeck);
        discardingDeck.clear();

        shuffle();
    }

    /**
     * Shuffle the deck.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }


}
