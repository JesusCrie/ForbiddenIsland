package iut2.forbiddenisland.demo.scenario1;

import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.card.*;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;

import java.util.Arrays;

public class TreasureDeckDemo1 extends TreasureDeck {

    public TreasureDeckDemo1(final Treasure sacredStone,
                             final Treasure zephyrStatue,
                             final Treasure ardentCrystal,
                             final Treasure waveChalice) {
        super(Arrays.asList(
                // *** Pre-stat cards ***

                // Pilot
                new TreasurePartCard(waveChalice, TreasureCardGraphicalMetadata.TREASURE_WAVE_CHALICE),
                new TreasurePartCard(waveChalice, TreasureCardGraphicalMetadata.TREASURE_WAVE_CHALICE),

                // Diver
                new TreasurePartCard(sacredStone, TreasureCardGraphicalMetadata.TREASURE_SACRED_STONE),
                new TreasurePartCard(sacredStone, TreasureCardGraphicalMetadata.TREASURE_SACRED_STONE),

                // Messenger
                new TreasurePartCard(ardentCrystal, TreasureCardGraphicalMetadata.TREASURE_ARDENT_CRYSTAL),
                new TreasurePartCard(ardentCrystal, TreasureCardGraphicalMetadata.TREASURE_ARDENT_CRYSTAL),
                new TreasurePartCard(zephyrStatue, TreasureCardGraphicalMetadata.TREASURE_ZEPHYR_STATUE),

                // Engineer
                new TreasurePartCard(zephyrStatue, TreasureCardGraphicalMetadata.TREASURE_ZEPHYR_STATUE),
                new HelicopterCard(),

                // *** Start cards ***

                // Pilot
                new TreasurePartCard(waveChalice, TreasureCardGraphicalMetadata.TREASURE_WAVE_CHALICE),
                new TreasurePartCard(waveChalice, TreasureCardGraphicalMetadata.TREASURE_WAVE_CHALICE),

                // Diver
                new TreasurePartCard(sacredStone, TreasureCardGraphicalMetadata.TREASURE_SACRED_STONE),
                new TreasurePartCard(sacredStone, TreasureCardGraphicalMetadata.TREASURE_SACRED_STONE),

                // Messenger
                new TreasurePartCard(ardentCrystal, TreasureCardGraphicalMetadata.TREASURE_ARDENT_CRYSTAL),
                new TreasurePartCard(ardentCrystal, TreasureCardGraphicalMetadata.TREASURE_ARDENT_CRYSTAL),

                // Engineer
                new TreasurePartCard(zephyrStatue, TreasureCardGraphicalMetadata.TREASURE_ZEPHYR_STATUE),
                new TreasurePartCard(zephyrStatue, TreasureCardGraphicalMetadata.TREASURE_ZEPHYR_STATUE),

                // *** Game cards ***
                new TreasurePartCard(zephyrStatue, TreasureCardGraphicalMetadata.TREASURE_ZEPHYR_STATUE),
                new TreasurePartCard(ardentCrystal, TreasureCardGraphicalMetadata.TREASURE_ARDENT_CRYSTAL),
                new TreasurePartCard(waveChalice, TreasureCardGraphicalMetadata.TREASURE_WAVE_CHALICE),
                new TreasurePartCard(sacredStone, TreasureCardGraphicalMetadata.TREASURE_SACRED_STONE),
                new SandBagCard(),
                new SandBagCard(),
                new HelicopterCard(),
                new HelicopterCard(),
                new RisingWatersCard(),
                new HelicopterCard(),
                new TreasurePartCard(zephyrStatue, TreasureCardGraphicalMetadata.TREASURE_ZEPHYR_STATUE),
                new TreasurePartCard(ardentCrystal, TreasureCardGraphicalMetadata.TREASURE_ARDENT_CRYSTAL),
                new TreasurePartCard(waveChalice, TreasureCardGraphicalMetadata.TREASURE_WAVE_CHALICE),
                new TreasurePartCard(sacredStone, TreasureCardGraphicalMetadata.TREASURE_SACRED_STONE)
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
