package resourceCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import cards.Card;
import cards.CardType;
import cards.Corner;
import cards.CornerPosition;
import cards.SpecialSymbol;
import cards.Symbol;

/**
 * classe astratta per creazione delle carte di tipo risorsa
 */
public abstract class ResourceCard extends Card {
	private CardType type = CardType.RESOURCE; // Tipo di carta
	private static boolean isFront;
	private boolean hasCentralSymbol;
	private Object[][] pointsAssignment = {{ 999, "vuoto" }};
	private int number;
	private Corner corner1;
	private Corner corner2;
	private Corner corner3;
	private Corner corner4;
	private static ArrayList<Integer> resourceCardsDeck=new ArrayList<>();
	private boolean isPlaced = false; 
	private static Symbol symbol; 
	private Scanner scanner;

	public ResourceCard () {
		this.scanner = new Scanner(System.in); // Inizializza lo scanner nel costruttore
	}
	
	 /**
     * Restituisce il numero della carta.
     * @return numero della carta
     */
    @Override
	public int getNumber() {
		return number;
	}
    /**
     * Restituisce il numero della carta risorsa.
     * @return numero della carta risorsa
     */
	public int getResourceCardNumber() {
		return this.number;
	}
	/**
     * Aggiunge gli angoli alla carta e assegna la loro posizione.
     * @return lista di angoli della carta
     */
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
    /**
     * Resetta il mazzo delle carte risorsa
     */
	public static void resetResourceCards() {
		resourceCardsDeck =new ArrayList<>();
		boolean mazzoDiCartePersonalizzato = false;
		if (mazzoDiCartePersonalizzato) {
			// Scegli le carte che vuoi che siano disponibili
			resourceCardsDeck.addAll(Arrays.asList(11,12,13,14,15));
		}
		else {
			resourceCardsDeck =new ArrayList<>();
			for (int i=1; i<=40; i++) {
				resourceCardsDeck.add(i);
			}	
		}
	}
	/**
     * Restituisce il mazzo di carte risorsa.
     * @return mazzo di carte risorsa
     */
	public static ArrayList<Integer> getResourceCardsDeck () {
		return resourceCardsDeck;
	}
	/**
	 * metodo per pescare una carta casuale
	 * @return carta risorsa
	 */
	public static ResourceCard drawResourceCard() {
		ResourceCard card = null;
		Random random = new Random();
		if(resourceCardsDeck.size()==0) {return null;}
		
		int index = random.nextInt(resourceCardsDeck.size()); // Posizione nella lista di carte disponibili
		int n = resourceCardsDeck.get(index); // ottendo il numero della carta che si trova in posizione "indiceCasuale"
		resourceCardsDeck.remove(index);
		
		switch(n) {
		case 1:
			card=new ResourceCard1();
			break;
		case 2:
			card=new ResourceCard2();
			break;
		case 3:
			card=new ResourceCard3();
			break;
		case 4:
			card=new ResourceCard4();
			break;
		case 5:
			card=new ResourceCard5();
			break;
		case 6:
			card=new ResourceCard6();
			break;
		case 7:
			card=new ResourceCard7();
			break;
		case 8:
			card=new ResourceCard8();
			break;
		case 9:
			card=new ResourceCard9();
			break;
		case 10:
			card=new ResourceCard10();
			break;
		case 11:
			card=new ResourceCard11();
			break;
		case 12:
			card=new ResourceCard12();
			break;
		case 13:
			card=new ResourceCard13();
			break;
		case 14:
			card=new ResourceCard14();
			break;
		case 15:
			card=new ResourceCard15();
			break;
		case 16:
			card=new ResourceCard16();
			break;
		case 17:
			card=new ResourceCard17();
			break;
		case 18:
			card=new ResourceCard18();
			break;
		case 19:
			card=new ResourceCard19();
			break;
		case 20:
			card=new ResourceCard20();
			break;
		case 21:
			card=new ResourceCard21();
			break;
		case 22:
			card=new ResourceCard22();
			break;
		case 23:
			card=new ResourceCard23();
			break;
		case 24:
			card=new ResourceCard24();
			break;
		case 25:
			card=new ResourceCard25();
			break;
		case 26:
			card=new ResourceCard26();
			break;
		case 27:
			card=new ResourceCard27();
			break;
		case 28:
			card=new ResourceCard28();
			break;
		case 29:
			card=new ResourceCard29();
			break;
		case 30:
			card=new ResourceCard30();
			break;
		case 31:
			card=new ResourceCard31();
			break;
		case 32:
			card=new ResourceCard32();
			break;
		case 33:
			card=new ResourceCard33();
			break;
		case 34:
			card=new ResourceCard34();
			break;
		case 35:
			card=new ResourceCard35();
			break;
		case 36:
			card=new ResourceCard36();
			break;
		case 37:
			card=new ResourceCard37();
			break;
		case 38:
			card=new ResourceCard38();
			break;
		case 39:
			card=new ResourceCard39();
			break;
		case 40:
			card=new ResourceCard40();
			break;
		case 41:
			card=new ResourceCardBackPlant();
			break;
		case 42:
			card=new ResourceCardBackFungi();
			break;
		case 43:
			card=new ResourceCardBackAnimal();
			break;
		case 44:
			card=new ResourceCardBackInsect();
			break;
		default:
			break;
		}
		return card;
	}
	
	/**
     * Restituisce il tipo di carta.
     * @return tipo di carta
     */
    public CardType getType() {
        return type;
    }

    /**
     * Verifica se la carta è posizionata sulla playArea.
     * @return true se la carta è posizionata, altrimenti false
     */
    @Override
    public boolean isPlaced() {
        return isPlaced;
    }

    /**
     * Imposta se la carta è posizionata sulla PlayArea.
     * @param isPlaced boolean che indica se la carta è posizionata sulla playArea
     */
    @Override
	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}
	
	/**
     * Restituisce il simbolo del regno (Kingdom) della carta.
     * @return simbolo del regno
     */
    @Override
    public Symbol getKingdom() {
        return this.symbol;
    }

    /**
     * Verifica se il fronte della carta è visibile.
     * @return true se il fronte è visibile, altrimenti false
     */
    public boolean isFront() {
        return isFront;
    }
    /**
     * Verifica se la carta ha un simbolo centrale.
     * @return true se ha un simbolo centrale, altrimenti false
     */
    @Override
	public boolean hasCentralSymbol() {
		return hasCentralSymbol;
	}
	/*
	@Override
	public Symbol getCentralSymbol() {
		return symbol;
	}
	*/

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
	public String getAbbreviatedCorner(CornerPosition position) {
	    for (Corner corner : addCorners()) {
	        if (corner.getPosition().equals(position)) {
	            switch (corner.getState()) {
	                case NULL:
	                    return "NUL";
	                case EMPTY:
	                    return "EMP";
	                
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
	    return "  "; 
	}
	
	/**
	 * Stampa i dettagli della carta.
	 */
	@Override
	public void printCard() {
		if(this.isFront()) {
			System.out.println("Card_id: RES_"+ getNumber() +", Type: " + getType() + "\tKingdom: " + getKingdom() + "\tScore: " + getStringPointsAssignment());
			System.out.println("Front side : \n");

			System.out.println(getAbbreviatedCorner(CornerPosition.TOP_LEFT) + "\t---\t"+
					getAbbreviatedCorner(CornerPosition.TOP_RIGHT));
			System.out.println(" | \t"+getKingdom().getAbbreviation()+"\t|");

			System.out.println(getAbbreviatedCorner(CornerPosition.BOTTOM_RIGHT) + "\t---\t"+
					getAbbreviatedCorner(CornerPosition.BOTTOM_LEFT)+"\n");	
		}
		else {

			System.out.println("Type: " + getType() + "\tKingdom: " + getKingdom() + "\tScore: " + getStringPointsAssignment());
			System.out.println("Back side :\n");

			System.out.print(getCornerRepresentation(CornerPosition.TOP_LEFT) + "        "+
					getCornerRepresentation(CornerPosition.TOP_RIGHT));
			System.out.println();
			System.out.print("    " + getKingdom() + "    ");
			System.out.println();
			System.out.println(getCornerRepresentation(CornerPosition.BOTTOM_LEFT) + "        "+
					getCornerRepresentation(CornerPosition.BOTTOM_RIGHT));	

		}}
	/*
	public void printCard() {
		if(this.isFront()) {
			System.out.println("Card_id: RES_"+ getNumber() +", Type: " + getType() + "\tKingdom: " + getKingdom() + "\tScore: " + getPoints());
			System.out.println("Front side : \n");

			System.out.print(getCornerRepresentation(CornerPosition.TOP_LEFT) + "        "+
					getCornerRepresentation(CornerPosition.TOP_RIGHT));
			System.out.println();
			System.out.println();


			System.out.println(getCornerRepresentation(CornerPosition.BOTTOM_RIGHT) + "        "+
					getCornerRepresentation(CornerPosition.BOTTOM_LEFT));	
		}
		else {

			System.out.println("Type: " + getType() + "\tKingdom: " + getKingdom() + "\tScore: " + getPoints());
			System.out.println("Back side :\n");

			System.out.print(getCornerRepresentation(CornerPosition.TOP_LEFT) + "        "+
					getCornerRepresentation(CornerPosition.TOP_RIGHT));
			System.out.println();
			System.out.print("    " + getKingdom() + "    ");
			System.out.println();
			System.out.println(getCornerRepresentation(CornerPosition.BOTTOM_LEFT) + "        "+
					getCornerRepresentation(CornerPosition.BOTTOM_RIGHT));	

		}}
		*/
	/**
	 * Mostra un menu che chiede all'utente di selezionare il lato frontale (1) o retro (2) della carta.
	 * @param d La carta di cui scegliere il lato.
	 * @return La carta aggiornata con il lato scelto dall'utente.
	 */
	
	@Override
	public Card ChooseSide(Card d) {
	    ResourceCard card = null;
	    System.out.println("Scegli il fronte o il retro della carta?");
	    System.out.println("1. Fronte");
	    System.out.println("2. Retro");

	    //Scanner scanner = new Scanner(System.in);
	    boolean validChoice = false; 

	    while (!validChoice) {
	        if (scanner.hasNextInt()) { 
	            int choice = scanner.nextInt();
	            scanner.nextLine(); 

	            if (choice == 1 || choice == 2) {
	                validChoice = true; 
	                int val = d.getNumber();
	                System.out.println("carta numero: " + val);
	                switch (choice) {
	                    case 1:
	                        card = (ResourceCard) d;
	                        break;
	                    case 2:
	                        if (val > 0 && val < 11) {
	                            card = (ResourceCard) new ResourceCardBackPlant();
	                        } else if (val > 10 && val < 21) {
	                            card = (ResourceCard) new ResourceCardBackFungi();
	                        } else if (val > 20 && val < 31) {
	                            card = (ResourceCard) new ResourceCardBackAnimal();
	                        } else if (val > 30 && val < 41) {
	                            card = (ResourceCard) new ResourceCardBackInsect();
	                        }
	                        break;
	                    default:
	                        
	                        System.out.println("Scelta non valida. Impostato il fronte");
	                        card = (ResourceCard) d;
	                        break;
	                }
	            } else {
	                System.out.println("Scelta non valida. Inserisci 1 o 2.");
	            }
	        } else {
	            System.out.println("Input non valido. Inserisci un numero.");
	            scanner.next(); 
	        }
	    }

	    //scanner.close(); // Chiudi lo scanner solo alla fine del metodo

	    System.out.println("Hai scelto il " + (card.isFront() ? "fronte" : "retro") + " della carta.");
	    return card;
	}

	public Object[][] getPointsAssignment() {
		return pointsAssignment;
	}
	
	public String getStringPointsAssignment() {
		for (Object[] ii : getPointsAssignment()) {
	    	// Estraggo la regola di assegnamento che ha punteggio diverso da zero
	        if ((int)ii[0] != 0) {// Solo uno dei possibili punti è diverso da zero
	        	if ((String)ii[1]=="niente") {
	        		return "[ "+ii[0]+" ]";
	        	}
	        	return "[ "+ii[0]+", " +(String) ii[1] + " ]";
	        }	
		
		}
		return "[ 0 ]";	
	}
}
