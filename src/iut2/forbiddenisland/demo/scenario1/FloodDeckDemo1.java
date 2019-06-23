package iut2.forbiddenisland.demo.scenario1;

import iut2.forbiddenisland.model.Location;
import iut2.forbiddenisland.model.card.FloodCard;
import iut2.forbiddenisland.model.card.FloodDeck;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.Arrays;
import java.util.Map;

public class FloodDeckDemo1 extends FloodDeck {

    public FloodDeckDemo1(final Map<Location, Cell> map) {
        super(Arrays.asList(
                map.get(Location.from(0, 3)),
                map.get(Location.from(1, 3)),

                map.get(Location.from(1, 4)),
                map.get(Location.from(2, 5)),

                map.get(Location.from(5, 2)),
                map.get(Location.from(4, 4)),

                map.get(Location.from(3, 4)),
                map.get(Location.from(2, 0)),

                map.get(Location.from(5, 3)),
                map.get(Location.from(1, 1)),

                map.get(Location.from(4, 2)),
                map.get(Location.from(2, 1)),

                map.get(Location.from(4, 1)),
                map.get(Location.from(3, 3)),

                map.get(Location.from(1, 1)),
                map.get(Location.from(1, 2)),

                map.get(Location.from(3, 5)),
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
