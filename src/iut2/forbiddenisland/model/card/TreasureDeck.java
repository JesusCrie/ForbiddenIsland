package iut2.forbiddenisland.model.card;

import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.view.TreasureCardGraphicalMetadata;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represent the deck where players draw there cards from.
 */
public class TreasureDeck extends Deck<TreasureCard> {

    public TreasureDeck(final List<Treasure> treasures) {
        super(
                // Concatenate the two streams of cards, one of TreasurePartCards, the other one of SpecialCards
                Stream.concat(
                        treasures.stream()
                                .flatMap(treasure -> { // Convert the treasures into 5 TreasurePartCard per treasure
                                    final TreasureCardGraphicalMetadata metadata = TreasureCardGraphicalMetadata.from(treasure);
                                    return Stream.of(
                                            new TreasurePartCard(treasure, metadata),
                                            new TreasurePartCard(treasure, metadata),
                                            new TreasurePartCard(treasure, metadata),
                                            new TreasurePartCard(treasure, metadata),
                                            new TreasurePartCard(treasure, metadata)
                                    );
                                }),
                        Stream.of( // Create a stream of every special cards
                                new RisingWatersCard(), // 3 rising waters cards
                                new RisingWatersCard(),
                                new RisingWatersCard(),

                                new HelicopterCard(), // 3 helicopter cards
                                new HelicopterCard(),
                                new HelicopterCard(),

                                new SandBagCard(), // 2 sand bag cards
                                new SandBagCard()
                        )

                ).collect(Collectors.toList()) // Convert the concatenation of streams into a list
        );
    }
}
