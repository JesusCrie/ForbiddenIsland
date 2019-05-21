package iut2.forbiddenisland.model;

public class WaterLevel {

    private int waterLevel;

    public WaterLevel(final int initialLevel) {
        this.waterLevel = initialLevel;
    }

    public void incrementWater() {
        ++waterLevel;
    }

    // Return number of card to drow
    // Returns -1 for the death and by default
    public int computeAmountFloodCards() {
        switch (waterLevel) {
            case 0:
            case 1:
                return 2;
            case 2:
            case 3:
            case 4:
                return 3;
            case 5:
            case 6:
            case 7:
                return 4;
            case 8:
            case 9:
                return 5;
            case 10:
            default:
                return -1;
        }
    }

}
