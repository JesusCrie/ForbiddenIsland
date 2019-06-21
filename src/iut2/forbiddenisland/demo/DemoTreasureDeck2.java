package iut2.forbiddenisland.demo;

import iut2.forbiddenisland.model.card.Deck;
import iut2.forbiddenisland.model.card.RisingWatersCard;
import iut2.forbiddenisland.model.card.TreasureCard;

import java.util.Arrays;

public class DemoTreasureDeck2 extends Deck<TreasureCard> {

    public DemoTreasureDeck2() {
        super(Arrays.asList(
                new RisingWatersCard(),
                new RisingWatersCard(),
                new RisingWatersCard(),
                new RisingWatersCard(),
                new RisingWatersCard(),
                new RisingWatersCard()
        ));
    }
}
