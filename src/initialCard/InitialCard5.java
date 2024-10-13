package initialCard;

import java.util.ArrayList;

import cards.Corner;
import cards.CornerPosition;
import cards.CornerState;
import cards.Symbol;

/*
 - Card 3:
- Corners: 4 EMPTY
- Center Symbols: FUNGI_KINGDOM, PLANT_KINGDOM
 */
public class InitialCard5 extends InitialCard{
	private boolean isPlaced = false;
	private static boolean isFront = true;
	private static boolean hasCentralSymbol = true;
	private static int number = 5;
	private static Symbol symbol1 = Symbol.FUNGI_KINGDOM;
	private static Symbol symbol2 = Symbol.PLANT_KINGDOM;
	private Corner corner1 = new Corner (CornerPosition.TOP_LEFT, CornerState.EMPTY, null);
	private Corner corner2 = new Corner (CornerPosition.TOP_RIGHT,CornerState.EMPTY, null);
	private Corner corner3 = new Corner (CornerPosition.BOTTOM_RIGHT, CornerState.EMPTY, null);
	private Corner corner4 = new Corner (CornerPosition.BOTTOM_LEFT, CornerState.EMPTY, null);
	
	public InitialCard5() {}
	
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
	public ArrayList <Symbol> addCentralSymbol (){
		ArrayList <Symbol> centralSymbol = new ArrayList<>();
		centralSymbol.add(symbol1);
		centralSymbol.add(symbol2);
		return centralSymbol;
	}
	@Override
	public boolean isPlaced() {
		return isPlaced;
	}
	@Override
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}
	@Override
	public boolean hasCentralSymbol() {
		return InitialCard5.hasCentralSymbol;
	}
	
	
	@Override
	public boolean isFront() {
		return InitialCard5.isFront;
	}
	@Override
	public int getNumber() {
		return InitialCard5.number;
	}



	
}
