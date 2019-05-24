package iut2.forbiddenisland.model.card;

import java.util.*;

public class TreasureDeck implements Deck {

	private final List<TreasureCard> originalCards = new ArrayList<>();
	private final LinkedList<TreasureCard> deck = new LinkedList<>();
	private final LinkedList<TreasureCard> discardingDeck = new LinkedList<>();

	@Override
	public Card drawCard() {
		return deck.pop();
	}

	@Override
	public void discardCard(Card f) {
		discardingDeck.addLast((TreasureCard) f);
	}



	@Override
	public void reset() {

	}

	@Override
	public void shuffle() {
		Collections.shuffle(deck);
	}
}
