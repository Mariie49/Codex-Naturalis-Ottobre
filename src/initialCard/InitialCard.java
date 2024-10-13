package initialCard;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import cards.Card;
import cards.CardType;
import cards.Corner;
import cards.CornerPosition;
import cards.SpecialSymbol;
import cards.Symbol;

public abstract class InitialCard extends Card {

	private CardType type = CardType.STARTING; // Tipo di carta
	private boolean isFront;
	private int number;
	private Corner corner1;
	private Corner corner2;
	private Corner corner3;
	private Corner corner4;
	private static ArrayList<Integer> assignedInitialCards=new ArrayList<>();
	private boolean isPlaced = false; 
	private boolean hasCentralSymbol;
	private Symbol symbolA;
	private final Scanner scanner = new Scanner(System.in);
	public InitialCard () {}

	@Override
	public int getNumber() {
		return this.number;
	}
	
	@Override
	public ArrayList <Corner> addCorners (){
		ArrayList <Corner> corners = new ArrayList<>();
		corners.add(corner1);
		corners.add(corner2);
		corners.add(corner3);
		corners.add(corner4);
		for (int i = 0; i < corners.size(); i++) {
			corners.get(i).setPosition(CornerPosition.values()[i]);
		}
		return corners;
	}
	@Override
	public ArrayList <Symbol> addCentralSymbol (){
		ArrayList <Symbol> centralSymbol = new ArrayList<>();
		centralSymbol.add(symbolA);
		return centralSymbol;
	}

	public static void resetInirialCards() {
		assignedInitialCards =new ArrayList<>();
	}


	/**
	 * metodo per assegnare una carta  casuale
	 * @return carta iniziale
	 */
	public static InitialCard drawInitialCard() {
		InitialCard card = null;
		Random r = new Random();
		int n=0;

		do{
			n=r.nextInt(6)*2 + 1;

		}while(assignedInitialCards.contains(n));

		assignedInitialCards.add(n);
		switch(n) {
		case 1:
			card=new InitialCard1();
			break;
		case 2:
			card=new InitialCard2();
			break;
		case 3:
			card=new InitialCard3();
			break;
		case 4:
			card=new InitialCard4();
			break;
		case 5:
			card=new InitialCard5();
			break;
		case 6:
			card=new InitialCard6();
			break;
		case 7:
			card=new InitialCard7();
			break;
		case 8:
			card=new InitialCard8();
			break;
		case 9:
			card=new InitialCard9();
			break;
		case 10:
			card=new InitialCard10();
			break;
		case 11:
			card=new InitialCard11();
			break;
		case 12:
			card=new InitialCard12();
			break;
		}
		return card;
	}

	public CardType getType() {
		return type;
	}

	public Object[][] getPointsAssignment(){
		return null;		
	};
	@Override
	public boolean isPlaced() {
		return isPlaced;
	}
	@Override
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}


	public boolean isFront() {
		return isFront;
	}
	@Override
	public boolean hasCentralSymbol() {
		return hasCentralSymbol;
	}
	@Override
	public Symbol getCentralSymbol() {
		return symbolA;
	}
	public String getAbbreviatedCorner(CornerPosition position) {
	    for (Corner corner : addCorners()) {
	        if (corner.getPosition().equals(position)) {
	            switch (corner.getState()) {
	                case NULL:
	                    return "NUL";
	                case EMPTY:
	                    return "EMP";
	                case HIDDEN:
	                    return "HID";
	                case SYMBOL:
	                	if (corner.getSymbol() instanceof Symbol) {
	                        Symbol symbol = (Symbol) corner.getSymbol();
	                        return symbol.getAbbreviation();
	                	} else {
	                        
	                        return corner.getSymbol().toString();
	                    }
	                case SPECIALSYMBOL:
	                	if (corner.getSymbol() instanceof SpecialSymbol) {
	                        SpecialSymbol symbol = (SpecialSymbol) corner.getSymbol();
	                        return symbol.getAbbreviation();
	                	} else {
	                        
	                        return corner.getSymbol().toString();
	                    }
	                default:
	                    return "?";
	            }
	        }
	    }
	    return "  "; // Angolo non trovato (non dovrebbe accadere)
	}
	@Override
	public void printCardInCell() {
		if(this.isFront()) {
	
			System.out.print(getAbbreviatedCorner(CornerPosition.TOP_LEFT) + "  "+
					getAbbreviatedCorner(CornerPosition.TOP_RIGHT));
			System.out.println();
			if(this.hasCentralSymbol()) {
				for(Symbol s: addCentralSymbol())
					System.out.println("  " + s.getAbbreviation() + "   ");
			}
			else {
				System.out.println();
			}

			System.out.println(getAbbreviatedCorner(CornerPosition.BOTTOM_RIGHT) + "  "+
					getAbbreviatedCorner(CornerPosition.BOTTOM_LEFT));	
		}
		else {
			System.out.print(getAbbreviatedCorner(CornerPosition.TOP_LEFT) + "  "+
					getAbbreviatedCorner(CornerPosition.TOP_RIGHT));
			System.out.println();
			if(this.hasCentralSymbol()) {
				for(Symbol s: addCentralSymbol())
					System.out.print("  " + s.getAbbreviation() + "   ");
			}
			else {
				System.out.println();
			}

			System.out.println(getAbbreviatedCorner(CornerPosition.BOTTOM_LEFT) + "  "+
					getAbbreviatedCorner(CornerPosition.BOTTOM_RIGHT));	

		}
	}

	public String getCornerRepresentation(CornerPosition position) {
		for (Corner corner : addCorners()) {
			if (corner.getPosition().equals(position)) {
				switch (corner.getState()) {
				case NULL:
					return "NULL";
				case EMPTY:
					return "EMPTY";
				case SYMBOL:
					return corner.getSymbol().toString();
				case SPECIALSYMBOL:
					return corner.getSymbol().toString();
				default:
					return "?";
				}
			}
		}
		return "  "; // Angolo non trovato (non dovrebbe accadere)
	}
	
	public String getCornerRepresentationP(CornerPosition position) {
		for (Corner corner : addCorners()) {
			if (corner.getPosition().equals(position)) {
				switch (corner.getState()) {
				case NULL:
					return "NULl";
				case EMPTY:
					return "EMPTY";
				case SYMBOL:
					return corner.getSymbol().toString();
				case SPECIALSYMBOL:
					return corner.getSymbol().toString();
				case HIDDEN:
					return "HIDDEN";
				default:
					return "?";
				}
			}
		}
		return "  "; // Angolo non trovato (non dovrebbe accadere)
	}
	/**
	 * Prints the details of the card.
	 * 
	 * This method displays the card's type, the side (front or back),
	 * the corner symbols and Central symbols (if present) of that side, and the card's score.
	 * If the card has no Central symbols, a blank line is printed to maintain consistent card height.
	 */
	@Override
	public void printCard() {		
		
		System.out.println("Card_id: INI_"+ getNumber() +", Type: " + getType());
		//System.out.println("Score: " + getPoints()); Le starting cards non hanno punti
		if(this.isFront()) 	{System.out.println("Front side :\n");}
		else 				{System.out.println("Back side :\n");}
		
		System.out.println(getAbbreviatedCorner(CornerPosition.TOP_LEFT) + "\t---\t"+
				getAbbreviatedCorner(CornerPosition.TOP_RIGHT));
		System.out.println(" | \t   \t|");

		System.out.println(getAbbreviatedCorner(CornerPosition.BOTTOM_RIGHT) + "\t---\t"+
				getAbbreviatedCorner(CornerPosition.BOTTOM_LEFT));	

		if(this.hasCentralSymbol()) {
			System.out.print("Central Symbols: [");
			for(Symbol s: addCentralSymbol()) {
				System.out.print("  " + s.getAbbreviation() + "   ");
			}
			System.out.println("]");
		} 
		else {
			System.out.println();
		}
	}
	/**
	 * This method displays a menu asking the user to select the front (1) or back (2) side.
	 * It reads the user's input and sets the isFront property of the card accordingly.
	 * If the user enters an invalid choice, it defaults to the front side.
	 */
	@Override
	public Card ChooseSide(Card inputCarta) {
        InitialCard card = null;
        boolean validChoice = false; 

        System.out.println("Scegli il fronte o il retro della prima carta da collocare sul tuo manoscritto?");
        System.out.println("1. Fronte");
        System.out.println("2. Retro");

        while (!validChoice) {
        	
        	while (!scanner.hasNextInt()) {
        	    System.out.println("Input non valido. Inserisci un numero.");
        	    scanner.next(); // Scarta l'input non valido
        	}
        	int choice = scanner.nextInt();
        	
            // Verifica che la scelta sia valida (1 o 2)
            if (choice != 1 && choice != 2) {
                System.out.println("Scelta non valida. Inserisci 1 o 2.");
                continue;
            }
            System.out.println("Hai scelto il numero: " + choice);

            validChoice = true;
            int val = inputCarta.getNumber(); // Ottieni il numero della carta

            // Se l'utente sceglie il retro (choice == 2)
            if (choice == 2) {
            	System.out.println("Back della carta numero: " + val);
                card = (InitialCard)createCardForBack(val, inputCarta);
            } else {
                // Se l'utente sceglie il fronte (choice == 1)
                card = (InitialCard) inputCarta;
            }
        }

        // Mostra quale lato è stato scelto
        System.out.println("Hai scelto " + (card.isFront() ? "fronte" : "retro") + " della carta, piazzata carta numero: "+card.getNumber());
        
        return card;
    }

    // Metodo per creare la carta per il retro in base al valore
    public InitialCard createCardForBack(int val, Card inputCarta) {
    	//System.out.println("switch entro in: " + val);
    	InitialCard card0;
        switch (val) {
            case 1:
            	card0 = new InitialCard2();
            break;
            case 3:
            	card0 = new InitialCard4();
            break;
            case 5:
            	card0 = new InitialCard6();
            break;
            case 7:
            	card0 = new InitialCard8();
            break;
            case 9:
            	card0 = new InitialCard10();
            break;
            case 11:
            	card0 = new InitialCard12();
            break;
            default:
                // Valore non valido, ritorna il fronte
                System.err.println("Valore non valido per il retro: " + val + ". Impostato il fronte.");
                card0 = (InitialCard) inputCarta; // Imposta il fronte
        }
        //System.out.println("Ho craftato la back della initial card, numero: " + card0.getNumber());
        return card0;
    }
	
	/*
	public Card ChooseSide(Card inputcarta) {
	    InitialCard card = null;
	    System.out.println("Scegli il fronte o il retro della carta?");
	    System.out.println("1. Fronte");
	    System.out.println("2. Retro");

	    Scanner in = new Scanner(System.in); 
	    boolean validChoice = false; 

	    while (!validChoice) {
	        if (in.hasNextInt()) { 
	            int choice = in.nextInt();
	            in.nextLine();

	            if (choice == 1 || choice == 2) {
	                validChoice = true;
	                int val = inputcarta.getNumber();
	                if (choice == 2) {
	                    switch (val) {
	                        case 1:
	                            card = new InitialCard2();
	                            break;
	                        case 3:
	                            card = new InitialCard4();
	                            break;
	                        case 5:
	                            card = new InitialCard6();
	                            break;
	                        case 7:
	                            card = new InitialCard8();
	                            break;
	                        case 9:
	                            card = new InitialCard10();
	                            break;
	                        case 11:
	                            card = new InitialCard12();
	                            break;
	                        default:
	                        	System.out.println(val);
	                            System.out.println("Scelta non valida. Impostato il fronte.");
	                            card = (InitialCard) inputcarta;
	                            break;
	                    }
	                } else {
	                    card = (InitialCard) inputcarta;
	                }
	            } else {
	                System.out.println("Scelta non valida. Inserisci 1 o 2.");
	            }
	        } else {
	            System.out.println("Input non valido. Inserisci un numero.");
	            in.next(); 
	        }
	    }

	    in.close(); 

	    System.out.println("Hai scelto  " + (card.isFront() ? "fronte" : "retro")+ " della carta.");
	    return card;
	}
	*/
}
