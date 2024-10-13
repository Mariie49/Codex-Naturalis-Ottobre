package cards;

public class Corner {

	private CornerPosition position;
	private CornerState state;
	private Object symbol;
	private boolean isCovered = false; // Flag per indicare se l'angolo � coperto
	

	public Corner() {}

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

	public void setPosition(CornerPosition position) {
        this.position = position;
    }
	
	public CornerPosition getPosition() {
        return position; // Restituisce direttamente la posizione
    }

	/**
	 * @return the state
	 */
	public CornerState getState() {
		return state;
	}

	/**
	 * @return the symbol
	 */
	public Object getSymbol() {
		return symbol;
	} 
	

	/**
	 * @param state the state to set
	 */
	public void setState(CornerState state) {
		this.state = state;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(Object symbol) {
		this.symbol = symbol;
	}
	public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

	@Override
	public String toString() {
		return "Corner(position=" + position + ", state=" + state + ", symbol=" + symbol + ")";
	}


	

}

