package cards;
import java.util.ArrayList;
import game.PlayArea;


/**
 * La classe astratta {@code Card} rappresenta una carta generica in un gioco,
 * con vari attributi come tipo, simboli e posizione nell'area di gioco.
 * Questa classe contiene metodi per gestire il posizionamento della carta
 *  e funzionalità specifiche della carta,
 * come l'aggiunta di simboli e il calcolo dei punti.
 * Le sottoclassi devono implementare i metodi astratti per fornire dettagli
 * aggiuntivi specifici al tipo di carta.
 */
public abstract class Card {
	private CardType type;
	private ArrayList <Object> symbols;
	private Symbol kingdom;
	private Corner corner;
	private boolean isPlaced = false;
	private boolean hasCentralSymbol;
	private int number;
	//private int row;
	private int column;
	/**
	 * Restituisce il tipo di angolo della carta.
	 *
	 * @return L'angolo associato alla carta.
	 */
	public Corner getCorner() {
		return corner;
	}
	/**
	 * Verifica se la carta è stata posizionata nell'area di gioco.
	 *
	 * @return {@code true} se la carta è posizionata, altrimenti {@code false}
	 */
	public boolean isPlaced() {
		return isPlaced;
	}
	/**
	 * Imposta lo stato di posizionamento della carta.
	 *
	 * @param isPlaced Il nuovo stato di posizionamento.
	 */
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}

	/**
	 * Imposta il tipo di angolo per questa carta.
	 *
	 * @param corner L'angolo da associare a questa carta.
	 */

	public void setCorner(Corner corner) {
		this.corner = corner;
	}

	/**
	 * Restituisce il numero assegnato a questa carta.
	 *
	 * @return Il numero della carta.
	 */

	public int getNumber() {
		return number;
	}

	/**
	 * Imposta il numero per questa carta.
	 *
	 * @param number Il numero da assegnare a questa carta.
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Restituisce la riga della carta nell'area di gioco.
	 *
	 * @return La riga della carta.
	 */
	//public int getRow() {
	//return row;
	//}
	/**
	 * Imposta la riga della carta nell'area di gioco.
	 *
	 * @param row La nuova riga della carta.
	 */
	//void setRow(int row) {
	//	this.row = row;
	//}
	/**
	 * Restituisce la colonna della carta nell'area di gioco.
	 *
	 * @return La colonna della carta.
	 */
	public int getColumn() {
	    return column;
	}
	/**
	 * Restituisce la colonna della carta nell'area di gioco.
	 *
	 * @return La colonna della carta.
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Restituisce il simbolo centrale che rappresenta il regno sulla carta.
	 *
	 * @return Il simbolo centrale che rappresenta il regno.
	 */

	public Symbol getCentralSymbol() {
		return kingdom;
	}
	/**
	 * Aggiunge angoli alla carta.
	 *
	 * @return Una lista di angoli associati alla carta.
	 */
	public ArrayList <Corner> addCorners (){
		ArrayList <Corner> corners = new ArrayList<>();
		return corners;
	}
	

    /**
     * Verifica se la carta ha un simbolo centrale.
     *
     * @return {@code true} se la carta ha un simbolo centrale, altrimenti {@code false}.
     */

	public boolean hasCentralSymbol() {
		return hasCentralSymbol;
	}
	/**
     * Aggiunge un simbolo centrale alla carta.
     *
     * @return Una lista di simboli centrali per la carta.
     */
	public ArrayList <Symbol> addCentralSymbol (){
		ArrayList <Symbol> symbols = new ArrayList<>();
		return symbols;
	}
	  /**
     * Restituisce il simbolo del regno associato alla carta.
     *
     * @return Il simbolo che rappresenta il regno.
     */
	public Symbol getKingdom() {
		return this.kingdom;
	}

	 /**
     * Restituisce la lista di simboli associati alla carta.
     *
     * @return Una lista di simboli.
     */
    public ArrayList<Object> getSymbols() {
        return symbols;
    }

    /**
     * Imposta la lista di simboli per la carta.
     *
     * @param symbols La lista di simboli da associare alla carta.
     */
    public void setSymbols(ArrayList<Object> symbols) {
        this.symbols = symbols;
    }

    /**
     * Permette al giocatore di scegliere un lato per questa carta.
     *
     * @param d la carta a cui è associato il fronte e il retro.
     * @return La carta scelta.
     */
    public Card ChooseSide(Card d) {
        return d;
    }


	/*
    public Card ChooseSide () { 
    	return card;
    }
	 */

    /**
     * Stampa una rappresentazione dettagliata della carta.
     * L'implementazione specifica è lasciata alle sottoclassi.
     */
    public void printCard() {}
   
  
    /**
     * Restituisce il tipo di carta.
     *
     * @return Il tipo della carta.
     */
    public CardType getType() {
        return type;
    }

    /**
     * Imposta il tipo di carta.
     *
     * @param type Il tipo da assegnare alla carta.
     */
    public void setType(CardType type) {
        this.type = type;
    }

    /**
     * Crea i requisiti per ottenere punti basati sulla carta.
     *
     * @return Una lista di simboli che rappresentano i requisiti per i punti.
     */
    public ArrayList<Symbol> createRequirementsForPoints() {
        ArrayList<Symbol> requirements = new ArrayList<>();
        return requirements;
    }

    /**
     * Metodo astratto per ottenere l'abbreviazione del tipo di angolo della carta.
     *
     * @param cornertype Il tipo di angolo da abbreviare.
     * @return Una stringa che rappresenta l'abbreviazione del tipo di angolo.
     */
    public abstract String getAbbreviatedCorner(CornerPosition cornertype);

    /**
     * Metodo astratto per ottenere l'assegnazione dei punti per la carta.
     * 
     * @return Una matrice che rappresenta l'assegnazione dei punti per la carta.
     */
    public abstract Object[][] getPointsAssignment();


	//public abstract int calculateObjectiveCardPoints();

    /**
     * Calcola i punti obiettivo della carta basati sull'area di gioco.
     * 
     * @param playArea L'area di gioco attuale.
     * @return I punti calcolati per questa carta.
     */
    public int calculateObjectiveCardPoints(PlayArea playArea) {
        return column;
    }
}
