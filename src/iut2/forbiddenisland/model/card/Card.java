package iut2.forbiddenisland.model.card;

import java.util.Objects;

public abstract class Card {

    private final String name;

    public Card(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Equality methods

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card && ((Card) obj).name.equals(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
