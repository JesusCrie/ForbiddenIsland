package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.Treasure;
import iut2.forbiddenisland.model.card.TreasureCard;
import iut2.forbiddenisland.model.cell.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Adventurer {

    private final String name;
    private final List<Power> powers;
    private Cell position;
    private final List<Treasure> treasures = new ArrayList<>();
    private List<TreasureCard> cards;

    public Adventurer(final String name, final Cell position, final Power... powers) {
        this.name = name;
        this.powers = Arrays.asList(powers);
        this.position = position;
    }

    public String getName() {
        return this.name;
    }

    public Cell getPosition() {
        return position;
    }

    public void move(final Cell to) {
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

    public boolean addTreasure(Treasure t) {
        return getTreasures().add(t);
    }

    public List<TreasureCard> getCards() {
        return cards;
    }

    public boolean addCard(TreasureCard c) {
        return getCards().add(c);
    }

    public boolean removeCard(TreasureCard c) {
        return getCards().remove(c);
    }
}
