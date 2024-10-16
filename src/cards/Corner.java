package cards;

/**
 * Rappresenta un angolo (Corner) di una carta, che può contenere un simbolo,
 * essere coperto e avere uno stato specifico.
 */
public class Corner {

	private CornerPosition position;
	private CornerState state;
	private Object symbol;
	private boolean isCovered = false; // Flag per indicare se l'angolo � coperto
	

	 /**
     * Costruttore vuoto che crea un angolo senza impostare parametri iniziali.
     */
    public Corner() {}

    /**
     * Costruttore che inizializza l'angolo con una posizione, stato e simbolo specifici.
     *
     * @param position La posizione dell'angolo.
     * @param state Lo stato dell'angolo.
     * @param symbol Il simbolo associato all'angolo, può essere null a seconda dello stato.
     * @throws IllegalArgumentException Se il simbolo non è valido per lo stato dato.
     */

	public Corner(CornerPosition position, CornerState state, Object symbol) {
		if (state == CornerState.SYMBOL && !(symbol instanceof Symbol)) {
			throw new IllegalArgumentException("Quando lo stato � 'SYMBOL', symbol deve essere un'istanza di Symbol");
		}
		if (state == CornerState.SPECIALSYMBOL && !(symbol instanceof SpecialSymbol)) {
			throw new IllegalArgumentException("Quando lo stato � 'SPECIALSYMBOL', symbol deve essere un'istanza di SpecialSymbol");
		}
		if ((state == CornerState.EMPTY || state == CornerState.NULL) && symbol != null) {
			throw new IllegalArgumentException("Quando lo stato � 'EMPTY' o 'NULL', symbol deve essere null");
		}

		this.position = position;
		this.state = state;
		this.symbol = symbol;

	}

	   /**
     * Imposta la posizione dell'angolo.
     * 
     * @param position La nuova posizione da impostare.
     */
    public void setPosition(CornerPosition position) {
        this.position = position;
    }

    /**
     * Restituisce la posizione dell'angolo.
     *
     * @return La posizione dell'angolo.
     */
    public CornerPosition getPosition() {
        return position;
    }

	/**
     * Restituisce lo stato corrente dell'angolo.
     *
     * @return Lo stato dell'angolo.
     */
    public CornerState getState() {
        return state;
    }

	/**
     * Restituisce il simbolo associato all'angolo.
     *
     * @return Il simbolo dell'angolo, può essere null.
     */
    public Object getSymbol() {
        return symbol;
    }
	

    /**
     * Imposta un nuovo stato per l'angolo.
     *
     * @param state Il nuovo stato da impostare.
     */
    public void setState(CornerState state) {
        this.state = state;
    }

	/**
     * Imposta il simbolo associato all'angolo.
     *
     * @param symbol Il simbolo da associare all'angolo.
     */
    public void setSymbol(Object symbol) {
        this.symbol = symbol;
    }

    /**
     * Verifica se l'angolo è coperto.
     *
     * @return true se l'angolo è coperto, false altrimenti.
     */
    public boolean isCovered() {
        return isCovered;
    }

    /**
     * Imposta lo stato di copertura dell'angolo.
     *
     * @param covered true se l'angolo deve essere coperto, false altrimenti.
     */
    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    /**
     * Restituisce una rappresentazione testuale dell'angolo.
     *
     * @return Una stringa che descrive l'angolo con la sua posizione, stato e simbolo.
     */
    @Override
	public String toString() {
		return "Corner(position=" + position + ", state=" + state + ", symbol=" + symbol + ")";
	}


	

}

