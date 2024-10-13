package cards;
import java.util.ArrayList;

import game.PlayArea;
public abstract class Card {
	private CardType type;
	private ArrayList <Object> symbols;
	private Symbol kingdom;
	private Corner corner;
	private boolean isPlaced = false;
	private boolean hasCentralSymbol;
	private int number;
	private int row;
    private int column;
	
	public Corner getCorner() {
		return corner;
	}

	public boolean isPlaced() {
		return isPlaced;
	}
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}
	
	public void setCorner(Corner corner) {
		this.corner = corner;
	}

	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}
	
	    /**
	    * Gets the row of the card on the play area.
	    *
	    * @return The row of the card.
	    */
	    public int getRow() {
			return row;
		}
	    /**
	     * Sets the row of the card on the play area.
	     *
	     * @param row The new row of the card.
	     */
		public void setRow(int row) {
			this.row = row;
		}
		/**
		 * Gets the column of the card on the play area.
		 *
		 * @return The column of the card.
		 */
		public int getColumn() {
			return column;
		}
		/**
		 * Sets the column of the card on the play area.
		 *
		 * @param column The new column of the card.
		 */
		public void setColumn(int column) {
			this.column = column;
		}
		
		public Symbol getCentralSymbol() {
			return kingdom;
		}
	
	public ArrayList <Corner> addCorners (){
		ArrayList <Corner> corners = new ArrayList<>();
		return corners;
	}
	
	public boolean hasCentralSymbol() {
		return hasCentralSymbol;
	}
	
	public ArrayList <Symbol> addCentralSymbol (){
		ArrayList <Symbol> symbols = new ArrayList<>();
		return symbols;
	}
	public Symbol getKingdom() {
		return this.kingdom;
	}
	
	public ArrayList<Object> getSymbols() {
        return symbols;
    }

    public void setSymbols(ArrayList<Object> symbols) {
        this.symbols = symbols;
    }

    
    public Card ChooseSide (Card d) { 
    	return d;
    }
    
    /*
    public Card ChooseSide () { 
    	return card;
    }
    */

    public void printCard() {}
    
    public void printCardInCell() {}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}
	public ArrayList<Symbol> createRequirementsForPoints() {
		ArrayList<Symbol> requirements = new ArrayList<>();
		return requirements;
	}
	public abstract String getAbbreviatedCorner(CornerPosition cornertype);

	public abstract Object[][] getPointsAssignment();

	//public abstract int calculateObjectiveCardPoints();

	public int calculateObjectiveCardPoints(PlayArea playArea) {
		return column;}
}
