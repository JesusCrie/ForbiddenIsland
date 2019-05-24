package iut2.forbiddenisland.model.card;

public abstract class Card {

    private final String name;

    public Card(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card && ((Card) obj).name.equals(name);
    }
}
