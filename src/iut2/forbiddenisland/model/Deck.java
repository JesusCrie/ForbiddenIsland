package iut2.forbiddenisland.model;

public interface Deck {

    /**
     * Pop a card from this deck and returns it.
     *
     * @return The next card.
     */
    Card drawCard();

    /**
     * place the floodCard in the discardingDeck
     *
     */
    void discardCard(FloodCard f);

    /**
     * Reset the deck in its initial state.
     */
    void reset();

    /**
     * Shuffle the deck.
     */
    void shuffle();



}
