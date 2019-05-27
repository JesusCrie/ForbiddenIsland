package iut2.forbiddenisland.model.adventurer;

import iut2.forbiddenisland.model.card.Card;
import iut2.forbiddenisland.model.cell.Cell;
import iut2.forbiddenisland.model.Treasure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Adventurer {

    private final String name;
    private final List<Power> powers;
    private Cell position;
    private final List<Treasure> treasures = new ArrayList<>();
    private List<Card> cards;

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

    public void addTreasure(Treasure t){
        getTreasures().add(t);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card c) {
        getCards().add(c);
    }

    public void removeCard(Card c){
        getCards().remove(c);
    }
}
