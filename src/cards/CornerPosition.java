package cards;

public enum CornerPosition {
    // Enum con valori abbreviati
    TOP_LEFT(0, 1, "TOP_LEFT"),
    TOP_RIGHT(1, 1, "TOP_RIGHT"),
    BOTTOM_RIGHT(1, 0, "BOT_RIGHT"),
    BOTTOM_LEFT(0, 0, "BOT_LEFT");

    private final int xOffset;
    private final int yOffset;
    private final String abbreviation; // Abbreviazione del nome

    CornerPosition(int yOffset, int xOffset, String abbreviation) {
        this.xOffset = yOffset;
        this.yOffset = xOffset;
        this.abbreviation = abbreviation;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    private int value;

    public int getValue() {
        return value;
    }
    


    public CornerPosition getOpposite() {
        switch (this) {
            case TOP_LEFT:
                return BOTTOM_RIGHT;
            case TOP_RIGHT:
                return BOTTOM_LEFT;
            case BOTTOM_RIGHT:
                return TOP_LEFT;
            case BOTTOM_LEFT:
                return TOP_RIGHT;
            default:
                return null; // Non dovrebbe mai accadere
        }
    }

    @Override
    public String toString() {
        return abbreviation;  // Restituisce l'abbreviazione
    }
}
