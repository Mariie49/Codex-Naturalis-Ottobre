package initialCard;

import java.util.ArrayList;

import cards.Corner;
import cards.CornerPosition;
import cards.CornerState;
import cards.Symbol;

public class InitialCard2 extends InitialCard{
	private boolean isPlaced = false;
	private static boolean isFront = false;
	private static boolean hasCentralSymbol = false;
	private static int number = 2;
	private static Symbol symbol1 = Symbol.FUNGI_KINGDOM;
	private static Symbol symbol2 = Symbol.PLANT_KINGDOM;
	private static Symbol symbol3 = Symbol.ANIMAL_KINGDOM;
	private static Symbol symbol4 = Symbol.INSECT_KINGDOM;
	private Corner corner1 = new Corner (CornerPosition.TOP_LEFT, CornerState.SYMBOL, symbol1);
	private Corner corner2 = new Corner (CornerPosition.TOP_RIGHT,CornerState.SYMBOL,symbol2);
	private Corner corner3 = new Corner (CornerPosition.BOTTOM_RIGHT, CornerState.SYMBOL, symbol3);
	private Corner corner4 = new Corner (CornerPosition.BOTTOM_LEFT, CornerState.SYMBOL, symbol4);
	
	
	public InitialCard2() {}
	
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
	public boolean isPlaced() {
		return isPlaced;
	}
	@Override
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}
	@Override
	public boolean hasCentralSymbol() {
		return InitialCard2.hasCentralSymbol;
	}
	
	
	@Override
	public boolean isFront() {
		return InitialCard2.isFront;
	}
	@Override
	public int getNumber() {
		return InitialCard2.number;
	}
	
}
