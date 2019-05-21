package iut2.forbiddenisland.model;

public abstract class Card {

    private final String name;

    public Card(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
