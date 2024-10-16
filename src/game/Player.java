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

import initialCard.*;
//import ObjectiveCard;

import cards.Card;
import cards.CornerPosition;
import cards.SpecialSymbol;
//import cards.Symbol;
//import game.PlayArea.PlayAreaItem;
import cards.Corner;
import cards.Coordinates;

/**
 * La classe Player rappresenta un giocatore in un gioco. 
 * Gestisce le azioni che il giocatore può compiere come scegliere una carta, 
 * piazzare una carta nella PlayArea, ottenere punti e altre funzionalità correlate.
 * 
 * Contiene metodi per:
 * - Scegliere una carta dalla mano
 * - Piazzare una carta nella PlayArea
 * - Gestire i punti e le carte obiettivo
 * - Interagire con le carte visibili sul tavolo
 * - Aggiungere e rimuovere carte dalla mano del giocatore
 
 */
public class Player {

	private String name;
	private final int id;
	private int points;
	private int achievedObjectiveCards;
	private boolean isFirst;
	private PlayArea playerPlayArea;
	private ArrayList <Card> hand;
	private Card playerObjectiveCard;
	private Scanner scanner;

	/**
     * Costruttore della classe Player.
     * 
     * @param id   Identificativo unico del giocatore
     * @param name Nome del giocatore
     */
	
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
	/**
     * Calcola i punti guadagnati da una carta piazzata sulla PlayArea.
     * 
     * @param playedCard La carta giocata
     * @param placedCorner La posizione dell'angolo della carta
     * @param placingCoordinates Le coordinate in cui la carta è piazzata
     * @return I punti guadagnati
     */
	public int n_PlacedCardPoints(Card playedCard, CornerPosition placedCorner, Coordinates placingCoordinates) {
	    // Leggo i pointsAssignment che contengono le informazioni sui punti da assegnare, e poi
		// a seconda del caso (della "regola di assegnamento") assegno i punti corrispondenti
		int pointsValue = 999;
		String stringRule = null;
		int pointsMultiplier = 999;
		
	    for (Object[] coppiaPuntoRegola : playedCard.getPointsAssignment()) {
	    	// Estraggo la regola di assegnamento che ha punteggio diverso da zero
	        if ((int)coppiaPuntoRegola[0] != 0) {// Solo uno dei possibili punti è diverso da zero
	        	stringRule = (String) coppiaPuntoRegola[1];	// Regola di assegnamento
	        	pointsMultiplier = (int) coppiaPuntoRegola[0];	// Moltiplicatore di punti assegnati
	        	
	            break; // Esci dal ciclo una volta trovato l'elemento
	        }
	        if (stringRule == null) {stringRule = "nessun punto"; pointsMultiplier = 0;}
	    }
	    // A seconda della regola stabilita succedono cose
		switch (stringRule) {
		
			case "nessun punto":
				// Non succede niente serve solo per entrare nello switch con un valore non vuoto	
				pointsValue = pointsMultiplier;
			break;
	    	case "niente": // Questo caso aggiunge punti secchi al giocatore
	    		pointsValue = pointsMultiplier;
	    		
	    		//System.out.println("Hai guadagnato " + pointsMultiplier + " punti secchi!");
	    	break;
	    	case "QUILL","MANUSCRIPT","INKWELL": // Questo caso da 1 punto per ogni occorrenza di SpecialSymbol nella PlayArea 
	    		// PRIMA che la carta sia collocata
	    		// conto quante volte lo SpecialSymbol in questione compare nella PlayARea
	    		ArrayList<String> tempList = new ArrayList<>();
	    		for (SpecialSymbol item : this.getPlayArea().getSpecialSymbolList()) {
	    			tempList.add(item.toString());
	    		}
	    	
	    		pointsValue = pointsMultiplier * Collections.frequency(tempList,stringRule);
	    	
	    		System.out.println("Sulla tua PlayArea c'erano "+pointsValue+" "+stringRule+" e hai guadagnato altrettanti punti!");
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
				System.err.println("Non sei entrato in nessuna stringrule valida");
				pointsValue = 999;
			break;
		}	
		return pointsValue;
		//System.out.println("PUNTI DEL GIOCATORE "+getName()+":\t"+points);
	}
	/**
     * Consente al giocatore di scegliere una posizione valida sulla PlayArea per piazzare una carta.
     * 
     * @param card   La carta da piazzare
     * @param cornerpos  La posizione dell'angolo che verrà attaccato
     * @param validCoordinates Una lista di coordinate valide per il posizionamento
     * @return Le coordinate scelte per piazzare la carta
     */
	public Coordinates choosePositionOnPlayArea (Card card, CornerPosition cornerpos,ArrayList<Coordinates> validCoordinates) {		
		Coordinates chosenCoordinates = new Coordinates(999,999);
		
		// 1) ------------- Individuazione delle coordinate appropriate. -------------
		//ArrayList<Coordinates> validCoordinates = playerPlayArea.validPlacementsForGivenCorner(card, cornerpos);
		
		// 2) ------------- Stampo a schermo "quale di queste vuoi scegliere?" -------------
		
		// Mostra le coordinate disponibili all'utente
		boolean validChoice = false; // Flag per controllare la validità della scelta
		while (!validChoice) {
		    System.out.println("Scegli tra le seguenti coordinate:");
		    for (int i = 0; i < validCoordinates.size(); i++) {
		        System.out.println((i + 1) + ": " + validCoordinates.get(i));
		    }

		    // Chiedi all'utente di inserire la scelta
		    System.out.print("Inserisci il numero della coordinata che desideri scegliere: ");
		    
		    if (scanner.hasNextInt()) {
		        int choice = scanner.nextInt();

		        // Validazione della scelta
		        if (choice > 0 && choice <= validCoordinates.size()) {
		            chosenCoordinates = validCoordinates.get(choice - 1); // Salva la scelta
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
	 /**
     * Aggiunge una carta alla PlayArea del giocatore.
     * 
     * @param card       La carta da aggiungere
     * @param cornerpos  La posizione dell'angolo della carta che viene attaccato
     * @param coords     Le coordinate in cui piazzare la carta
     */
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
     * Restituisce l'ID del giocatore.
     * 
     * @return L'ID del giocatore
     */
	public int getId() {
		return this.id;
	}

	 /**
     * Restituisce la PlayArea del giocatore.
     * 
     * @return La PlayArea del giocatore
     */
	public PlayArea getPlayArea() {
		return playerPlayArea;
	}
	/**
     * Imposta la PlayArea del giocatore.
     * 
     * @param playArea La PlayArea da assegnare al giocatore
     */
	public void setPlayArea(PlayArea playArea) {
		playerPlayArea = playArea;
		System.out.println("Settato il manoscritto del giocatore " + id + ", chiamato " + name);
	}
	
	/**
     * Restituisce il nome del giocatore.
     * 
     * @return Il nome del giocatore
     */
	public String getName() {
		return this.name;
	}

	/**
     * Imposta se il giocatore è il primo a giocare.
     * 
     * @param isFirst Indica se il giocatore è il primo
     */
	public void setIsFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	/**
     * Verifica se il giocatore è il primo.
     * 
     * @return true se il giocatore è il primo, false altrimenti
     */
	public boolean isFirst() {
		return isFirst;
	}
	/**
     * Restituisce i punti del giocatore.
     * 
     * @return I punti attuali del giocatore
     */
	public int getPoints() {
		return points;
	}
	
	/**
	 * somma i punti inseriti ai punti che il giocatore possiede
	 * @param points punti da sommare 
	 */
	public void addPoints(int points) {
		this.points += points;
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
	/**
     * Aggiunge una carta alla mano del giocatore.
     * 
     * @param card La carta da aggiungere alla mano
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
		        System.out.println("0 - giocare una carta");
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
		    		System.out.println("\n");
		    		System.out.println("Risorse disponibili: " + this.getPlayArea().getSymbolList());		    		
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
     * Permette al giocatore di scegliere una carta dalla mano.
     * 
     * @return La carta scelta dalla mano
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
	/**
     * Permette al giocatore di scegliere una posizione (angolo) per attaccare una carta.
     * 
     * @return La posizione (CornerPosition) scelta
     */
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
     * Permette al giocatore di scegliere una carta da prendere tra quelle visibili.
     * 
     * @param visibleCards Una lista di carte visibili disponibili
     * @return La carta scelta
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
	/*
	 * private boolean yesorNoInput(Scanner scanner) {
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
	 */

	
	
	/**
     * Stampa la PlayArea del giocatore.
     * 
     * @return La rappresentazione in stringa della PlayArea del giocatore
     */
	public String printPlayArea() {
		return playerPlayArea.toString();
	}
	
	/**
     * Permette al giocatore di scegliere e piazzare una carta iniziale nella PlayArea.
     * 
     * @param initialCard La carta iniziale da piazzare
     */
	 public void chooseOrientationAndPlaceInitialCard(InitialCard initialCard) {
		 
		 InitialCard backInitialCard;
	
		System.out.println("\nFronte della initial card:\n");
		initialCard.printCard();
		System.out.println("\nRetro della initial card:\n");
		initialCard.createCardForBack(initialCard.getNumber(), initialCard).printCard();
		backInitialCard = (InitialCard) initialCard.ChooseSide(initialCard);
		playerPlayArea.placeInitialCard(backInitialCard);
	}
	
	 
	 /**
	     * Rimuove una carta dalla mano del giocatore.
	     * 
	     * @param card La carta da rimuovere
	     */
	 // Metodo per rimuovere una carta dalla mano del giocatore
	public void removeCardFromHand(Card card) {
		this.hand.remove(card);	
	}
	/**
     * Restituisce la mano del giocatore.
     * 
     * @return La mano del giocatore come lista di carte
     */
	public ArrayList <Card> getHand(){
		return this.hand;
	}
	/**
     * Restituisce la carta obiettivo personale del giocatore.
     * 
     * @return La carta obiettivo del giocatore
     */
	public Card getPlayerObjectiveCard() {
		return playerObjectiveCard;
	}
	 /**
     * Imposta la carta obiettivo personale del giocatore.
     * 
     * @param playerObjectiveCard La carta obiettivo da assegnare
     */
	public void setPlayerObjectiveCard(Card playerObjectiveCard) {
		this.playerObjectiveCard = playerObjectiveCard;
	}
	/**
     * Restituisce il numero di carte obiettivo raggiunte dal giocatore.
     * 
     * @return Il numero di carte obiettivo raggiunte
     */
	public int getAchievedObjectiveCards() {
		return achievedObjectiveCards;
	}
	/**
     * Somma al totale il numero di carte obiettivo raggiunte dal giocatore.
     * 
     * @param num Il numero di carte da aggiungere
     */
	public void addAchievedObjectiveCards(int num) {
		this.achievedObjectiveCards =+ num;
	}
}




