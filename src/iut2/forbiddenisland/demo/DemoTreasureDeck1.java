package iut2.forbiddenisland.demo;

import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.card.*;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoTreasureDeck1 extends Deck<TreasureCard> {

    public DemoTreasureDeck1(Treasure zephyrStatue, Treasure ardentCrystal, Treasure waveChalice, Treasure sacredStone) {
        super(
                        Stream.of(
                                new TreasurePartCard(zephyrStatue,TreasureCardGraphicalMetadata.TREASURE_ZEPHYR_STATUE),
                                new TreasurePartCard(ardentCrystal,TreasureCardGraphicalMetadata.TREASURE_ARDENT_CRYSTAL),
                                new TreasurePartCard(waveChalice,TreasureCardGraphicalMetadata.TREASURE_WAVE_CHALICE),
                                new TreasurePartCard(sacredStone,TreasureCardGraphicalMetadata.TREASURE_SACRED_STONE),
                                new SandBagCard(),
                                new SandBagCard(),
                                new HelicopterCard(),
                                new HelicopterCard(),
                                new RisingWatersCard(),
                                new HelicopterCard(),
                                new TreasurePartCard(zephyrStatue,TreasureCardGraphicalMetadata.TREASURE_ZEPHYR_STATUE),
                                new TreasurePartCard(waveChalice,TreasureCardGraphicalMetadata.TREASURE_WAVE_CHALICE),
                                new TreasurePartCard(sacredStone,TreasureCardGraphicalMetadata.TREASURE_SACRED_STONE),
                                new TreasurePartCard(ardentCrystal,TreasureCardGraphicalMetadata.TREASURE_ARDENT_CRYSTAL)
                        )
                .collect(Collectors.toList())
        );
    }
}
