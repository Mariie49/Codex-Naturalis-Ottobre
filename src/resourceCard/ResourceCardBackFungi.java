package resourceCard;

import java.util.ArrayList;

import cards.Corner;
import cards.CornerPosition;
import cards.CornerState;
import cards.Symbol;

public class ResourceCardBackFungi extends ResourceCard {

	private Object[][] pointsAssignment = {
		    { 0 , "niente"},
		    { 0 , "QUILL"},
		    { 0 , "MANUSCRIPT"},
		    { 0 , "INKWELL"},
		    { 0 , "coveredCards"}			
	};
	private static boolean isFront= false;
	private static int number= 42;
	private boolean hasCentralSymbol = true;
	private static Symbol symbol = Symbol.FUNGI_KINGDOM;
	//private ArrayList <Corner> corners = new ArrayList <Corner>;
	private Corner corner2 = new Corner (CornerPosition.TOP_RIGHT, CornerState.EMPTY, null);
	private Corner corner3 = new Corner (CornerPosition.BOTTOM_RIGHT, CornerState.EMPTY, null );
	private Corner corner4 = new Corner (CornerPosition.BOTTOM_LEFT, CornerState.EMPTY, null);
	private Corner corner1 = new Corner (CornerPosition.TOP_LEFT, CornerState.EMPTY, null);
	private boolean isPlaced = false;
	
	public ResourceCardBackFungi() {}
	
	
	
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
		return ResourceCardBackFungi.symbol;
	}
	
	@Override
	public boolean isFront() {
		return isFront;
	}
	@Override
	public int getResourceCardNumber() {
		return ResourceCardBackFungi.number;
	}

	@Override
	public boolean hasCentralSymbol() {
		return hasCentralSymbol;
	}

}
