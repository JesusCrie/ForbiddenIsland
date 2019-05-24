package iut2.forbiddenisland.model;

import java.util.*;

// TODO generalize
public class FloodDeck implements Deck {

	private final List<FloodCard> originalCards = new ArrayList<>();
	private final LinkedList<FloodCard> deck = new LinkedList<>();
	private final LinkedList<FloodCard> discardingDeck = new LinkedList<>();

	@Override
	public Card drawCard() {
		// TODO check if deck empty
		return deck.pop();
	}

	@Override
	public void discardCard(FloodCard f) {
		discardingDeck.addLast(f);
	}

	@Override
	public void reset() {

	}

	@Override
	public void shuffle() {
		Collections.shuffle(deck);
	}


}
