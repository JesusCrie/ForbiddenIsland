package iut2.forbiddenisland.model.card;

public interface Deck {

    /**
     * Pop a card from this deck and returns it.
     *
     * @return The next card.
     */
    Card drawCard();

    /**
     * Reset the deck in its initial state.
     */
    void reset();

    /**
     * Shuffle the deck.
     */
    void shuffle();

}
