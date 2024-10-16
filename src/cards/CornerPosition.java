package cards;
/**
 * Enumerazione che rappresenta le posizioni degli angoli di una carta.
 * 
 * Ogni valore enum ha una posizione corrispondente tramite coordinate
 * (xOffset, yOffset) e un'abbreviazione del nome.
 *
 */
public enum CornerPosition {
	/**
     * Posizione in alto a sinistra (0,1).
     */
    TOP_LEFT(0, 1, "TOP_LEFT"),
    
    /**
     * Posizione in alto a destra (1,1).
     */
    TOP_RIGHT(1, 1, "TOP_RIGHT"),
    
    /**
     * Posizione in basso a destra (1,0).
     */
    BOTTOM_RIGHT(1, 0, "BOT_RIGHT"),
    
    /**
     * Posizione in basso a sinistra (0,0).
     */
    BOTTOM_LEFT(0, 0, "BOT_LEFT");

    private final int xOffset;
    private final int yOffset;
    private final String abbreviation; // Abbreviazione del nome
    /**
     * Costruttore dell'enum per inizializzare i valori di posizione e abbreviazione.
     *
     * @param yOffset L'offset verticale.
     * @param xOffset L'offset orizzontale.
     * @param abbreviation L'abbreviazione del nome della posizione.
     */
    CornerPosition(int yOffset, int xOffset, String abbreviation) {
        this.xOffset = yOffset;
        this.yOffset = xOffset;
        this.abbreviation = abbreviation;
    }

    /**
     * Restituisce l'offset orizzontale (x) della posizione.
     *
     * @return L'offset orizzontale (x).
     */
    public int getXOffset() {
        return xOffset;
    }

    /**
     * Restituisce l'offset verticale (y) della posizione.
     *
     * @return L'offset verticale (y).
     */
    public int getYOffset() {
        return yOffset;
    }

    /**
     * Restituisce la posizione opposta all'angolo corrente.
     *
     * @return La posizione opposta.
     */


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

    /**
     * Restituisce l'abbreviazione della posizione.
     *
     * @return L'abbreviazione della posizione.
     */
    @Override
    public String toString() {
        return abbreviation;  // Restituisce l'abbreviazione
    }
}
