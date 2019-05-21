package iut2.forbiddenisland.model.card;

import iut2.forbiddenisland.model.Treasure;

public abstract class TreasureCard extends Card {

	private Treasure treasure;

	/**
	 * @param name
	 */
	public TreasureCard(String name) {
		super(name);
	}
}
