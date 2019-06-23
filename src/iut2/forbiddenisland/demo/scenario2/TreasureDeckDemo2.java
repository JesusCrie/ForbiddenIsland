package iut2.forbiddenisland.demo.scenario2;

import iut2.forbiddenisland.model.card.*;

import java.util.Arrays;

public class TreasureDeckDemo2 extends TreasureDeck {

    public TreasureDeckDemo2() {
        super(Arrays.asList(
                // Starting cards
                new SandBagCard(),
                new SandBagCard(),
                new SandBagCard(),
                new SandBagCard(),

                // Game cards
                new HelicopterCard(),
                new RisingWatersCard(),

                new SandBagCard(),
                new SandBagCard(),

                new HelicopterCard(),
                new SandBagCard()
        ), null);
    }

    @Override
    public TreasureCard drawCard() {
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
