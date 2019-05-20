package iut2.forbiddenisland.model;

import java.util.List;

public class Treasure {

	private String name;
	private boolean claimable = true;
    private final List<TreasureCell> cells;

    public Treasure(final String name, final List<TreasureCell> cells) {
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

	public boolean isClaimable(){
		return claimable;
	}

    public List<TreasureCell> getCells() {
        return cells;
    }
}
