package iut2.forbiddenisland.model.card;

import iut2.forbiddenisland.model.cell.Cell;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represent a deck of flood cards, playable by the island.
 */
public class FloodDeck extends Deck<FloodCard> {

    public FloodDeck(final Collection<Cell> cells) {
        super(cells.stream()
                .map(FloodCard::new)
                .collect(Collectors.toList())
        );
    }
}
