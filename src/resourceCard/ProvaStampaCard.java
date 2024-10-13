package resourceCard;

import java.util.ArrayList;

import cards.Corner;
import initialCard.InitialCard3;
import initialCard.InitialCard4;
import cards.Card;

public class ProvaStampaCard {

	public static void main(String[] args) {
		//ArrayList<ResourceCard> card= new ArrayList<ResourceCard>();
		Card card = new ResourceCard23();
		//ResourceCardBackPlant cardiB  =  new ResourceCardBackPlant();
		//InitialCard3 cardI = new InitialCard3 ();
		InitialCard4 cardIB = new InitialCard4 ();
		 //cardI.printCard();
		 for(Corner c : card.addCorners()) {
			 c.toString();
		 }
		 System.out.println(card.addCorners());
		 System.out.println();
		 //cardIB.printCard();
		 card.printCardInCell();
		 
		
		 /*
		  * System.out.println(card.getSymbol());
		 System.out.println(card.getPoints());
		 System.out.println(card.getResourceCardNumber());
		 System.out.println(card.isFront());
		 System.out.println(card.getType());
		 card.setPlaced(true);
		 System.out.println(card.isPlaced());
		  */
	}

}
