package iut2.forbiddenisland.model;

import iut2.forbiddenisland.model.Card;

public interface Deck {

	abstract Card drawCard();

	abstract void reset();

	abstract void shuffle();

}
