package iut2.forbiddenisland.demo.scenario2;

import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.card.FloodCard;
import iut2.forbiddenisland.model.card.FloodDeck;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.Arrays;
import java.util.Map;

public class FloodDeckDemo2 extends FloodDeck {

    public FloodDeckDemo2(final Map<Location, Cell> map) {
        super(Arrays.asList(
                map.get(Location.from(1, 1)),
                map.get(Location.from(5, 3)),
                map.get(Location.from(1, 1)),
                map.get(Location.from(3, 2))
        ));
    }

    @Override
    public FloodCard drawCard() {
        return deck.pop();
    }

    @Override
    public void reset() {
        /* no-op */
    }

    @Override
    public void shuffle() {
        /* no-op */
    }
}
