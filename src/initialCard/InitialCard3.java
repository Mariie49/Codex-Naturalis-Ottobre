package initialCard;

import java.util.ArrayList;

import cards.Corner;
import cards.CornerPosition;
import cards.CornerState;
import cards.Symbol;

public class InitialCard3 extends InitialCard {
	private boolean isPlaced = false;
	private static boolean isFront = true;
	private static boolean hasCentralSymbol = true;
	private static int number = 3;
	private static Symbol symbol = Symbol.FUNGI_KINGDOM;
	private static Symbol differentSymbol = Symbol.ANIMAL_KINGDOM;
	private Corner corner1 = new Corner (CornerPosition.TOP_LEFT, CornerState.SYMBOL, differentSymbol);
	private Corner corner2 = new Corner (CornerPosition.TOP_RIGHT,CornerState.EMPTY, null);
	private Corner corner3 = new Corner (CornerPosition.BOTTOM_RIGHT, CornerState.SYMBOL, symbol);
	private Corner corner4 = new Corner (CornerPosition.BOTTOM_LEFT, CornerState.EMPTY, null);
	
	public InitialCard3() {}
	
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
		centralSymbol.add(symbol);
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
		return InitialCard3.hasCentralSymbol;
	}
	
	
	@Override
	public boolean isFront() {
		return InitialCard3.isFront;
	}
	@Override
	public int getNumber() {
		return InitialCard3.number;
	}


}
