/* PLAYER

Contiene, in linea di massima, tutte le azioni che il player può compiere

	chooseViablePositionOnPlayArea (Card, CornerPosition)
		sceglie una posizione legale per piazzare la data carta, attaccando la data CornerPosition (ad esempio BOT_LEFT)
		in output ci sono le coordinates scelte
		
	addCardToPlayArea (Card, CornerPosition, Coordinates)
		aggiunge la carta sulla PlayArea attaccando il dato CornerPosition alle date coordinate
		Non controlla niente perchè, in teoria, il controllo è fatto da un metodo della PlayArea,
		cioè: validPlacementsForGivenCorner, chiamato dal metodo chooseViablePositionOnPlayArea

	chooseCardToPlay()
		sceglie una tra le 3 carte che il player ha in mano
		
	removeCardFromHand()
		rimuove una carta dalla mano del giocatore

 */



package game;

import java.util.ArrayList;

import java.util.Scanner;
import java.util.Collections;
import goldCard.*;
import initialCard.*;
//import ObjectiveCard;
import resourceCard.*;
import cards.Card;
import cards.CornerPosition;
//import cards.Symbol;
//import game.PlayArea.PlayAreaItem;
import cards.Corner;
import cards.Coordinates;


public class Player {

	private String name;
	private final int id;
	private int points;
	private boolean isFirst;
	private PlayArea playerPlayArea;
	private ArrayList <Card> hand;
	private Card playerObjectiveCard;
	private Scanner scanner;


	public Player(int id, String name) {
		this.id = id;
		this.playerPlayArea = new PlayArea();
		this.name = name;
		this.points = 0;
		this.hand = new ArrayList <Card>();
		this.scanner = new Scanner(System.in); // Inizializza lo scanner nel costruttore
		//this.handResourceCard = new ArrayList<ResourceCard>();
		//this.handGoldCard = new ArrayList <GoldCard> ();
		//this.commonObj = CommonObjectiveCard.assignCommonObjectiveCard();
	}
	
	public int n_PlacedCardPoints(Card playedCard, CornerPosition placedCorner, Coordinates placingCoordinates) {
	    // Leggo i pointsAssignment che contengono le informazioni sui punti da assegnare, e poi
		// a seconda del caso (della "regola di assegnamento") assegno i punti corrispondenti
		int pointsValue = 999;
		String stringRule = null;
		int pointsMultiplier = 999;
		
	    for (Object[] ii : playedCard.getPointsAssignment()) {
	    	// Estraggo la regola di assegnamento che ha punteggio diverso da zero
	        if ((int)ii[0] != 0) {// Solo uno dei possibili punti è diverso da zero
	        	stringRule = (String) ii[1];	// Regola di assegnamento
	        	pointsMultiplier = (int) ii[0];	// Moltiplicatore di punti assegnati
	        	
	            break; // Esci dal ciclo una volta trovato l'elemento
	        }
	        if (stringRule == null) {stringRule = "nessun punto";}
	    }
	    // A seconda della regola stabilita succedono cose
		switch (stringRule) {
		
			case "nessun punto":
				// Non succede niente serve solo per entrare nello switch con un valore non vuoto
			break;
	    	case "niente": // Questo caso aggiunge punti secchi al giocatore
	    		pointsValue = pointsMultiplier;
	    		
	    		//System.out.println("Hai guadagnato " + pointsMultiplier + " punti secchi!");
	    	break;
	    	case "QUILL","MANUSCRIPT","INKWELL": // Questo caso da 1 punto per ogni occorrenza di SpecialSymbol nella PlayArea PRIMA che la carta sia collocata
	    		// conto le occorrenze dello SpecialSymbol in questione nella lista degli specialsymbols presenti sulla playarea
	    		int newpoints = pointsMultiplier * Collections.frequency(playerPlayArea.getSpecialSymbolList(),stringRule);
	    		pointsValue = newpoints;
	    	
	    		System.out.println("Sulla tua PlayArea c'erano "+newpoints+" "+stringRule+" e hai guadagnato altrettanti punti!");
	    	break;
	    	case "coveredCards": // Questo caso da 2 punti per ogni corner che la carta piazzata va a coprire sulla PlayArea
	    		
	    		int n_coveredCorners = 0;
	    		
	    		for (Corner ii_corner : playedCard.addCorners()) { 
	    			// Per ogni corner vado a vedere quali sono le coordinate che va a coprire
	        		int x_relativaCorner = ii_corner.getPosition().getXOffset() - placedCorner.getXOffset();
	        		int y_relativaCorner = ii_corner.getPosition().getYOffset() - placedCorner.getYOffset();
	        		int x_coperta = placingCoordinates.getX() + (x_relativaCorner);
	        		int y_coperta = placingCoordinates.getY() + (y_relativaCorner);
	        		
	        		// poi ciclo sulla PlayArea per vedere se quelle coordinate sono occupate da un corner o meno.
	                for (Coordinates coord : playerPlayArea.getOccupiedCoordinates()) {
	                	// Se sì aggiungo 1 al counter dei coveredCorners
	                    if (coord.getX() == x_coperta && coord.getY() == y_coperta) {
	                    	n_coveredCorners++;
	                    }
	                }       
	    		}	    		
	    		pointsValue = pointsMultiplier * n_coveredCorners;
	    		
	    		System.out.println("Hai coperto "+n_coveredCorners+" angoli e questo ti ha fruttato " + pointsMultiplier * n_coveredCorners + " punti!");
	    		
			break;	
			
			default:
				pointsValue = 999;
			break;
		}	
		return pointsValue;
		//System.out.println("PUNTI DEL GIOCATORE "+getName()+":\t"+points);
	}
	
	public Coordinates choosePositionOnPlayArea (Card card, CornerPosition cornerpos,ArrayList<Coordinates> step1_viableCoordinates) {		
		Coordinates chosenCoordinates = new Coordinates(999,999);
		
		// 1) ------------- Individuazione delle coordinate appropriate. -------------
		//ArrayList<Coordinates> step1_viableCoordinates = playerPlayArea.validPlacementsForGivenCorner(card, cornerpos);
		
		// 2) ------------- Stampo a schermo "quale di queste vuoi scegliere?" -------------
		
		// Mostra le coordinate disponibili all'utente
		boolean validChoice = false; // Flag per controllare la validità della scelta
		while (!validChoice) {
		    System.out.println("Scegli tra le seguenti coordinate:");
		    for (int i = 0; i < step1_viableCoordinates.size(); i++) {
		        System.out.println((i + 1) + ": " + step1_viableCoordinates.get(i));
		    }

		    // Chiedi all'utente di inserire la scelta
		    System.out.print("Inserisci il numero della coordinata che desideri scegliere: ");
		    
		    if (scanner.hasNextInt()) {
		        int choice = scanner.nextInt();

		        // Validazione della scelta
		        if (choice > 0 && choice <= step1_viableCoordinates.size()) {
		            chosenCoordinates = step1_viableCoordinates.get(choice - 1); // Salva la scelta
		            validChoice = true; // Imposta il flag a true per uscire dal ciclo
		            System.out.println("Hai scelto le coordinate: " + chosenCoordinates);
		        } else {
		            System.out.println("Scelta non valida, per favore riprova.");
		        }
		    } else {
		        System.out.println("Input non valido. Inserisci un numero.");
		        scanner.next(); // Consuma il valore non valido
		    }
		}

		
		// 3) ------------- Output funzione -------------
		return chosenCoordinates;
	}
	
	// Metodo che aggiunge una carta alla PlayArea. Dobbiamo scegliere carta, angolo che vogliamo attaccare e posizione sul dove attaccarlo
    public void addCardToPlayArea (Card card, CornerPosition cornerpos, Coordinates coords) {
    	int x_carta; int y_carta;
    	
    	// Inserimento corners
    	for (Corner ii : card.addCorners()) {

    		x_carta = coords.getX() + (ii.getPosition().getXOffset() - cornerpos.getXOffset());
    		y_carta = coords.getY() + (ii.getPosition().getYOffset() - cornerpos.getYOffset());
    		
    		Coordinates placeCoords = new Coordinates(x_carta,y_carta);
    		
    		// Inserisco la carta nella PlayArea nelle coordinate appena calcolate:
    		playerPlayArea.placeCornerOnXYCoordinates(placeCoords,ii,card);
    	}    	
    }

	/**
	 * @return player's Id
	 */
	public int getId() {
		return this.id;
	}

	
	public PlayArea getPlayArea() {
		return playerPlayArea;
	}

	public void setPlayArea(PlayArea playArea) {
		playerPlayArea = playArea;
		System.out.println("Settato il manoscritto del giocatore " + id + ", chiamato " + name);
	}
	
	/**
	 * @return player's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param isFirst
	 */
	public void setIsFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	/**
	 * @return if player is first
	 */
	public boolean isFirst() {
		return isFirst;
	}
	/**
	 * @return player's points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @param commonGoalPoints
	 */
	public void setPoints(int commonGoalPoints) {
		this.points = commonGoalPoints;
	}
	/**
	 * somma i punti inseriti ai punti che il giocatore ha
	 * @param points punti da sommare 
	 */
	public void addPoints(int points) {
		this.points += points;
	}

	public int totalPoints() {
		int total = 0;
		//total += personalGoalPoints();
		total += points;
		//total += playerPlayArea.nearbySymbolsScore();
		return total;
	}

	/*
	 * public ArrayList <ResourceCard> addResourceCardToHand(ResourceCard card) {
		handResourceCard.add(card);
		return handResourceCard;
	}
	public ArrayList <GoldCard> addGoldCardToHand(GoldCard card) {
		handGoldCard.add(card);
		return handGoldCard;
	}
	 */
	public void addCardToHand(Card card) {
		hand.add(card);
	}
	
	// Metodo per scegliere se vuoi visualizzare roba o vuoi giocare una carta
	public void ChooseWhatYouWannaDo (ArrayList<Card> visibleCards, ArrayList<Card> visibleObjectiveCards) {
		boolean wantToPlayCard = false;
		
		do {
			
			// L'utente sceglie cosa vuole fare
			int choice = -1;
		    // Ciclo finché l'utente non inserisce una scelta valida
		    while (choice < 0 || choice > 6) {
		        System.out.println("\nScegli cosa vuoi fare tra:");
		        System.out.println("0 - giocare una carta (esce dal ciclo)");
		        System.out.println("1 - Vedere la PlayArea");
		        System.out.println("2 - Vedere le carte obiettivo comuni");
		        System.out.println("3 - Vedere le carte scoperte sul tavolo che possono essere pescate");
		        System.out.println("4 - Vedere la tua mano");
		        System.out.println("5 - Vedere la carta obiettivo segreta");
		        System.out.println("6 - Testa la tua carta obiettivo");
		        if (scanner.hasNextInt()) {
		            choice = scanner.nextInt();
		            if ((choice < 0 || choice > 6)) {
		                System.out.println("Scelta non valida. Riprova.");
		            }
		        } else {
		            System.out.println("Input non valido. Inserisci un numero.");
		            scanner.next(); // Consuma il valore non valido
		        }
		    }
		    
		    // In base alla scelta faccio cosa l'utente desidera:
		    switch (choice) {
		    	case 0: // Giocare una carta
		    		wantToPlayCard = true;
		    	break;
		    	case 1: // Vedere la PlayArea
		    		playerPlayArea.printPlayAreaGrid();
		    	break;
		    	case 2: // Vedere le carte Obiettivo Comuni
		    		for (Card objcard : visibleObjectiveCards) {objcard.printCard();}
		    	break;
		    	case 3: // Vedere le carte scoperte sul tavolo
		    		for (Card viscard : visibleCards) {viscard.printCard();}
		    	break;
		    	case 4: // Vedere la tua mano
		    		for (Card handcard : this.getHand()) {handcard.printCard();}
		    	break;
		    	case 5: // Vedere la tua carta obiettivo segreta
		    		this.getPlayerObjectiveCard().printCard();
		    	break;
		    	case 6:
		    		System.out.println("Punti presi dall'objective card: "+this.getPlayerObjectiveCard().calculateObjectiveCardPoints(this.getPlayArea()));
		    	break;
		    	default:
		    		System.err.println("Player>ChooseWhatYouWannaDo switch con valore non valido!");
		    	break;
		    	
		    }
		    	
		} while (!wantToPlayCard);
		
		
	}



	/**
	 * Permette al giocatore di scegliere una carta da giocare dalla propria mano.
	 *
	 * @param mano La mano di carte corrente del giocatore (ArrayList).
	 * @return L'oggetto Carta scelto, o null se non � stata selezionata alcuna carta.
	 */
	public Card chooseCardToPlay() {
	    int choice;
	    Card chosenCard = null;
	    boolean visualizza = true;

        do {
        	
        	if (visualizza) {
	            System.out.println("La tua mano:");
	            for (int i = 0; i < this.hand.size(); i++) {
	                Card card = this.hand.get(i);  
	                System.out.println((i + 1) + ": ");
	                card.printCard();
	                System.out.println();
	            }
        	}
            System.out.print("Scegli una carta: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Inserisci un numero valido.");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > this.hand.size()); 

        chosenCard = this.hand.get(choice - 1);
        System.out.println("Hai scelto: " + chosenCard.getNumber());

	    return chosenCard; 
	}

	public CornerPosition choosePosition() {
	    int choice = -1;

	    // Stampa le opzioni
	    System.out.println("Scegli una posizione:");
	    System.out.println("0 - BOT_LEFT");
	    System.out.println("1 - BOT_RIGHT");
	    System.out.println("2 - TOP_RIGHT");
	    System.out.println("3 - TOP_LEFT");

	    // Ciclo finché l'utente non inserisce una scelta valida
	    while (choice < 0 || choice > 3) {
	        System.out.print("Inserisci il numero della posizione: ");
	        if (scanner.hasNextInt()) {
	            choice = scanner.nextInt();
	            if ((choice < 0 || choice > 3)) {
	                System.out.println("Scelta non valida. Riprova.");
	            }
	        } else {
	            System.out.println("Input non valido. Inserisci un numero.");
	            scanner.next(); // Consuma il valore non valido
	        }
	    }

	    // Restituisce la posizione corrispondente
	    switch (choice) {
	        case 0:
	            return CornerPosition.BOTTOM_LEFT;
	        case 1:
	            return CornerPosition.BOTTOM_RIGHT;
	        case 2:
	            return CornerPosition.TOP_RIGHT;
	        case 3:
	            return CornerPosition.TOP_LEFT;
	        default:
	            return null; // Non dovrebbe mai accadere
	    }
	}


	/**
	 * Allows the player to choose a card to take from the visible cards.
	 *
	 * @param visibleCards The list of visible cards available.
	 * 
	 * @return The chosen Card object. If the player doesn't choose any of the visible 
	 *         cards, a new resource or gold card (depending on their choice) is drawn
	 *         and returned. Returns null only if the player enters invalid input for
	 *         the card type.
	 */
	public Card chooseCardToTake(ArrayList <Card> visibleCards) {
		

	    int choice;
	    Card chosenCard = null;
	    boolean visualizza = true;

        do {
        	
        	if (visualizza) {
	            System.out.println("Le carte sul tavolo:");
	            int i = 1;
	            for (Card card : visibleCards) {
	            	System.out.println("Scelta " + i + ": ");
	                card.printCard();
	                System.out.println();
	                i++;
	            }
        	}
            System.out.print("Scegli una carta: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Inserisci un numero valido.");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > visibleCards.size()); 

        chosenCard = visibleCards.get(choice - 1);
        System.out.println("Hai scelto: " + chosenCard.getNumber());

	    return chosenCard; 
		
		/*
		Card cardTaken = null;
		boolean choosen;
		boolean secondchoice;
		Scanner scanner = new Scanner (System.in);
		Scanner in = new Scanner (System.in);
		System.out.println("Le carte visibili sono: ");
		for(Card c: visibleCards) {
				c.printCard();
		}
		System.out.println("Vuoi pescare una tra queste carte?");
		choosen = yesorNoInput(scanner);
		if (choosen) {
			System.out.println("Che tipo di carta vorresti? Oro oppure Risorsa?");
			String typeCard = scanner.next();
			for( Card card : visibleCards ) {
				if (typeCard.equals("risorsa") || typeCard.equals("Risorsa") || typeCard.equals("RISORSA")) {
					if (card instanceof ResourceCard) {
						card.printCard();
						System.out.println("Vuoi questa carta? ");
						secondchoice = yesorNoInput(scanner);
						if (secondchoice) {
							cardTaken = card;
							break;
						}
						else
							continue;
					}

				}
				else if(typeCard.equals("gold") || typeCard.equals("Gold") || typeCard.equals("GOLD")) {
					if (card instanceof GoldCard) {
						card.printCard();
						System.out.println("Vuoi questa carta? ");
						secondchoice = yesorNoInput(scanner);
						if (secondchoice) {
							cardTaken = card;
							break;
						}
						else
							continue;
					}
				}

			}
		}
		else{
			System.out.println("Che tipo di carta vorresti? Oro oppure Risorsa?");
			String typeCard = scanner.next();
			if (typeCard.equals("risorsa") || typeCard.equals("Risorsa") || typeCard.equals("RISORSA")) {
				cardTaken = ResourceCard.drawResourceCard();
			}
			else if(typeCard.equals("gold") || typeCard.equals("Gold") || typeCard.equals("GOLD")) {}
			cardTaken = GoldCard.drawGoldCard();
		}
		in.close();
		scanner.close();
		return cardTaken;
		*/
	}

	private boolean yesorNoInput(Scanner scanner) {
		String answer;
		boolean choosen;
		answer = scanner.next();
		while (!answer.startsWith("s") && !answer.startsWith("S") && !answer.startsWith("N") && !answer.startsWith("n")) {
			System.out.println("La risposta non � corretta. Rispondi si o no!'");
			answer = scanner.next();
		}
		choosen = answer.startsWith("s") || answer.startsWith("S");
		return choosen;
	}
	
	
	/**
	 * @return print player's Manuscript
	 */
	public String printPlayArea() {
		return playerPlayArea.toString();
	}
	
	
	 public void chooseOrientationAndPlaceInitialCard(InitialCard initialCard) {
		 
		 InitialCard backInitialCard;
	
		System.out.println("\nFronte della initial card:\n");
		initialCard.printCard();
		System.out.println("\nRetro della initial card:\n");
		initialCard.createCardForBack(initialCard.getNumber(), initialCard).printCard();
		backInitialCard = (InitialCard) initialCard.ChooseSide(initialCard);
		playerPlayArea.placeInitialCard(backInitialCard);
	}
	
	 // Metodo per rimuovere una carta dalla mano del giocatore
	public void removeCardFromHand(Card card) {
		this.hand.remove(card);	
	}
	
	public ArrayList <Card> getHand(){
		return this.hand;
	}
	
	public Card getPlayerObjectiveCard() {
		return playerObjectiveCard;
	}
	
	public void setPlayerObjectiveCard(Card playerObjectiveCard) {
		this.playerObjectiveCard = playerObjectiveCard;
	}
}




