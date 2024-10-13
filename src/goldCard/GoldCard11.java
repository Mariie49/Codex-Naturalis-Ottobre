package goldCard;

import java.util.ArrayList;

import cards.*;

public class GoldCard11 extends GoldCard {

	private boolean isPlaced = false;
	private Object[][] pointsAssignment = {
		    { 0 , "niente"},
		    { 1 , "QUILL"},
		    { 0 , "MANUSCRIPT"},
		    { 0 , "INKWELL"},
		    { 0 , "coveredCards"}			
	};
	private static boolean isFront = true;
	private static int number = 11;
	private static SpecialSymbol cornerSymbol = SpecialSymbol.QUILL;
	private static Symbol kingdom = Symbol.ANIMAL_KINGDOM;
	private static Symbol otherSymbol = Symbol.FUNGI_KINGDOM;
	
	// Card with 3 corners available
	private  Corner corner1 = new Corner (CornerPosition.TOP_LEFT, CornerState.NULL ,null);
	private  Corner corner2 = new Corner (CornerPosition.TOP_RIGHT, CornerState.EMPTY ,null);
	private  Corner corner3 = new Corner (CornerPosition.BOTTOM_LEFT, CornerState.SPECIALSYMBOL,cornerSymbol);
	private  Corner corner4 = new Corner (CornerPosition.BOTTOM_RIGHT, CornerState.EMPTY ,null);
	
	
	public GoldCard11() {}

	@Override
	public ArrayList<Corner> addCorners() {
		ArrayList <Corner> frontCorners = new ArrayList<Corner>();
		frontCorners.add(corner1);
		frontCorners.add(corner2);
		frontCorners.add(corner3);
		frontCorners.add(corner4);
		return frontCorners;
	}

	@Override
	public ArrayList<Symbol> createRequirementsForPoints() {
		ArrayList<Symbol> requirements = new ArrayList<>(); 
		requirements.add(kingdom);
		requirements.add(kingdom);
		requirements.add(otherSymbol);
		return requirements;
	}
    @Override
    public Object[][] getPointsAssignment() {
        return pointsAssignment;
    }


	/**
	 * @return the isFront
	 */
	public boolean isFront() {
		return isFront;
	}

	/**
	 * @return the isPlaced
	 */
	public boolean isPlaced() {
		return isPlaced;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the cornerSymbol
	 */
	public SpecialSymbol getCornerSymbol() {
		return cornerSymbol;
	}

	/**
	 * @return the kingdom
	 */
	public Symbol getKingdom() {
		return kingdom;
	}

	/**
	 * @return the otherSymbol
	 */
	public Symbol getOtherSymbol() {
		return otherSymbol;
	}


	/**
	 * @param isPlaced the isPlaced to set
	 */
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}

}
