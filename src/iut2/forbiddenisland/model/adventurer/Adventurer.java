package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.adventurer.power.Power;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represent an adventurer of any kind.
 */
public abstract class Adventurer {

    private final String name;
    private final List<Power> powers;
    private Cell position;
    private final List<Treasure> treasures = new ArrayList<>();
    private final List<TreasureCard> cards = new ArrayList<>();

    public Adventurer(final String name, final Power... powers) {
        this.name = name;
        this.powers = Arrays.asList(powers);
    }

    public String getName() {
        return this.name;
    }

    public Cell getPosition() {
        return position;
    }

    public void move(final Cell to) {
        if (position != null)
            position.removeAdventurer(this);
        position = to;
        to.addAdventurer(this);

    }

    public List<Power> getPowers() {
        return powers;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public boolean addTreasure(final Treasure t) {
        return getTreasures().add(t);
    }

    public List<TreasureCard> getCards() {
        return cards;
    }

    public boolean addCard(final TreasureCard c) {
        return cards.add(c);
    }

    public boolean removeCard(final TreasureCard c) {
        return cards.remove(c);
    }
}
