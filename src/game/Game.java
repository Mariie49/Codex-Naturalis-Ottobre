/* GAME
	
	startGame()
		Fa un botto di roba, anche troppa:
		1) Scelta di numero giocatori
		2) Pesca e piazza 4 carte sul tavolo visibili a tutti. 2 Resource e 2 Oro, TODO aggiungere le carte obiettivo.
		3) ogni giocatore:
			3.1) Pesca 2 carte risorsa e una oro
			3.2) Pesca carta Iniziale
			3.3) Sceglie fronte e retro di carta iniziale
			3.4) Colloca carta iniziale su PlayArea
		4) Mischia la lista dei giocatori
		
	turn(Player currentPlayer) WORK IN PROGRESS <<<<<<<<<<<<<<
		Il giocatore corrente:
		1)
			
 */


package game;
import resourceCard.*;
import cards.*;
import goldCard.*;
import initialCard.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;



public class Game {

	private ArrayList<Player> playerList;
	private ArrayList<Integer> objectiveCardDeck = new ArrayList<>();
	//private ArrayList<ObjectiveCard> commonObjCard;
	private ArrayList<Card> visibleCards;
	private ArrayList<Card> visibleObjectiveCards = new ArrayList<>();
	private int[] playerScores;
	private int[] playerTurnsPlayed;
	private int numPlayers;
	private int currentTurn = 0;
	private boolean lastRound  = false;
	private int maxTurns;
	Random rand = new Random();
	private final Scanner scanner = new Scanner(System.in);


	public ArrayList<Player> getPlayerList() {
		return playerList;
	}



	public Game() {
		// Inizializzo la lista dei giocatori e delle loro playareas
		playerList = new ArrayList<Player>();
		
		GoldCard.resetGoldCards();
		ResourceCard.resetResourceCards();
		InitialCard.resetInirialCards();
		//ObjectiveCard.resetObjectiveCards;
		
		visibleCards = new ArrayList<Card>();
		currentTurn = 0;
		maxTurns = 0;
		//matchBoard = new Board();

	}

	public void startGame() {	
		// Inizializza il deck delle ObjectiveCards:
        for (int i = 1; i <= 16; i++) {objectiveCardDeck.add(i);}
        
		// ------------ Scelta numero di giocatori ------------
		boolean bypass_numeroGiocatori = true; // bypass scelta giocatori
		
		if (!bypass_numeroGiocatori) {
			String name;
			numPlayers = 0; //variabile che conta il numero di giocatori
			do {
	            System.out.print("Quanti giocatori parteciperanno (da 2 a 4)?");
	            numPlayers = scanner.nextInt();
	            if (numPlayers < 2 || numPlayers > 4) {
	            	System.out.print("Numero di giocatori non valido\n");
	            }
	        } while (numPlayers < 2 || numPlayers > 4);			
			int ii = 1;
			do {
				System.out.println("Inserisci il nome del giocatore: " + (ii));
				name = scanner.next();
				boolean isDuplicated = false;
				for (Player p : playerList) {
					if (p.getName().equalsIgnoreCase(name)) { 
						isDuplicated = true;
						break; 
					}
				}
				if (isDuplicated) {
					System.out.println("Nome gi� utilizzato. Per favore, inserisci un nome diverso.");
				} else {
					playerList.add(new Player(ii, name));
					ii++;
				}
			} while ( ii < numPlayers+1);
		}
		else {
			numPlayers = 1;
			playerList.add(new Player(1, "zio Adolfo"));
		}
		
		// ------------ Stampa i nomi dei giocatori ------------
		System.out.print("I giocatori sono: " + " ");
		for (Player s : playerList) {
			System.out.print(s.getName() + " ");
		}
		System.out.println();
		// ------------ ------------
		
		playerScores = new int[numPlayers + 1]; // inizializzazione punteggi
		playerTurnsPlayed = new int[numPlayers + 1];
		
		// ------------ Colloca 4 carte sul tavolo ------------
		for (int i = 0; i < 2; i++) {
			
			// Due carte risorsa visibili
			ResourceCard visibleResourceCard = ResourceCard.drawResourceCard();
			if (visibleResourceCard == null) {
			    System.err.println("Attenzione: la carta risorsa estratta è null!");
			} else {
			    visibleCards.add(visibleResourceCard);
			}

			// Due carte oro visibili
			GoldCard visibleGoldCard = GoldCard.drawGoldCard();
			if (visibleGoldCard == null) {
			    System.err.println("Attenzione: la carta oro estratta è null!");
			} else {
			    visibleCards.add(visibleGoldCard);
			}	

			// due carte obiettivo comuni
			int randomIndex = rand.nextInt(objectiveCardDeck.size()-1)+1; // Numero ObjCard che pesco
			objectiveCardDeck.remove(randomIndex); // Tolgo il numero dalla lista (la carta dal Deck)
			System.out.println("Pesco la ObjectiveCard numero: "+randomIndex);
			ObjectiveCard visibleObjectiveCard = new ObjectiveCard(randomIndex); // Costruisco l'ObjectiveCard
			visibleObjectiveCards.add(visibleObjectiveCard); // La aggiungo alla lista di carte visibili
		}

		// ------------ Stampa le carte presenti sul tavolo ------------
		System.out.println("Ecco le prime carte disponibili sul tavolo: 2 carte oro e 2 carte risorsa. ");
		for (Card r : visibleCards) {
			System.out.println();
			r.printCard();
			System.out.println();
		}
		for (Card r : visibleObjectiveCards) {
			r.printCard();
			System.out.println();
		}
		
		// ------------ Inizializza le cose per ogni giocatore ------------
		for (Player player : playerList) {
			
			// PlayArea
			PlayArea matchManuscript = new PlayArea();
			player.setPlayArea(matchManuscript);
			
			System.out.println(" " + player.getName());
			System.out.println("Pesco 2 carte risorsa e una carta oro. ");
			
			// Carte risorsa
			for (int i = 0; i < 2; i++) {
				Card resourceHandCard = ResourceCard.drawResourceCard();
				if (resourceHandCard == null) {
				    System.err.println("Attenzione: la carta risorsa estratta è null!");
				} else {
					player.addCardToHand(resourceHandCard);
				}				
				resourceHandCard.printCard();				
			}

			// Carta oro
			Card goldHandCard = GoldCard.drawGoldCard();
			if (goldHandCard == null) {
			    System.err.println("Attenzione: la carta Gold estratta è null! Game>Startgame");
			} else {
				player.addCardToHand(goldHandCard);
			}				
			goldHandCard.printCard();
			
			// Carta obiettivo segreta - gestione nuova rispetto alle altre:
			
						
			int randomIndex = 1; //rand.nextInt(objectiveCardDeck.size()-1)+1; // Numero ObjCard che pesco
			
			
			//System.out.println("Estratta carta obiettivo numero:" + randomIndex);
			//objectiveCardDeck.remove(randomIndex); // Tolgo il numero dalla lista (la carta dal Deck)			
			ObjectiveCard playerObjectiveCard = new ObjectiveCard(randomIndex); // Costruisco l'ObjectiveCard
			player.setPlayerObjectiveCard(playerObjectiveCard); // La do al giocatore		
			player.getPlayerObjectiveCard().printCard(); // Gliela mostro
			
			// Initial Card
			InitialCard initialHandCard = InitialCard.drawInitialCard(); // Pesca la initial card (pesca solo il fronte)
			player.chooseOrientationAndPlaceInitialCard(initialHandCard); // la orienta e la mette sulla sua PlayArea
		}

		Collections.shuffle(playerList);
		playerList.get(0).setIsFirst(true);
	}

	public boolean turn (Player currentPlayer) {
		Card chosenCard = null;
		Card chosenCardSide = null;
		CornerPosition chosenCornerPosition = null;
		Coordinates chosenCoordinates = null;
		currentTurn++;
		
		playerTurnsPlayed[currentPlayer.getId()]++;
		
		System.out.println("Tocca a " + currentPlayer.getName() + "La sua PlayArea è:\n");
		currentPlayer.getPlayArea().printPlayArea();
		currentPlayer.getPlayArea().printPlayAreaGrid();
		System.out.println("\n");
		System.out.println(currentPlayer.getPlayArea().getSymbolList());
		
		boolean wantToPlayCard = false;
		boolean validCardChoice = false;
		boolean validSideChoice = false;
		boolean validCornerChoice = false;
		boolean decisionMade = false;

		do { // Algoritmo di scelta, organizzato a checkpoint
			// Per prima cosa scelgo se voglio vedere qualcosa:
			if (!wantToPlayCard) {
				currentPlayer.ChooseWhatYouWannaDo (visibleCards,visibleObjectiveCards); // Resto in questo ciclo di visualizzazione finchè non decido di giocare una carta
				wantToPlayCard = true;
			}

		    if (!validCardChoice) { // Checkpoint 1: scelta carta
		        chosenCard = currentPlayer.chooseCardToPlay();
		        if (chosenCard.getType() == CardType.GOLD) { // Se la carta presa è gold
		            if (currentPlayer.getPlayArea().verifyGoldCardRequirements((GoldCard) chosenCard)) { // Faccio un check sui requisiti.
		                validCardChoice = true;
		            } else {
		                System.out.println("Non soddisfi i requisiti per giocare questa carta oro, scegli un'altra carta.");
		            }
		        } else {
		            validCardChoice = true;
		        }
		    } // Usciamo con ValidChoice true oppure ripetiamo dal Checkpoint 1.

		    if (validCardChoice && !validSideChoice) { // Checkpoint 2: scelta del lato (Se checkpoint 1 non è superato qua non si entra)
		    	// Controlla il lato
		    	chosenCardSide = chosenCard.ChooseSide(chosenCard); // Scegli il lato
		    	validSideChoice = true; // Checkpoint 2 superato (sul lato non c'è molto di cui discutere)
		    }

		    if (validSideChoice && !validCornerChoice) { // Checkpoint 3: Scelta del corner che voglio attaccare
		        chosenCornerPosition = currentPlayer.choosePosition(); // Scegli la cornerposition
		        validCornerChoice = true; // Checkpoint 3 superato
		    }

		    if (validCardChoice && validSideChoice && validCornerChoice) { // Checkpoint 4: scelta del corner SUL quale voglio attaccare il corner appena scelto
		        
		    	// Controllo se il mitico metodo "validPlacementsForGivenCorner" mi da dei corner validi sui quali attaccare la carta
		    	ArrayList<Coordinates> validPlacements = currentPlayer.getPlayArea().validPlacementsForGivenCorner(chosenCard, chosenCornerPosition);
		    	
		    	// Se il vettore dei corners validi non è vuoto allora consento al giocatore di sceglierne uno.
		    	if (!validPlacements.isEmpty()) { 
		            chosenCoordinates = currentPlayer.choosePositionOnPlayArea(chosenCardSide, chosenCornerPosition,validPlacements);
		            decisionMade = true; // GAME OVER
		            
		            
		        } else { // E se non ci sono corner sui quali attaccare la carta? Lo dico al giocatore.
		            System.out.println("Non ci sono coordinate valide per quell'angolo della carta scelta.");
		            System.out.println("Cosa vuoi modificare? ( 1 = Cambia corner, 2 = Cambia lato,3 = Cambia carta, 4 = Visualizza cose )");

		            int choice = scanner.nextInt();
		            
		            // Adesso scegli da dove vuoi ripartire a effettuare la tua decisione.
		            switch (choice) {
	                	case 4:
	                		wantToPlayCard = false;   // Torna a visualizzare cose
		                    validCardChoice = false;  // Torna a scegliere la carta
		                    validSideChoice = false;  // Dovrai scegliere nuovamente il lato
		                    validCornerChoice = false; // E l'angolo
	                    break;
		                case 3:
		                    validCardChoice = false;  // Torna a scegliere la carta
		                    validSideChoice = false;  // Dovrai scegliere nuovamente il lato
		                    validCornerChoice = false; // E l'angolo
		                    break;
		                case 2:
		                    validSideChoice = false;  // Torna a scegliere il lato
		                    validCornerChoice = false; // E l'angolo
		                    break;
		                case 1:
		                    validCornerChoice = false; // Solo l'angolo
		                    break;
		                default:
		                    System.out.println("Scelta non valida. Riprova.");
		                    break;
		            }
		        }
		    }
		} while (!decisionMade); // Cicla tra i checkpoint finchè non hai preso una DECISIONE
		
		int pointsValue = currentPlayer.n_PlacedCardPoints(chosenCardSide, chosenCornerPosition, chosenCoordinates);
		currentPlayer.removeCardFromHand(chosenCard);
		currentPlayer.addCardToPlayArea(chosenCardSide, chosenCornerPosition , chosenCoordinates);
		
		
		// FASE DI PESCAGGIO
		// 
		int choice = 0;
        do {
	       System.out.println("Vuoi:");
	       System.out.println("1 - Pescare una carta casuale");
	       System.out.println("1 - Prendere una carta dal tavolo");

            while (!scanner.hasNextInt()) {
                System.out.println("Inserisci un numero valido.");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > visibleCards.size()); 
		
		// A seconda del tipo di carta che hai giocato, pescane una dello stesso tipo.
		if(chosenCardSide.getType() == CardType.RESOURCE) {
			currentPlayer.addCardToHand(ResourceCard.drawResourceCard());
		}
		else if (chosenCardSide.getType() == CardType.GOLD) {
			currentPlayer.addCardToHand(GoldCard.drawGoldCard());
		}
		else {System.err.println("Attenzione: la carta BOH estratta è null! Game>Startgame");System.exit(0);}
		
		return false;
	}
	

	// Altri metodi
	private int getMaxTurnsPlayed() {
		int maxTurns = 0;
		for (int turns : playerTurnsPlayed) {
			maxTurns = Math.max(maxTurns, turns);
		}
		return maxTurns;
	}

	private boolean allPlayersHadLastTurn() {
		for (int turns : playerTurnsPlayed) {
			if (turns < getMaxTurnsPlayed()) {
				return false;
			}
		}
		return true;
	}

	private void endGame() {
		//da fare
		System.out.println("Partita terminata!");
	}

	/**
	 * Verifica se l'utente risponde si oppure no. Le valide risposte sono : 'Y', 'Yes', 'yes', 'N', 'No', or 'no'
	 *
	 * @return true is the answer is yes
	 */
	public boolean checkAnswer() {
		String choice;
		boolean flag;
		choice = scanner.next();
		//while(!choice.contains("y") || !choice.equals("Yes") || !choice.equals("yes") || !choice.equals("N") || !choice.equals("No") || !choice.equals("no")) 
		while (!choice.startsWith("s") && !choice.startsWith("S") && !choice.startsWith("n") && !choice.startsWith("N")) {
			System.out.println("La risposta non � corretta. Rispondi si oppure no. ");
			choice = scanner.next();
		}
		flag = choice.startsWith("s") || choice.startsWith("S");

		return flag;
	}

	

	private boolean isGameOver() {
		
		for (Player player : playerList) {
			if (player.getPoints() >= 20 ) {
				return true;
			}
		}
		return false;
	}

	
	//da fare
	private void determineWinner() {
		Player winner = null;
		int maxScore = Integer.MIN_VALUE;

		
		for (Player player : playerList) {
			if (player.getPoints() > maxScore) {
				maxScore = player.getPoints();
				winner = player;
			}
		}

		// pareggio?
		ArrayList <Player> winners = new ArrayList<>();
		for (Player player : playerList) {
			if (player.getPoints() == maxScore) {
				winners.add(player);
			}
		}

		
		if (winners.size() == 1) {
			System.out.println("Il vincitore �: " + winners.get(0).getName());
		} else {
			System.out.println("Pareggio! Il vincitore �:");
			for (Player player : winners) {
				System.out.println(player.getName());
			}
		}
	}







	public ArrayList<Player> rankings(){
		ArrayList<Player> sortedRanking;
		sortedRanking = playerList;
		// Setting up the comparator, the reversed flag is to have a decrescent order
		Comparator <Player> scoreComparator = Comparator.comparingInt(Player :: totalPoints).reversed();

		// Sorting the ArrayList with the Comparator
		Collections.sort(sortedRanking, scoreComparator);
		return sortedRanking;
	}

	/**
	 * Fast way to print an ArrayList of Cards with an index on top of the row
	 *
	 * @param cards
	 */
	public void printCardArrayList(ArrayList<Card> genericCards) {

		for (int i = 0; i < genericCards.size(); i++) {
			System.out.print(i + "\t");
		}
		System.out.println();

		for (int i = 0; i < genericCards.size(); i++) {
			System.out.print(genericCards.get(i) + "\t");
		}
		System.out.println();
	}

	private void updatevisibleCards() {
		int goldCount = 0;
		int resourceCount = 0;


		// Analisi delle carte visibili
		for (Card card : visibleCards) {
			if (card.getType() == CardType.GOLD) {
				goldCount++;
			} else if (card.getType() == CardType.RESOURCE) {
				resourceCount++;
			}
		}

		// Pesca delle nuove carte (se necessario)
		while (goldCount < 2) {
			Card gold = GoldCard.drawGoldCard(); // Metodo ipotetico per pescare una carta specifica
			if (gold != null) {
				visibleCards.add(gold);
				goldCount++;
			} else {
				lastRound = true;
				System.out.println("Carte oro sono finite. Fine gioco.");
				break; 
			}
		}

		while (resourceCount < 2) {
			Card resource = ResourceCard.drawResourceCard();
			if (resource != null) {
				visibleCards.add(resource);
				resourceCount++;
			} else {
				lastRound = true;
				System.out.println("Carte risorsa sono finite. Fine gioco. ");
				break;
			}
		}

	}
}
