package cards;

import java.util.ArrayList;
import java.util.Arrays;


import game.PlayArea;
import game.PlayArea.PlayAreaItem;
/**
 * La classe ObjectiveCard rappresenta una carta obiettivo nel gioco. 
 * Essa estende la classe Card e contiene informazioni sui requisiti 
 * necessari per ottenere punti in base alla configurazione della 
 * PlayArea. I requisiti possono includere simboli, simboli speciali 
 * e posizioni specifiche delle carte nel campo di gioco.
 */
public class ObjectiveCard extends Card {

	private CardType type = CardType.OBJECTIVE;
	private RequirementType requirementType;
	private ArrayList<Symbol> symbolRequirement;
	private ArrayList<SpecialSymbol> specialSymbolRequirement;
	private Object[][] positionRequirement;
	private int pointsPerRequirement = 999;//per il debug
	
	/**
     * Calcola i punti guadagnati dalla carta obiettivo in base alla PlayArea.
     *
     * @param playArea L'area di gioco in cui sono posizionate le carte.
     * @return I punti guadagnati dalla carta obiettivo.
     */
	
	// Metodo importantissimo che calcola i punti in base alla PlayArea;
	@Override
	public int calculateObjectiveCardPoints (PlayArea playArea) {
		int points = 0;
		
		switch (this.requirementType) {
		
			case SYMBOL_REQUIREMENT:
				points = pointsPerRequirement * lista1InLista2(this.symbolRequirement,playArea.getSymbolList());
			break;
			case SPECIALSYMBOL_REQUIREMENT:
				points = pointsPerRequirement * lista1InLista2(this.specialSymbolRequirement,playArea.getSpecialSymbolList());
			break;
			case POSITION_REQUIREMENT:
				/* Il position requirement è strutturato in questo modo:
					Ogni carta obiettivo con requisiti sulla posizione delle carte contiene le 3 carti collocate in 3 posizioni (x,y).
					Queste posizioni partono da una (0,0), scelta in maniera arbitraria (per convenzione la carta 0,0 è sempre quella più in basso).
					Quando si controlla se esistono, nella PlayArea del giocatore, dei gruppi di carte che seguono questo pattern
					si fa il controllo solo sul BOTTOM_LEFT corner, così da definire una posizione univoca per la carta nel campo di gioco.
					
					!!! Ogni carta va contata una sola volta, per massimizzare il numero di punti devo ciclare su una playArea ordinata
					Se non lo facessi rischerei di avere un pattern di 3 carte in fila, averne 6 di fila sulla play area e matchare il pattern
					una volta sola perchè parto a contare dalla posizione 2, quindi 2,3,4 matcharebbero mentre 1,5,6 non formerebbero una fila da 3.
				*/
				
				// La modalità debug permette di printare messaggi a schermo per sapere cosa sta facendo l'algoritmo.
				boolean debugMode = true;
				
				//Uso il metodo playArea.getSortedPlayAreaItems per ottenere la play area ordinata
				ArrayList<PlayAreaItem> l_sortedPlayAreaItems = playArea.getSortedPlayArea();
				
				// Salvo la variabile "filtered", dalla quale tolgo le carte che appartengono già ad un pattern matchato.
				ArrayList<PlayAreaItem> l_sortedPlayAreaItems_filtered = new ArrayList<>(l_sortedPlayAreaItems);
				
				int numeroPatternTrovati = 0;
			
				// Ciclo su tutta la playArea prendendo il PlayAreaItem, controllo i candidati "punti di partenza" dai quali partire per controllare tutto
				for (PlayAreaItem playAreaItem_0 : l_sortedPlayAreaItems) {
					if (debugMode) {System.out.println("Corner di partenza: ( " + playAreaItem_0.getCoordinates().getX() + " , "+playAreaItem_0.getCoordinates().getY()+" )");
									System.out.print(", con carta di tipo: " + playAreaItem_0.getCard().getKingdom());} 
					
					// Per ogni round di ricerca salvo le carte che ho controllato così che, se trovo l'intero pattern,
					// le elimino dalla l_sortedPlayAreaItems_filtered (ogni carta può essere contata una volta sola in uno stesso tipo di pattern)
					ArrayList<Coordinates> checkedCoordinates = new ArrayList<>();
					
					// Verifico subito che quel punto sia BOTTOM_LEFT, se non lo è lo lascio perdere in partenza
					if ( ! (playAreaItem_0.getCornerPosition() == CornerPosition.BOTTOM_LEFT)){
						continue;
					}					
					
					if (debugMode) {System.out.println("Il punto ( " + playAreaItem_0.getCoordinates().getX() + " , "
									+ playAreaItem_0.getCoordinates().getY() + " ), è BOTTOM_LEFT! VADO AVANTI!!... ");} 
					
					boolean conditionsSatisfied = true;
					// Ciclo sui "positionRequirements" della Objective card che intendo controllare.
					// Ogni singleRequirement contiene {Coordinates, Symbol}
					for (Object[] singleRequirement : this.positionRequirement) { 
						
						// Estraggo le coordinate e il simbolo dal singleRequirement:
						Coordinates requirementCoordinates = (Coordinates)singleRequirement[0];
						Symbol requirementSymbol = (Symbol)singleRequirement[1];						

						if (debugMode) {System.out.println("Single Requirement con coordinate: ( " + requirementCoordinates.getX() + " , "+requirementCoordinates.getY()+" )");
										System.out.print(" e simbolo: "+requirementSymbol.toString());}
						
						// Calcolo il punto della playArea che intendo veramente controllare sommando le coordinate del pattern
						// il primo sarà il punto playAreaItem_0 stesso, dato che sommo (0,0)
						int x_toCheck = playAreaItem_0.getCoordinates().getX() + requirementCoordinates.getX();
						int y_toCheck = playAreaItem_0.getCoordinates().getX() + requirementCoordinates.getY();						
					
						if (debugMode) {System.out.println(" Cerco il punto che nella PlayArea ha coordinate: (" + x_toCheck + " , " + y_toCheck + " )");}
						
						boolean trovato = false; // booleanino per vedere se ho trovato il punto
						// Frugo tutta la l_sortedPlayAreaItems per vedere se trovo un punto a quelle coordinate con quel simbolo che sia BOTTOM_LEFT
						for (PlayAreaItem item : l_sortedPlayAreaItems_filtered) {
							
							// Se non ha le coordinate uguali e non è un BOTTOM_LEFT e la carta non è iniziale lascio perdere e proseguo con gli altri punti
							if ( ! ( item.getCoordinates().getX() == x_toCheck && item.getCoordinates().getY() == y_toCheck &&
									item.getCornerPosition() == CornerPosition.BOTTOM_LEFT && item.getCard().getType() != CardType.STARTING)) {
								continue;
							}
							if (debugMode) {System.out.println("Trovato il punto. Requirement: "+requirementSymbol.toString()+", Carta: "+item.getCard().getKingdom());}
							
							// Se non appartiene al medesimo regno proseguo con gli altri punti
							// NB Devo controllare LA CARTA, non il corner 
							// (non avrei potuto incorporare questo controllo in quelli precedenti perchè InitialCard non contiene il metodo getKingdom())
							if ( ! ( item.getCard().getKingdom().toString() == requirementSymbol.toString())) {
								continue;
							}
							
							if (debugMode) {System.out.println("Trovato punto con stesso requirement!!, il ( " + x_toCheck + " , " + y_toCheck + " )\n");}
							
							// Se sono arrivato fino a qui, cioè se ho trovato una carta, nella giusta posizione, con il giusto simbolo
							// Allora dichiaro di aver trovato ciò che cercavo
							trovato = true;
							
							// Interrompo il ciclo
							break;
						}// fine ricerca della carta nella playarea che soddisfa le condizioni della singola carta nel pattern
						
						// Ho finito di cercare nella playarea, se ho trovato la carta allora bene:
						// Salvo la carta che soddisfa le condizioni richieste e vado avanti con la ricerca della carta successiva,
						// cioè quella che, nel pattern della ObjectiveCard, viene dopo quella che ho cercato.
						// Se invece non ho trovato nulla, allora trovato sarà false, in questo caso posso rompere anche questo for e dire che
						// la condizione per ottenere dei punti non è soddisfatta:
						if (trovato) {
							// Salvo la carta nelle checked coordinates
							checkedCoordinates.add(new Coordinates(x_toCheck,y_toCheck));
						}
						else { 
							// Se trovato è falso devo smettere di testare requirements partendo dalla carta corrispondente al playAreaItem_0
							// memorizzo il fatto che la carta della playarea dalla quale sono partito a testare le condizioni richieste dalla ObjectiveCard,
							// cioè la playAreaItem_0, non soddisfa le suddette condizioni.
							conditionsSatisfied = false;
							break;								
						}
					}// fine del ciclo sulle carte mostrate sulla ObjectiveCard che compongono il pattern
					
					/*
						A questo punto devo agire in base alla conditionsSatisfied.
						Se è true vuol dire che tutto è andato liscio e che il pattern è stato trovato sulla playarea, partendo dalla playAreaItem_0
						devo: 	1) incrementare il counter numeroPatternTrovati
								2) cancellare dalla l_sortedPlayAreaItems_filtered le carte che appartengono al pattern che ho matchato
						Se è false vuol dire che dalla carta dalla quale sono partito: 
						playAreaItem_0, non parte nessun pattern simile a quello che sto cercando. 
						Niente, vado avanti e testo un'altra carta della PlayArea.
					*/
					if (conditionsSatisfied) {
					    numeroPatternTrovati++;

					    // Cancellazione delle carte salvate in checkedCoordinates dalla l_sortedPlayAreaItems_filtered
					    // Devo farla prima salvando i PlayAreaItem che intendo cancellare e poi cancellandoli tutti insieme.
					    ArrayList<PlayAreaItem> itemsToRemove = new ArrayList<>(); // Lista di elementi da rimuovere
				        for (PlayAreaItem playAreaItem_toDelete : l_sortedPlayAreaItems_filtered) {
				            for (Coordinates coordinates_toDelete : checkedCoordinates) {
				                if (playAreaItem_toDelete.getCoordinates().getX() == coordinates_toDelete.getX() &&
				                    playAreaItem_toDelete.getCoordinates().getY() == coordinates_toDelete.getY()) {
				                    itemsToRemove.add(playAreaItem_toDelete); // Aggiungi l'elemento da rimuovere
				                    break; // Esci dal ciclo interno
				                }
				            }
				        }

				        // Rimuovi gli elementi dalla lista filtrata.
				        l_sortedPlayAreaItems_filtered.removeAll(itemsToRemove);
					}
					else {
						//Niente, vado avanti e testo un'altra carta della PlayArea.
					}
				} // fine del ciclo che cerca i punti di partenza per la ricerca dei pattern.
			
				// Moltiplico il modificatore di punti per il numero dei pattern trovati per ottenere i punti che la ObjectiveCard mi frutta data la PlayArea.
				points = pointsPerRequirement * numeroPatternTrovati;			
		break;
		
		default:
            throw new IllegalArgumentException("RequirementType non riconosciuto");		
		}			
		return points;
	}
	
	 /**
     * Stampa i dettagli della carta obiettivo.
     */
    @Override
	public void printCard() {		
		
		System.out.println("\nObjective card numero: " + this.getNumber());
		System.out.println("Requirement type : " + this.getRequirementType()+", "+this.getPointsPerRequirement() +" punti per ogni : ");
		
		switch (this.getRequirementType()) {
			case SYMBOL_REQUIREMENT:
				System.out.println(this.getSymbolRequirement());
			break;
			case SPECIALSYMBOL_REQUIREMENT:
				System.out.println(this.getSpecialSymbolRequirement());
			break;
			case POSITION_REQUIREMENT:
				int n=4;
				// Questo è tosto, devo creare una griglia nxn per stampare tutto e poi mettere dentro i kingdom corrispondenti
				
			    // Creazione della griglia vuota nxn (son tutte stringhe)
			    String[][] grid = new String[n][n];
			    
			    // Inizializzo tutto con 3 spazi, che è la lunghezza degli abbreviated kingdoms
			    for (int j = 0; j < n; j++) {
			        for (int i = 0; i < n; i++) {
			            grid[j][i] = "   ";  // j corrisponde a y (-y a dire il vero) e i corrisponde a x
			        }
			    }

			    // Trovo il minimo valore di x e y nelle coordinate
			    int minX = Integer.MAX_VALUE;
			    int minY = Integer.MAX_VALUE;
			    for (Object[] req : positionRequirement) { // Ciclo sui positionrequirements
			        Coordinates coord = (Coordinates) req[0]; // Estraggo il primo valore che son le coordinate (Coordinates)
			        // Confronto con il minimo salvato e sostituisco nel caso.
			        int x = coord.getX();
		            int y = coord.getY();
		            if (x < minX) { minX = x; }
		            if (y < minY) { minY = y; }
			    }

			    // Riempio la griglia con le coordinare relative!
			    for (Object[] req : positionRequirement) { // Ciclo sui position requirements
			        Coordinates coord = (Coordinates) req[0]; // estraggo le coordinate
			        Symbol symbol = (Symbol) req[1]; // estraggo il simbolo
			        String abbreviatedSymbol = symbol.getAbbreviation(); // estraggo l'abbreviazione, che è una stringa
			        
			        // Calcolo le coordinate relative rispetto ai minimi trovati
			        int x = coord.getX() - minX;
			        int y = coord.getY() - minY;
			        
			        // Devo invertire y (che deve andare dal basso verso l'alto), mi trovo e controllo che la griglia sia 3x3
			        if (x >= 0 && x < n && y >= 0 && y < n) {
			            grid[(n - 1) - y][x] = abbreviatedSymbol; // (n-1) - y per invertire l'asse Y
			        } else {
			            System.err.println("Coordinate fuori dalla griglia: (" + coord.getX() + ", " + coord.getY() + ") carta: " + this.getNumber());
			        }
			    }

			    // Stampa della griglia
			    System.out.println();
			    for (int j = 0; j < n; j++) {
			        for (int i = 0; i < n; i++) {
			            System.out.print(grid[j][i] + " ");
			        }
			        System.out.println(); // A capo dopo ogni riga
			    }
				
				
			break;
		}
		
	}
    /**
    * Questo costruttore imposta i valori della carta obiettivo in base al valore di n.
    * Ogni valore di n corrisponde a un insieme specifico di requisiti e punti.
    *
    * @param n Un intero che determina il tipo di carta obiettivo da creare.
    *          I valori da 1 a 16 identificano la carta
    * @throws IllegalArgumentException Se il valore di n non è valido.
    */
    // Costruttore che accetta int n
    public ObjectiveCard(int n) {
        // Imposta i valori in base a n
    	this.setNumber(n);
        switch(n) {
            case 1:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.POSITION_REQUIREMENT);
                this.setPositionRequirement(new Object[][] {
                    {new Coordinates(0, 0), Symbol.FUNGI_KINGDOM},
                    {new Coordinates(1, 1), Symbol.FUNGI_KINGDOM},
                    {new Coordinates(2, 2), Symbol.FUNGI_KINGDOM}
                });
                break;
            case 2:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.POSITION_REQUIREMENT);
                this.setPositionRequirement(new Object[][] {
                    {new Coordinates(0, 0), Symbol.PLANT_KINGDOM},
                    {new Coordinates(-1, 1), Symbol.PLANT_KINGDOM},
                    {new Coordinates(-2, 2), Symbol.PLANT_KINGDOM}
                });
                break;
            case 3:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.POSITION_REQUIREMENT);
                this.setPositionRequirement(new Object[][] {
                    {new Coordinates(0, 0), Symbol.ANIMAL_KINGDOM},
                    {new Coordinates(1, 1), Symbol.ANIMAL_KINGDOM},
                    {new Coordinates(2, 2), Symbol.ANIMAL_KINGDOM}
                });
                break;
            case 4:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.POSITION_REQUIREMENT);
                this.setPositionRequirement(new Object[][] {
                    {new Coordinates(0, 0), Symbol.INSECT_KINGDOM},
                    {new Coordinates(-1, 1), Symbol.INSECT_KINGDOM},
                    {new Coordinates(-2, 2), Symbol.INSECT_KINGDOM}
                });
                break;
            case 5:
            	this.setPointsPerRequirement(3);
                this.setRequirementType(RequirementType.POSITION_REQUIREMENT);
                this.setPositionRequirement(new Object[][] {
                    {new Coordinates(0, 0), Symbol.PLANT_KINGDOM},
                    {new Coordinates(-1, 1), Symbol.FUNGI_KINGDOM},
                    {new Coordinates(-1, 3), Symbol.FUNGI_KINGDOM}
                });
                break;
            case 6:
            	this.setPointsPerRequirement(3);
                this.setRequirementType(RequirementType.POSITION_REQUIREMENT);
                this.setPositionRequirement(new Object[][] {
                    {new Coordinates(0, 0), Symbol.PLANT_KINGDOM},
                    {new Coordinates(1, 1), Symbol.PLANT_KINGDOM},
                    {new Coordinates(1, 3), Symbol.INSECT_KINGDOM}
                });
                break;
            case 7:
            	this.setPointsPerRequirement(3);
                this.setRequirementType(RequirementType.POSITION_REQUIREMENT);
                this.setPositionRequirement(new Object[][] {
                    {new Coordinates(0, 0), Symbol.ANIMAL_KINGDOM},
                    {new Coordinates(0, 2), Symbol.ANIMAL_KINGDOM},
                    {new Coordinates(1, 3), Symbol.FUNGI_KINGDOM}
                });
                break;
            case 8:
            	this.setPointsPerRequirement(3);
                this.setRequirementType(RequirementType.POSITION_REQUIREMENT);
                this.setPositionRequirement(new Object[][] {
                    {new Coordinates(0, 0), Symbol.INSECT_KINGDOM},
                    {new Coordinates(0, 2), Symbol.INSECT_KINGDOM},
                    {new Coordinates(-1, 3), Symbol.ANIMAL_KINGDOM}
                });
                break;
            case 9:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.SYMBOL_REQUIREMENT);
                this.setSymbolRequirement(new ArrayList<>(Arrays.asList(
                		Symbol.FUNGI_KINGDOM, Symbol.FUNGI_KINGDOM, Symbol.FUNGI_KINGDOM)));
                break;
            case 10:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.SYMBOL_REQUIREMENT);
                this.setSymbolRequirement(new ArrayList<>(Arrays.asList(
                		Symbol.PLANT_KINGDOM, Symbol.PLANT_KINGDOM, Symbol.PLANT_KINGDOM)));
                break;
            case 11:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.SYMBOL_REQUIREMENT);
                this.setSymbolRequirement(new ArrayList<>(Arrays.asList(
                		Symbol.ANIMAL_KINGDOM, Symbol.ANIMAL_KINGDOM, Symbol.ANIMAL_KINGDOM)));
                break;
            case 12:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.SYMBOL_REQUIREMENT);
                this.setSymbolRequirement(new ArrayList<>(Arrays.asList(
                		Symbol.INSECT_KINGDOM, Symbol.INSECT_KINGDOM, Symbol.INSECT_KINGDOM)));
                break;
            case 13:
            	this.setPointsPerRequirement(3);
                this.setRequirementType(RequirementType.SPECIALSYMBOL_REQUIREMENT);
                this.setSpecialSymbolRequirement(new ArrayList<>(Arrays.asList(
                		SpecialSymbol.QUILL, SpecialSymbol.INKWELL, SpecialSymbol.MANUSCRIPT)));
                break;
            case 14:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.SPECIALSYMBOL_REQUIREMENT);
                this.setSpecialSymbolRequirement(new ArrayList<>(Arrays.asList(
                		SpecialSymbol.MANUSCRIPT, SpecialSymbol.MANUSCRIPT)));
                break;
            case 15:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.SPECIALSYMBOL_REQUIREMENT);
                this.setSpecialSymbolRequirement(new ArrayList<>(Arrays.asList(
                		SpecialSymbol.INKWELL, SpecialSymbol.INKWELL)));
                break;
            case 16:
            	this.setPointsPerRequirement(2);
                this.setRequirementType(RequirementType.SPECIALSYMBOL_REQUIREMENT);
                this.setSpecialSymbolRequirement(new ArrayList<>(Arrays.asList(
                		SpecialSymbol.QUILL, SpecialSymbol.QUILL)));
                break;

            // Altri casi per eventuali altre regole
            default:
                throw new IllegalArgumentException("Valore di n non valido!");
            
        }
    }
    /**
     * Conta quante volte gli elementi di una lista sono contenuti in un'altra lista.
     *
     * @param lista1 La lista da controllare.
     * @param lista2 La lista in cui cercare gli elementi di lista1.
     * @param <T> Il tipo degli elementi nelle liste.
     * @return Il numero di volte che tutti gli elementi di lista1 sono stati trovati in lista2.
     */
    public static <T> int lista1InLista2(ArrayList<T> lista1, ArrayList<T> lista2) {
        // copia della lista
        ArrayList<T> tempLista2 = new ArrayList<>(lista2);
        Boolean bContenuta = true;
        int nListe1InLista2 = 0;
        
        while (bContenuta) {
	        for (T item : lista1) {
	            if (!tempLista2.contains(item)) {
	            	bContenuta = false; // Se un elemento di lista1 non c'è in lista 2 ferma il ciclo
	            	break;
	            } else {
	                // Se l'elemento esiste, rimuovilo da tempLista2
	                tempLista2.remove(item);
	            }
	        }        
	        // Se tutti gli elementi di lista1 sono stati trovati, aumenta il counter
	        if (bContenuta) {
	        	 nListe1InLista2++;
	        }
	       
        }
        
        return nListe1InLista2;
    }
    /**
     * Estrae una carta obiettivo dal mazzo
     *
     * @param n Un intero che può essere  per determinare il tipo di carta obiettivo da estrarre.
     * @return Una ObjectiveCard estratta o null se non implementata.
     */
	public static ObjectiveCard drawObjectiveCards(int n) {
		ObjectiveCard card = null;
		return card;
	}
	/**
	 * enum che definisce i diversi tipi di requisiti che una carta obiettivo può avere.
	 * I tipi di requisiti includono:
	 * SYMBOL_REQUIREMENT Richiede una combinazione specifica di simboli.
	 * SPECIALSYMBOL_REQUIREMENT Richiede una combinazione specifica di simboli speciali.
	 * POSITION_REQUIREMENT Richiede una combinazione di simboli in posizioni specifiche sulla griglia.
	 */
	private enum RequirementType {
		SYMBOL_REQUIREMENT, SPECIALSYMBOL_REQUIREMENT, POSITION_REQUIREMENT;
	}
	/**
	 * Restituisce il tipo di requisito associato a questa carta obiettivo.
	 *
	 * @return Il tipo di requisito  di questa carta.
	 */
	public RequirementType getRequirementType() {
		return requirementType;
	}

	/**
	 * Imposta il tipo di requisito per questa carta obiettivo.
	 *
	 * @param requirementType Il tipo di requisito da impostare per questa carta ({@code RequirementType}).
	 */
	public void setRequirementType(RequirementType requirementType) {
		this.requirementType = requirementType;
	}

	/**
	 * Restituisce i simboli richiesti per soddisfare il requisito di questa carta.
	 * 
	 * @return Una lista di simboli  che rappresentano i simboli richiesti.
	 */
	public ArrayList<Symbol> getSymbolRequirement() {
		return symbolRequirement;
	}

	/**
	 * Imposta i simboli richiesti per soddisfare il requisito di questa carta.
	 * 
	 * @param symbolRequirement Una lista di simboli che rappresentano i simboli richiesti.
	 */
	public void setSymbolRequirement(ArrayList<Symbol> symbolRequirement) {
		this.symbolRequirement = symbolRequirement;
	}

	/**
	 * Restituisce i simboli speciali richiesti per soddisfare il requisito di questa carta.
	 * 
	 *
	 * @return Una lista di simboli speciali richiesti.
	 */
	public ArrayList<SpecialSymbol> getSpecialSymbolRequirement() {
		return specialSymbolRequirement;
	}

	/**
	 * Imposta i simboli speciali richiesti per soddisfare il requisito di questa carta.
	 *
	 *
	 * @param specialSymbolRequirement Una lista di simboli speciali da impostare.
	 */
	public void setSpecialSymbolRequirement(ArrayList<SpecialSymbol> specialSymbolRequirement) {
		this.specialSymbolRequirement = specialSymbolRequirement;
	}

	/**
	 * Restituisce la matrice che rappresenta i requisiti di posizione per questa carta.
	 * 
	 *
	 * @return Una matrice di oggetti (code Object[][]) che contiene le coordinate e i simboli richiesti.
	 */
	public Object[][] getPositionRequirement() {
		return positionRequirement;
	}

	/**
	 * Imposta la matrice dei requisiti di posizione per questa carta.
	 *
	 * @param positionRequirement Una matrice di oggetti ( Object[][]) che contiene le coordinate e i simboli da impostare.
	 */
	public void setPositionRequirement(Object[][] positionRequirement) {
		this.positionRequirement = positionRequirement;
	}

	/**
	 * Restituisce il numero di punti assegnati per ogni requisito soddisfatto.
	 *
	 * @return Il numero di punti per ogni requisito soddisfatto.
	 */
	public int getPointsPerRequirement() {
		return pointsPerRequirement;
	}

	/**
	 * Imposta il numero di punti assegnati per ogni requisito soddisfatto.
	 *
	 * @param pointsPerRequirement Il numero di punti da assegnare per ogni requisito soddisfatto.
	 */
	public void setPointsPerRequirement(int pointsPerRequirement) {
		this.pointsPerRequirement = pointsPerRequirement;
	}


	
	@Override
	public String getAbbreviatedCorner(CornerPosition cornertype) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Restituisce una matrice che rappresenta l'assegnazione dei punti per questa carta.
	 *
	 * @return Una matrice di oggetti che rappresenta l'assegnazione dei punti.
	 */
	@Override
	public Object[][] getPointsAssignment() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CardType getType() {
		return this.type;
	}

}




