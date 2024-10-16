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


/**
 * La classe Game rappresenta una sessione di gioco, 
 * gestendo i giocatori, le carte e il flusso del gioco.
 */
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
	private int pointsToEnd;
	private final Scanner scanner = new Scanner(System.in);
	private boolean resourcesCardsOver = false;
	private boolean goldCardsOver = false;

	/**
     * Restituisce la lista dei giocatori che partecipano al gioco.
     *
     * @return la lista dei giocatori
     */
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	/**
     * Costruisce un'istanza di Game, inizializzando la lista dei giocatori e i mazzi di carte.
     */
	
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
	/**
     * Avvia il gioco impostando i giocatori, 
     * inizializzando i mazzi e distribuendo le carte iniziali.
     */
	public void startGame() {	
		// Inizializza il deck delle ObjectiveCards:
        for (int i = 1; i <= 16; i++) {objectiveCardDeck.add(i);}
        
		// ------------ Scelta numero di giocatori ------------
		boolean bypass_numeroGiocatori = false; // bypass scelta giocatori
		
		if (!bypass_numeroGiocatori) {
			String name;
			numPlayers = 0; //variabile che conta il numero di giocatori
			do {
	            System.out.print("Quanti giocatori parteciperanno (da 2 a 4)?");
	            while (!scanner.hasNextInt()) {
	            	System.out.println("Per cortesia, inserisci un numero");
	            	scanner.next(); // Scarta l'input non valido
	            }
	            numPlayers = scanner.nextInt();
	            if (numPlayers < 2 || numPlayers > 4) {
	            	System.out.print("Numero di giocatori non valido\n");
	            	//scanner.next(); // Scarta l'input non valido
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
					System.out.println("Nome già utilizzato. Per favore, inserisci un nome diverso.");
				} else {
					playerList.add(new Player(ii, name));
					ii++;
				}
			} while ( ii < numPlayers+1);
		}
		else {
			numPlayers = 1;// per il debugging
			playerList.add(new Player(1, "zio Martino"));
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
			
			for (int i = 0; i < 2; i++) {System.out.println();}
			System.out.println("----------------------     " + player.getName());
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
			    System.err.println("Attenzione: la carta Gold estratta è null! Game > Startgame");
			} else {
				player.addCardToHand(goldHandCard);
			}				
			goldHandCard.printCard();
			
			// Carta obiettivo segreta - gestione nuova rispetto alle altre:
			
						
			//int randomIndex = 1;
			int randomIndex = rand.nextInt(objectiveCardDeck.size()-1)+1; // Numero ObjCard che pesco
			
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
	/**
     * Esegue il turno di un giocatore nel gioco.
     *
     * @param currentPlayer il giocatore il cui turno è in corso
     * @return true se il gioco è finito, false altrimenti
     */
	public boolean turn (Player currentPlayer) {
		Card chosenCard = null;
		Card chosenCardSide = null;
		CornerPosition chosenCornerPosition = null;
		Coordinates chosenCoordinates = null;
		currentTurn++;
		
		playerTurnsPlayed[currentPlayer.getId()]++;
		
		for (int i = 0; i < 3; i++) {System.out.println();}
		System.out.println("----------------------     " + currentPlayer.getName() + "  ---  punti: "+currentPlayer.getPoints());
		System.out.println("\nLa tua PlayArea è:\n");
		//currentPlayer.getPlayArea().printPlayArea(); // Stampa le informazioni relative ai corner, contenute nella lista di PlayAreaItems
		currentPlayer.getPlayArea().printPlayAreaGrid();
		System.out.println("\n");
		System.out.println("Risorse disponibili: " + currentPlayer.getPlayArea().getSymbolList());
		System.out.println("Speciali disponibili: " + currentPlayer.getPlayArea().getSpecialSymbolList());
		boolean wantToPlayCard = false;
		boolean validCardChoice = false;
		boolean validSideChoice = false;
		boolean validCornerChoice = false;
		boolean decisionMade = false;

		do { // Algoritmo di scelta, organizzato a checkpoint
			// Per prima cosa scelgo se voglio vedere qualcosa:
			if (!wantToPlayCard) {
				// Resto in questo ciclo di visualizzazione finchè non decido di giocare una carta
				currentPlayer.ChooseWhatYouWannaDo (visibleCards,visibleObjectiveCards); 
				wantToPlayCard = true;
			}

		    if (!validCardChoice) { // Checkpoint 1: scelta carta
		        chosenCard = currentPlayer.chooseCardToPlay();
		        validCardChoice = true;

		    } // Usciamo con ValidChoice true oppure ripetiamo dal Checkpoint 1.

		    if (validCardChoice && !validSideChoice) { // Checkpoint 2: scelta del lato (Se checkpoint 1 non è superato qua non si entra)
		    	// Controlla il lato
		    	chosenCardSide = chosenCard.ChooseSide(chosenCard); // Scegli il lato
		        if (chosenCardSide.getType() == CardType.GOLD) { // Se la carta presa è gold
		            if (currentPlayer.getPlayArea().verifyGoldCardRequirements((GoldCard) chosenCardSide)) { // Faccio un check sui requisiti.
		            	validSideChoice = true;
		            } else {
		                System.out.println("Non soddisfi i requisiti per giocare questa carta oro, scegli un'altra carta o cambia verso.");
		                validCardChoice = false;
		            }
		        } else {
		        	validSideChoice = true;
		        }
		    }

		    if (validSideChoice && !validCornerChoice) { // Checkpoint 3: Scelta del corner DELLA CARTA che voglio attaccare
		        chosenCornerPosition = currentPlayer.choosePosition(); // Scegli la cornerposition
		        validCornerChoice = true; // Checkpoint 3 superato
		    }

		    if (validCardChoice && validSideChoice && validCornerChoice) { // Checkpoint 4: scelta del corner SUL quale voglio attaccare il corner appena scelto     
		    	// Controllo se il mitico metodo "validPlacementsForGivenCorner" mi da dei corner validi sui quali attaccare la carta
		    	ArrayList<Coordinates> validPlacements = currentPlayer.getPlayArea().validPlacementsForGivenCorner(chosenCard, chosenCornerPosition);
		    	
		    	// Se il vettore dei corners validi non è vuoto allora consento al giocatore di sceglierne uno.
		    	if (!validPlacements.isEmpty()) { 
		            chosenCoordinates = currentPlayer.choosePositionOnPlayArea(chosenCardSide, chosenCornerPosition,validPlacements);
		            decisionMade = true; // Da qui si può solo piazzare la carta, la fase decisionale è finita.
		            
		            
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
		currentPlayer.addPoints(pointsValue);
		System.out.println("La carta piazzata ti ha dato punti: " + pointsValue + ", il tuo ammontare di punti totale è: " + currentPlayer.getPoints());
		currentPlayer.removeCardFromHand(chosenCard);
		currentPlayer.addCardToPlayArea(chosenCardSide, chosenCornerPosition , chosenCoordinates);
		
		
		// FASE DI PESCAGGIO
		// Se il player ha superato i punti per concludere la partita, oppure è il suo ultimo turno, non pesca.
		if (currentPlayer.getPoints() >= this.pointsToEnd || lastRound) {
			return true;
		}
		// 
		int choice = 0;
        do {
	       System.out.println("Vuoi:");
	       System.out.println("1 - Pescare una carta casuale");
	       System.out.println("2 - Prendere una carta dal tavolo");

            while (!scanner.hasNextInt()) {
                System.out.println("Inserisci un numero valido.");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2); 
		
        
        if (choice == 1) {
			// A seconda del tipo di carta che hai giocato, pescane una dello stesso tipo.
			if(chosenCardSide.getType() == CardType.RESOURCE) {
				Card cardToAdd = (Card)ResourceCard.drawResourceCard();
				if (ResourceCard.getResourceCardsDeck().size() == 0) {
					System.err.println("Ultima carta risorsa pescata");
					resourcesCardsOver = true;
				}
				if (cardToAdd == null) {
					System.err.println("Mazzo di Risorse finito, la carta non è stata pescata");
				} else {
					currentPlayer.addCardToHand(cardToAdd);
				}				
			}
			else if (chosenCardSide.getType() == CardType.GOLD) {
				Card cardToAdd = (Card)GoldCard.drawGoldCard();
				if (GoldCard.getGoldCardsDeck().size() == 0) {
					System.err.println("Ultima carta oro pescata");
					goldCardsOver = true;
				}
				if (cardToAdd == null) {
					System.err.println("Mazzo di Gold finito, la carta non è stata pescata");
					goldCardsOver = true;
				} else {
					currentPlayer.addCardToHand(cardToAdd);
				}
			}
			else {System.err.println("Attenzione: la carta BOH estratta è null! Game>Startgame");System.exit(0);}
        } else {
        	// Caso in cui il giocatore ha scelto di prendere una carta dal tavolo.
        	ArrayList<Card> carteDisponibili = new ArrayList<>();
        	// Mostra le carte presenti sul tavolo dello stesso tipo della carta appena giocata
        	for (Card visibleCard : visibleCards) {
        		if (visibleCard.getType() == chosenCardSide.getType()) {
        			visibleCard.printCard();
        			carteDisponibili.add(visibleCard);
        		}
        	}
        	
        	// Scegli una delle 2 carte carte a disposizione
            do {
     	       System.out.println("Pesca la carta:");
     	       System.out.println("1 - Carta 1");
     	       System.out.println("2 - Carta 2");
                 while (!scanner.hasNextInt()) {
                     System.out.println("Inserisci un numero valido.");
                     scanner.next();                 }
                 choice = scanner.nextInt()-1;
             } while (choice < 0 || choice > carteDisponibili.size()-1);
            
            currentPlayer.addCardToHand(carteDisponibili.get(choice));
            visibleCards.remove(carteDisponibili.get(choice));
            
            if(carteDisponibili.get(choice).getType() == CardType.RESOURCE) {
				Card cardToAdd = (Card)ResourceCard.drawResourceCard();
				if (ResourceCard.getResourceCardsDeck().size() == 0) {
					System.err.println("Ultima carta risorsa pescata");
					resourcesCardsOver = true;
				}
				if (cardToAdd == null) {
					System.err.println("Mazzo di risorse finito, la carta non è stata messa sul tavolo");
					resourcesCardsOver = true;
				} else {
					visibleCards.add(cardToAdd);
				}            	
            } else {
				Card cardToAdd = (Card)GoldCard.drawGoldCard();
				if (GoldCard.getGoldCardsDeck().size() == 0) {
					System.err.println("Ultima carta oro pescata");
					goldCardsOver = true;
				}
				if (cardToAdd == null) {
					System.err.println("Mazzo di Gold finito, la carta non è stata messa sul tavolo");
					goldCardsOver = true;
				} else {
					visibleCards.add(cardToAdd);
				}     
            	visibleCards.add(GoldCard.drawGoldCard());
            }
        }
        
        if (goldCardsOver && resourcesCardsOver) {
        	lastRound = true;
        }
		return false;
	}
	

	/*
	 * // Altri metodi
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
	 */

	/**
	 * Verifica se l'utente risponde si oppure no. Le valide risposte sono : 'Y', 'Yes', 'yes', 'N', 'No', or 'no'
	 *
	 * @return true is the answer is yes
	 */
	/*
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
	*/
	
	
	/**
     * Assegna punti al giocatore in base alle carte obiettivo.
     *
     * @param player il giocatore a cui assegnare i punti
     */
	public void assignObjectiveCardPoints (Player player) {
		
		int objectiveCardPoints = player.getPlayerObjectiveCard().calculateObjectiveCardPoints(player.getPlayArea());
		
		player.addPoints(objectiveCardPoints);	
		
		player.addAchievedObjectiveCards((objectiveCardPoints>0 ? 1 : 0));
		
		for (Card visibleObjectiveCard : visibleObjectiveCards) {
			
			objectiveCardPoints = visibleObjectiveCard.calculateObjectiveCardPoints(player.getPlayArea());
			
			player.addPoints(objectiveCardPoints);	
			
			player.addAchievedObjectiveCards((objectiveCardPoints>0 ? 1 : 0));
		}
		
	}

	
	/*
	private boolean isGameOver() {
		
		for (Player player : playerList) {
			if (player.getPoints() >= 20 ) {
				return true;
			}
		}
		return false;
	}
	*/

	
	/**
     * Determina il vincitore del gioco in base ai punti e agli obiettivi raggiunti.
     */
	public void determineWinner() {
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
			System.out.println("Il vincitore è: " + winners.get(0).getName());
		} else {	
			int maxAchi = Integer.MIN_VALUE;
			
			for (Player player : winners) {
				if (player.getAchievedObjectiveCards() > maxAchi) {
					maxAchi = player.getAchievedObjectiveCards();
					winner = player;
				}
			}
			
			ArrayList <Player> winners2 = new ArrayList<>();
			int maxObjCardAch = maxAchi;
			
			for (Player player : winners) {
				if (player.getAchievedObjectiveCards() == maxObjCardAch) {
					winners2.add(player);
				}
			}
			
			if (winners2.size() == 1) {
				System.out.println("Pareggio! Il vincitore è:" + winners2);
			} else {
				System.out.println("Pareggio! I vincitori sono: " + winners2);
			}
			
			
		}
	}





	/**
	 * Restituisce una classifica dei giocatori ordinata in base al loro punteggio, in ordine decrescente.
	 * 
	 * @return un ArrayList di oggetti Player ordinati dal punteggio più alto a quello più basso
	 */

	public ArrayList<Player> rankings(){
		ArrayList<Player> sortedRanking;
		sortedRanking = playerList;
		// Setting up the comparator, the reversed flag is to have a decrescent order
		Comparator <Player> scoreComparator = Comparator.comparingInt(Player :: getPoints).reversed();

		// Sorting the ArrayList with the Comparator
		Collections.sort(sortedRanking, scoreComparator);
		return sortedRanking;
	}

	/**
	 * Metodo rapido per stampare un ArrayList di oggetti Card con un indice nella riga superiore.
	 *
	 * @param genericCards un ArrayList di Card da stampare
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

	/*
	 * private void updatevisibleCards() {
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
				System.out.println("Carte oro sono finite. , la carta non è stata pescata");
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
	 */


	/**
	 * Restituisce i punti necessari per terminare il gioco.
	 *
	 * @return un intero che rappresenta i punti rimanenti per terminare il gioco
	 */
	public int getPointsToEnd() {
		return pointsToEnd;
	}
	/**
	 * Imposta lo stato che indica che le carte risorse sono terminate.
	 */
	public void setResourcesCardsOver() {
		this.resourcesCardsOver = true;
	}
	/**
	 * Imposta lo stato che indica che le carte d'oro sono terminate.
	 */
	public void setGoldCardsOver() {
		this.goldCardsOver = true;
	}

	/**
	 * Imposta i punti necessari per l'ultimo round.
	 *
	 * @param pointsToEnd il numero di punti rimanenti per terminare il gioco
	 */
	public void setPointsToEnd(int pointsToEnd) {
		this.pointsToEnd = pointsToEnd;
	}
	
	/**
	 * Imposta lo stato che indica se l'ultimo round è in corso.
	 *
	 * @param in_lastRound true se è l'ultimo round, false altrimenti
	 */
	public void setLastRound(boolean in_lastRound) {
		this.lastRound = in_lastRound;
	}
	/**
	 * Restituisce lo stato che indica se l'ultimo turno è in corso.
	 *
	 * @return true se è l'ultimo round, false altrimenti
	 */
	public boolean getLastRound() {
		return this.lastRound;
	}
}
