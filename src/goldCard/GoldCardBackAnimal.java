package goldCard;

import java.util.ArrayList;

import cards.*;


public class GoldCardBackAnimal extends GoldCard {
	
	private boolean isPlaced = false;	
	private Object[][] pointsAssignment = {
		    { 0 , "niente"},
		    { 0 , "QUILL"},
		    { 0 , "MANUSCRIPT"},
		    { 0 , "INKWELL"},
		    { 0 , "coveredCards"}			
	};
	private static boolean isFront = false;
	private boolean hasCentralSymbol = true;
	private static int number = 42;
	private static Symbol kingdom = Symbol.ANIMAL_KINGDOM;
	
	// Card with 3 corners available
	private  Corner corner1 = new Corner (CornerPosition.TOP_LEFT, CornerState.EMPTY ,null);
	private  Corner corner2 = new Corner (CornerPosition.TOP_RIGHT, CornerState.EMPTY ,null);
	private  Corner corner3 = new Corner (CornerPosition.BOTTOM_LEFT, CornerState.EMPTY ,null);
	private  Corner corner4 = new Corner (CornerPosition.BOTTOM_RIGHT, CornerState.EMPTY ,null);
	
	
	public GoldCardBackAnimal() {}

	@Override
	public ArrayList<Corner> addCorners() {
		ArrayList <Corner> frontCorners = new ArrayList<Corner>();
		frontCorners.add(corner1);
		frontCorners.add(corner2);
		frontCorners.add(corner3);
		frontCorners.add(corner4);
		return frontCorners;
	}

	/**
	 * @return the isPlaced
	 */
	public boolean isPlaced() {
		return isPlaced;
	}

	/**
	 * @return the isFront
	 */
	public boolean isFront() {
		return isFront;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the kingdom
	 */
	public Symbol getKingdom() {
		return kingdom;
	}
	@Override
	public boolean hasCentralSymbol() {
		return hasCentralSymbol;
	}


	/**
	 * @param isPlaced the isPlaced to set
	 */
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}

	public Object[][] getPointsAssignment() {
		return pointsAssignment;
	}

	public void setPointsAssignment(Object[][] pointsAssignment) {
		this.pointsAssignment = pointsAssignment;
	}

}
