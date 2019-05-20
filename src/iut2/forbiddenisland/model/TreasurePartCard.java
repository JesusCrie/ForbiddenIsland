package iut2.forbiddenisland.model;

public class TreasurePartCard extends TreasureCard {

    private final Treasure treasure;

    public TreasurePartCard(final String name, final Treasure treasure) {
        super(name);
        this.treasure = treasure;
    }
}
