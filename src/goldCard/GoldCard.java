package goldCard;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import cards.*;
/**
 * Classe astratta che rappresenta una carta d'oro nel gioco.
 * Questa classe estende la classe Card e fornisce funzionalità specifiche per le carte d'oro.
 */

public abstract class GoldCard extends Card {

	private CardType type = CardType.GOLD;
	private static boolean isFront;
	private boolean isPlaced = false;
	private boolean hasCentralSymbol;
	private Object[][] pointsAssignment = {{ 999, "vuoto" }};
	private int number;
	private Symbol kingdom;
	private SpecialSymbol specialSymbol;
	private Corner corner1;
	private Corner corner2;
	private Corner corner3;
	private Corner corner4;
	private static ArrayList<Integer> goldCardsDeck= new ArrayList<>();
	private Scanner scanner;
	
	public GoldCard () {
		this.scanner = new Scanner(System.in); // Inizializza lo scanner nel costruttore
	}
	
	/**
     * Aggiunge i angoli alla carta e imposta la loro posizione.
     * 
     * @return Un ArrayList contenente i quattro angoli della carta.
     */
	public ArrayList<Corner> addCorners(){
		ArrayList<Corner> corners = new ArrayList<>();
		corners.add(corner1);
		corners.add(corner2);
		corners.add(corner3);
		corners.add(corner4);
		for(int i = 0; i <corners.size(); i++) {
			corners.get(i).setPosition(CornerPosition.values()[i]);
		}
		return corners;
	}
	
	/**
     * Crea i requisiti per i punti della carta.
     * 
     * @return Un ArrayList di simboli che rappresentano i requisiti.
     */
    @Override
	public ArrayList<Symbol> createRequirementsForPoints() {
		ArrayList<Symbol> requirements = new ArrayList<>();
		return requirements;
	}

    /**
     * Resetta il mazzo di carte d'oro.
     * riempie il mazzo con carte da 1 a 40.
     */
	public static void resetGoldCards() {
		goldCardsDeck =new ArrayList<>();
		boolean mazzoDiCartePersonalizzato = false; // Serve per scegliere, eventualmente, un mazzo di carte personalizzato per il debug
		if (mazzoDiCartePersonalizzato) {
			// Scegli le carte che vuoi che siano disponibili
			goldCardsDeck.addAll(Arrays.asList(11,12,13,14,15));
		}
		else {
			goldCardsDeck =new ArrayList<>();
			for (int i=1; i<=40; i++) {
				goldCardsDeck.add(i);
			}	
		}
	}
	
	/**
     * Ottiene il mazzo di carte d'oro attuale.
     * 
     * @return Un ArrayList di interi rappresentanti le carte d'oro nel mazzo.
     */
    public static ArrayList<Integer> getGoldCardsDeck() {
        return goldCardsDeck;
    }

    /**
     * Ottiene il tipo di carta.
     * 
     * @return Il tipo di carta.
     */	public CardType getType() {
		return type;
	}

     /**
      * Verifica se la carta è sul fronte.
      * 
      * @return {@code true} se la carta è sul fronte, altrimenti {@code false}.
      */
     public boolean isFront() {
         return isFront;
     }

     /**
      * Verifica se la carta è stata posizionata.
      * 
      * @return true se la carta è posizionata, altrimenti {@code false}.
      */
     @Override
     public boolean isPlaced() {
         return isPlaced;
     }

     /**
      * Ottiene il numero della carta.
      * 
      * @return Il numero della carta.
      */
     public int getNumber() {
         return number;
     }
     
     /**
      * Ottiene il simbolo del regno associato alla carta.
      * 
      * @return Il simbolo del regno.
      */
     @Override
     public Symbol getKingdom() {
         return kingdom;
     }

     /**
      * Ottiene il simbolo speciale associato alla carta.
      * 
      * @return Il simbolo speciale.
      */
     public SpecialSymbol getSpecialSymbol() {
         return specialSymbol;
     }



     /**
      * Imposta se la carta è stata posizionata.
      * 
      * @param isPlaced true se la carta è posizionata, altrimenti {@code false}.
      */
     @Override
     public void setPlaced(boolean isPlaced) {
         this.isPlaced = isPlaced;
     }

     /**
      * Imposta il simbolo del regno per la carta.
      * 
      * @param kingdom Il simbolo del regno da impostare.
      */
     public void setKingdom(Symbol kingdom) {
         this.kingdom = kingdom;
     }

     /**
      * Verifica se la carta ha un simbolo centrale.
      * 
      * @return true se la carta ha un simbolo centrale, altrimenti {@code false}.
      */
     @Override
     public boolean hasCentralSymbol() {
         return hasCentralSymbol;
     }

     /**
      * Estrae una carta d'oro casuale dal mazzo di carte d'oro.
      * 
      * @return La carta d'oro estratta, oppure null se il mazzo è vuoto.
      */

	public static GoldCard drawGoldCard() {
		GoldCard card = null;
		Random random = new Random();
		if(goldCardsDeck.size()==0) {return null;}
		
		int index = random.nextInt(goldCardsDeck.size()); // Posizione nella lista di carte disponibili
		int n = goldCardsDeck.get(index); // ottendo il numero della carta che si trova in posizione "indiceCasuale"
		goldCardsDeck.remove(index);
		
		switch(n) {
		case 1:
			card=new GoldCard1();
			break;
		case 2:
			card=new GoldCard2();
			break;
		case 3:
			card=new GoldCard3();
			break;
		case 4:
			card=new GoldCard4();
			break;
		case 5:
			card=new GoldCard5();
			break;
		case 6:
			card=new GoldCard6();
			break;
		case 7:
			card=new GoldCard7();
			break;
		case 8:
			card=new GoldCard8();
			break;
		case 9:
			card=new GoldCard9();
			break;
		case 10:
			card=new GoldCard10();
			break;
		case 11:
			card=new GoldCard11();
			break;
		case 12:
			card=new GoldCard12();
			break;
		case 13:
			card=new GoldCard13();
			break;
		case 14:
			card=new GoldCard14();
			break;
		case 15:
			card=new GoldCard15();
			break;
		case 16:
			card=new GoldCard16();
			break;
		case 17:
			card=new GoldCard17();
			break;
		case 18:
			card=new GoldCard18();
			break;
		case 19:
			card=new GoldCard19();
			break;
		case 20:
			card=new GoldCard20();
			break;
		case 21:
			card=new GoldCard21();
			break;
		case 22:
			card=new GoldCard22();
			break;
		case 23:
			card=new GoldCard23();
			break;
		case 24:
			card=new GoldCard24();
			break;
		case 25:
			card=new GoldCard25();
			break;
		case 26:
			card=new GoldCard26();
			break;
		case 27:
			card=new GoldCard27();
			break;
		case 28:
			card=new GoldCard28();
			break;
		case 29:
			card=new GoldCard29();
			break;
		case 30:
			card=new GoldCard30();
			break;
		case 31:
			card=new GoldCard31();
			break;
		case 32:
			card=new GoldCard32();
			break;
		case 33:
			card=new GoldCard33();
			break;
		case 34:
			card=new GoldCard34();
			break;
		case 35:
			card=new GoldCard35();
			break;
		case 36:
			card=new GoldCard36();
			break;
		case 37:
			card=new GoldCard37();
			break;
		case 38:
			card=new GoldCard38();
			break;
		case 39:
			card=new GoldCard39();
			break;
		case 40:
			card=new GoldCard40();
			break;
		case 41:
			card=new GoldCardBackFungi();
			break;
		case 42:
			card=new GoldCardBackAnimal();
			break;
		case 43:
			card=new GoldCardBackPlant();
			break;
		case 44:
			card=new GoldCardBackInsect();
			break;
		}
		return card;
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
	    return "  "; // Angolo non trovato (non dovrebbe accadere)
	}
	
	
	/**
	 * Restituisce la rappresentazione abbreviata di un simbolo nel corner.
	 * @param position La posizione dell'angolo da cui recuperare l'abbreviazione.
	 * @return Una stringa che rappresenta lo stato abbreviato o il simbolo dell'angolo, oppure uno spazio ("  ") se l'angolo non è trovato.
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
	/**
	 * Stampa i dettagli della carta.
	 * 
	 * Questo metodo visualizza il tipo della carta, il lato (fronte o retro),
	 * i simboli degli angoli e, se presenti, i simboli centrali di quel lato, insieme al punteggio della carta.
	 * Se la carta non ha simboli centrali, viene stampata una riga vuota per mantenere l'altezza uniforme.
	 */
	@Override
	public void printCard() {
		if(this.isFront())
			{
				System.out.println("Card_id: GOL_"+ getNumber() +", Type: " + getType() + "\tKingdom: " + getKingdom()+"\tScore: " + getStringPointsAssignment());
				System.out.println("Requirement " + createRequirementsForPoints());
				System.out.println("Front side :\n");

				System.out.println(getAbbreviatedCorner(CornerPosition.TOP_LEFT) + "\t---\t"+
						getAbbreviatedCorner(CornerPosition.TOP_RIGHT));
				System.out.println(" | \t"+getKingdom().getAbbreviation()+"\t|");

				System.out.println(getAbbreviatedCorner(CornerPosition.BOTTOM_RIGHT) + "\t---\t"+
						getAbbreviatedCorner(CornerPosition.BOTTOM_LEFT));	
			}else {
				System.out.println("Type: " + getType() + "\tKingdom: " + getKingdom());
				System.out.println("Requirement " + createRequirementsForPoints());
				System.out.println("Back side :\n");

				System.out.print(getCornerRepresentation(CornerPosition.TOP_LEFT) + "        "+
					getCornerRepresentation(CornerPosition.TOP_RIGHT));
				System.out.println("\n\n    " + getKingdom() + "    \n");
				System.out.println(getCornerRepresentation(CornerPosition.BOTTOM_RIGHT) + "        "+
					getCornerRepresentation(CornerPosition.BOTTOM_LEFT)+"\n");
		}
	}
	/**
	 * Mostra un menu che chiede all'utente di selezionare il lato della carta, fronte (1) o retro (2).
	 * @param d La carta su cui scegliere il lato.
	 * @return La carta con il lato scelto dall'utente.
	 */
	@Override
	public Card ChooseSide(Card d) {
	    GoldCard card = null;
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
	                switch (choice) {
	                    case 1:
	                        card = (GoldCard) d;
	                        break;
	                    case 2:
	                        if (val > 0 && val < 11) {
	                            card = new GoldCardBackFungi();
	                        } else if (val > 10 && val < 21) {
	                            card = new GoldCardBackAnimal();
	                        } else if (val > 20 && val < 31) {
	                            card = new GoldCardBackPlant();
	                        } else if (val > 30 && val < 41) {
	                            card = new GoldCardBackInsect();
	                        }
	                        break;
	                    default:
	                        
	                        System.out.println("Scelta non valida. Impostato il fronte");
	                        card = (GoldCard) d;
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

	    //scanner.close(); 

	    System.out.println("Hai scelto " + (card.isFront() ? "fronte" : "retro")+ " della carta.");
	    return card;
	}

	/**
	 * Restituisce l'assegnazione dei punti della carta.
	 * 
	 * @return Una matrice di oggetti che rappresenta i punti assegnati alla carta.
	 */
	public Object[][] getPointsAssignment() {
		return pointsAssignment;
	}
	/**
	 * Restituisce una rappresentazione testuale dell'assegnazione dei punti della carta.
	 * @return Una stringa che rappresenta l'assegnazione dei punti.
	 */
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
