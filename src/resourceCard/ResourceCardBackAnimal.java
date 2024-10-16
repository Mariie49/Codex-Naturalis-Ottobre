package resourceCard;

import java.util.ArrayList;

import cards.Corner;
import cards.CornerPosition;
import cards.CornerState;
import cards.Symbol;

public class ResourceCardBackAnimal extends ResourceCard {

	private Object[][] pointsAssignment = {
		    { 0 , "niente"},
		    { 0 , "QUILL"},
		    { 0 , "MANUSCRIPT"},
		    { 0 , "INKWELL"},
		    { 0 , "coveredCards"}			
	};
	private static boolean isFront= false;
	private static int number= 43;
	private static Symbol symbol = Symbol.PLANT_KINGDOM;
	private boolean hasCentralSymbol = true;
	//private ArrayList <Corner> corners = new ArrayList <Corner>;
	private Corner corner2 = new Corner (CornerPosition.TOP_RIGHT, CornerState.EMPTY, null);
	private Corner corner3 = new Corner (CornerPosition.BOTTOM_RIGHT, CornerState.EMPTY, null );
	private Corner corner4 = new Corner (CornerPosition.BOTTOM_LEFT, CornerState.EMPTY, null);
	private Corner corner1 = new Corner (CornerPosition.TOP_LEFT, CornerState.EMPTY, null);
	private boolean isPlaced = false;
	
	public ResourceCardBackAnimal() {}
	
	
	
	@Override
	public ArrayList<Corner> addCorners() {
		ArrayList <Corner> backCorners = new ArrayList<Corner>();
		backCorners.add(corner1);
		backCorners.add(corner2);
		backCorners.add(corner3);
		backCorners.add(corner4);
		return backCorners;
	}
	
	@Override
    public Object[][] getPointsAssignment() {
        return pointsAssignment;
    }
	
	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public boolean isPlaced() {
		return isPlaced;
	}
	@Override
	public void setPlaced(boolean isPlaced) {
		this.isPlaced   = isPlaced;
	}
	@Override
	public Symbol getKingdom() {
		return ResourceCardBackAnimal.symbol;
	}
	
	@Override
	public boolean isFront() {
		return ResourceCardBackAnimal.isFront;
	}
	@Override
	public int getResourceCardNumber() {
		return ResourceCardBackAnimal.number;
	}
	@Override
	public boolean hasCentralSymbol() {
		return hasCentralSymbol;
	}

	
	


}
