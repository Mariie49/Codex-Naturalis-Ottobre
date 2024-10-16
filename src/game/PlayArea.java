/* PLAY AREA

Ha i suoi bei metodini, ma fondamentalmente è una lista di PlayAreaItem che sono strutturati così:

{Coordinate, Corner, CornerPosition, b_covered, Card}

C'è da ragionare se salvare direttamente qua informazioni sui corners oppure estrarle dall'oggetto corner

Metodini:

	getOccupiedCoordinates()
		metodo che restituisce una arraylist di Coordinates, tutte quelle che ha trovato nella play area, quindi occupate

	validPlacementsForGivenCorner(Card card, CornerPosition cornerposition)
		restituisce una ArrayList di Coordinates, data la carta che si intende piazzare e il corner che si intende attaccare
		calcola dove è possibile piazzare quel corner nella PlayArea rispettando alcuni contraints che per ora includono:
			1) Angolo della PlayArea opposto all'angolo scelto
	
	placeCornerOnXYCoordinates (Coordinates coordinates ,Corner corner, Card card)
		piazza il corner nelle coordinate date. Solo il corner, non la carta.
		IMPORTANTE: Si occupa di coprire i corner che sono stati coperti dalla nuova carta
		
	placeInitialCard(InitialCard card)
		piazza la carta iniziale con il corner in basso a sinistra in 0,0.
		Di solito è il giocatore che piazza le carte
		
	getSymbolList ()
		fruga sulla PlayArea e ottiene tutti i simboli normali che si vedono
		
	lista1ContenutaInLista2
		restituisce true se la lista 1 è contenuta totalmente in lista 2
		
	verifyGoldCardRequirements
	
	getSortedPlayArea
	
			
 */

package game;

import java.util.ArrayList;
import cards.Card;
import cards.CardType;
import cards.Corner;
import cards.CornerPosition;
import cards.CornerState;
import cards.SpecialSymbol;
import initialCard.InitialCard;
import cards.Coordinates;
import cards.Symbol;
import goldCard.GoldCard;
//import java.util.*;
/**
 * La classe PlayArea rappresenta l'area di gioco in cui vengono collocati elementi come simboli, carte, 
 * angoli speciali e altri oggetti di gioco. Questa classe fornisce metodi per gestire, ordinare e verificare
 * gli elementi posizionati nella PlayArea, incluse funzionalità come ordinamento, verifica dei requisiti di carte,
 * ricerca di simboli e piazzamento di carte.
 */
public class PlayArea {
    
    private ArrayList<PlayAreaItem> l_PlayAreaCorners; //Contiene gli oggetti della PlayArea
    private ArrayList<Symbol> l_Symbols; // Simboli: sono i 4 regni.
    private ArrayList<SpecialSymbol> l_SpecialSymbols; // elencco dei simboli speciali contenuti nella playarea
    private ArrayList<Card> l_Cards; // Carte contenute nella playarea
    

    // Costruttore
    public PlayArea() {
        l_PlayAreaCorners = new ArrayList<>(); // Inizializza la lista
    }
    
    /**
     * Restituisce la lista di elementi della PlayArea.
     * @return Un'ArrayList di PlayAreaItem che rappresenta tutti gli oggetti nella PlayArea.
     */
    public ArrayList<PlayAreaItem> getPlayArea() {
        return l_PlayAreaCorners;
    }
    /**
     * Restituisce la lista di elementi della PlayArea ordinati per coordinate.
     * 
     * @return Un'ArrayList ordinata di PlayAreaItem.
     */
    // get della lista ORDINATA, parte dagli elementi più in alto a sinistra a quelli più in basso a destra dando precedenza alla Y
    public ArrayList<PlayAreaItem> getSortedPlayArea() {
    	// NB: Creo una "Shallow copy" di PlayArea, quindi PlayArea resta invariata se modifico l_SortedPlayAreaCorners
    	// MA se modifico GLI ELEMENTI di l_SortedPlayAreaCorners anche gli elementi di PlayArea saranno modificati!
    	// Se cancello un elemento dalla lista l_SortedPlayAreaCorners, PlayArea resterà invariata, perchè cancellare un elemento dalla lista
    	// vuol dire cancellare il puntatore a quell'elemento
        ArrayList<PlayAreaItem> l_SortedPlayAreaCorners = new ArrayList<>(getPlayArea());

        // Bubble sort per ordinare la lista
        for (int i = 0; i < l_SortedPlayAreaCorners.size() - 1; i++) {
        	
            for (int j = 0; j < l_SortedPlayAreaCorners.size() - 1 - i; j++) {
            	
            	PlayAreaItem PAI_0 = l_SortedPlayAreaCorners.get(j);
            	PlayAreaItem PAI_1 = l_SortedPlayAreaCorners.get(j+1);
            	
                Coordinates C_J0 = PAI_0.getCoordinates();
                Coordinates C_J1 = PAI_1.getCoordinates();

                // Confronta prima per y (ordine decrescente)
                if (C_J0.getY() < C_J1.getY() || (C_J0.getY() == C_J1.getY() && C_J0.getX() > C_J1.getX())) {
                    // Scambia gli elementi se c1 deve andare dopo c2
                	l_SortedPlayAreaCorners.set(j, PAI_1);
                	l_SortedPlayAreaCorners.set(j + 1, PAI_0);
                }
            }
        }    	
        return l_SortedPlayAreaCorners;
    }
    /**
     * Verifica i requisiti delle carte d'oro presenti nella PlayArea confrontandoli con i simboli presenti.
     * 
     * @param card La carta d'oro di cui si vogliono verificare i requisiti.
     * @return True se i requisiti sono soddisfatti, False altrimenti.
     */
    
    // Verifica i requirements delle golden cards
    public boolean verifyGoldCardRequirements (GoldCard card) {
    	if (lista1ContenutaInLista2(card.createRequirementsForPoints(),this.getSymbolList())) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    /**
     * Restituisce le coordinate degli oggetti presenti nella PlayArea.
     * 
     * @return Un'ArrayList di Coordinates che rappresenta tutte le coordinate occupate.
     */
    public ArrayList<Coordinates> getOccupiedCoordinates(){
    	ArrayList<Coordinates> occupiedCoordinates = new ArrayList<>();
    	for (PlayAreaItem ii : l_PlayAreaCorners) {
    		occupiedCoordinates.add(ii.getCoordinates());
    	}
    	return occupiedCoordinates;    	
    }
    /**
     * Restituisce la lista dei simboli presenti nella PlayArea.
     * I simboli rappresentano i regni e vengono cercati tra i corner delle carte.
     * 
     * @return Un'ArrayList di Symbol presenti nella PlayArea.
     */
    
    // Metodo che fruga in tutta la PlayArea e cerca i Symbol 
    public ArrayList<Symbol> getSymbolList () { // 
    	
    	l_Symbols = new ArrayList<Symbol>();
    	l_Cards = new ArrayList<Card>();
    	
    	for (PlayAreaItem item : l_PlayAreaCorners) {    		
    		if (!item.isCovered()) {
    			if (item.getCorner().getState() == CornerState.SYMBOL && (item.getCorner().getSymbol() instanceof Symbol)) {
    				l_Symbols.add((Symbol) item.getCorner().getSymbol());
    			}
    		}
    		// Controllo che la carta non sia già nella lista di carte, se non c'è la inserisco
    		if (!l_Cards.contains(item.getCard())) {
    			l_Cards.add(item.getCard());
    		}
    	}    	
    	// ciclo sulla lista di carte
    	for (Card item : l_Cards) {
    		// Controllo che la carta abbia "centralSymbol"
    		if (item.hasCentralSymbol()) {
    			// Se sì aggiungo il simbolo alla lista dei miei simboli
    			if (item.getType() == CardType.STARTING) {
    				l_Symbols.addAll(item.addCentralSymbol());    			
	    		} else {
	    			l_Symbols.add(item.getKingdom());   
	    		}
    		}
    	}    	
    	return l_Symbols;
    }
    /**
     * Restituisce la lista dei simboli speciali nella PlayArea.
     * I simboli speciali includono oggetti come calamaio, piuma e manoscritto.
     * 
     * @return Un'ArrayList di SpecialSymbol presenti nella PlayArea.
     */
    
    // Metodo che fruga in tutta la PlayArea e cerca i SpecialSymbol (che sono solo calamaio, piuma e manoscritto)
    // Gli SpecialSymbols sono solo sui corners delle carte, mai al centro
    public ArrayList<SpecialSymbol> getSpecialSymbolList () { //
    	boolean debugMode = false;
    	l_SpecialSymbols = new ArrayList<SpecialSymbol>();
    	
    	for (PlayAreaItem item : l_PlayAreaCorners) {    		
    		if (!item.isCovered()) {
    			if (debugMode) {System.out.print("Il corner ( " + item.getCoordinates().getX() + " , "+ item.getCoordinates().getY() + " )");
    							System.out.println("\tè di tipo: "+item.getCorner().getState()+" e contiene " + item.getCorner().getSymbol());
    			}
    			if (item.getCorner().getState() == CornerState.SPECIALSYMBOL && (item.getCorner().getSymbol() instanceof SpecialSymbol)) {
    				l_SpecialSymbols.add((SpecialSymbol) item.getCorner().getSymbol());
    			}
    		}
    	}    	

    	return l_SpecialSymbols;
    }
    /**
     * Determina le coordinate valide in cui un angolo di una carta può essere piazzato nella PlayArea.
     * 
     * @param card La carta che si desidera piazzare.
     * @param givenCornerPosition La posizione dell'angolo della carta da piazzare.
     * @return Un'ArrayList di Coordinates che rappresenta le posizioni valide.
     */
    // Metodo per ottenere dove un dato corner può essere piazzato
    public ArrayList<Coordinates> validPlacementsForGivenCorner(Card card, CornerPosition givenCornerPosition) {
        ArrayList<Coordinates> viableCoordinates = new ArrayList<>();
        boolean debugMode = false;

        // Passo 1.1: Ciclo su tutta la PlayArea
        for (PlayAreaItem ii_playAreaItem : l_PlayAreaCorners) {
        	boolean coordinatesAreViable = true;
        	
        	if (debugMode) {System.out.println("\tCONTROLO: ("+ii_playAreaItem.getCoordinates().getX()+", "+ii_playAreaItem.getCoordinates().getY()+")");}
        	
        	// Ciclo su tutte le posizioni sulle quali verrebbe piazzato il corner.
        	for (Corner cornerCarta : card.addCorners()) {
        		//
        		// Coordinate sulle quali piazzo il corner scelto
        		// Più coordinate relative rispetto al corner scelto
        		int x_relativaCorner = cornerCarta.getPosition().getXOffset() - givenCornerPosition.getXOffset();
        		int y_relativaCorner = cornerCarta.getPosition().getYOffset() - givenCornerPosition.getYOffset();
        		int x_carta = ii_playAreaItem.getCoordinates().getX() + x_relativaCorner;
        		int y_carta = ii_playAreaItem.getCoordinates().getY() + y_relativaCorner;
        		
        		PlayAreaItem cornerPlayArea = getPlayAreaCorner(new Coordinates(x_carta,y_carta));
        		
        		// Se non trovo niente in quelle coordinate allora vanno bene punto
        		if (debugMode) {}//{System.out.println("Controllo se esiste un corner in x= " + x_carta + " ,y= " + y_carta);}
        		if (null == cornerPlayArea) {
		        	continue;
        		}
        		
		        // Controllo se l'angolo corrente è diverso da quello dato
        		CornerPosition cornerPositionPlayArea = cornerPlayArea.getCornerPosition();
        		CornerPosition cornerPositionCarta = cornerCarta.getPosition();
        		if (debugMode) {System.out.println("PositionPlayArea= " + x_carta+", "+y_carta + " ,positionCarta= " + cornerPositionCarta);}
        		if (debugMode) {System.out.println("cornerPositionPlayArea= " + cornerPositionPlayArea + " ,cornerPositionCarta= " + cornerPositionCarta);}
		        if (cornerPositionPlayArea.getOpposite() != cornerPositionCarta) {
		        	if (debugMode) {System.out.println("CornerPositions incompatibili a causa dei corners non opposti");}
		        	coordinatesAreViable = false;
		        	break;
		        }
		        
		        // Controllo che l'angolo sul quale sto per piazzare l'angolo della carta che ho deciso di piazzare non sia NULL:		        
		        if (cornerPlayArea.getPlayAreaItemCorner().getState() == CornerState.NULL) {
		        	if (debugMode) {System.out.println("CornerPositions incompatibili a causa del null sulla playarea");}
		        	coordinatesAreViable = false;
		        	break;
		        }		        		        
            }
        	
        	if (debugMode) {System.out.println("Ho appena controllato le coordinate play area: " + ii_playAreaItem.getCoordinates());}
        	if (coordinatesAreViable) {
        		viableCoordinates.add(ii_playAreaItem.getCoordinates());
        	}
        }
        return viableCoordinates;
    }
    /**
     * Aggiunge un corner alla PlayArea nelle coordinate specificate.
     *
     * @param coordinates Le coordinate in cui aggiungere il corner.
     * @param corner Il corner da aggiungere.
     * @param card La carta che contiene il corner.
     * @return true se l'operazione è riuscita.
     * @throws IllegalArgumentException se uno degli oggetti passati è null.
     */ 
    
    //Metodo per aggiungere una carta alla PlayArea:
    // Il metodo NON controlla che le coordinate siano valide!!!!
    public boolean placeCornerOnXYCoordinates (Coordinates coordinates ,Corner corner, Card card) {   	
    	
    	// Copro il corner che si trovava già in queste coordinate: ciclo sulle vecchie coordinate
    	for (PlayAreaItem oldPlayAreaCorner : l_PlayAreaCorners) {
    		
    		// Controllo se le coordinate corrispondono
	        if (oldPlayAreaCorner.coordinates.getX()==coordinates.getX() && oldPlayAreaCorner.coordinates.getY()==coordinates.getY()) {  
	            oldPlayAreaCorner.covered = true;  // Segno come coperto il corner esistente
	        }
    	}
      	   	
    	// Inizializzo il PlayAreaItem e BAM
    	PlayAreaItem playAreaObject;    	
    	playAreaObject = new PlayAreaItem(coordinates, corner, card);    	
        l_PlayAreaCorners.add(playAreaObject);
        return true;
    }
    /**
     * Ottiene l'elemento PlayAreaItem associato a specifiche coordinate.
     * Se esiste un elemento non coperto nelle coordinate, viene restituito, altrimenti null.
     *
     * @param givenCoordinates Le coordinate dell'elemento da cercare.
     * @return L'elemento PlayAreaItem corrispondente o null se non trovato.
     */
    // Metodo per estrarre dalla lista l_PlayAreaCorners date le coordinate
    public PlayAreaItem getPlayAreaCorner(Coordinates givenCoordinates) {
        for (PlayAreaItem item : l_PlayAreaCorners) {
            Coordinates coordinates = item.getCoordinates();
            
            if (!item.isCovered()) {
	            if (coordinates.getX() == givenCoordinates.getX() && coordinates.getY() == givenCoordinates.getY()) {
	                return item;  // Oggetto trovato
	            }
            }
        }
        return null;  // Coordinate non trovate
    }
    
    /**
     * Aggiunge una carta iniziale alla PlayArea.
     * Vengono posizionati i quattro corner della carta nelle coordinate di partenza (0,0), (0,1), (1,1), (1,0).
     *
     * @param card La carta iniziale da posizionare.
     */
    
    // Metodo per aggiungere la carta iniziale
    public void placeInitialCard(InitialCard card) {
    	System.out.println("Carta iniziale numero: " + card.getNumber());
        for (Corner ii : card.addCorners()) {  // Ciclo sui 4 corners della carta
            
            PlayAreaItem playAreaObject;
            
            // Creazione del nuovo oggetto Coordinates per ogni posizione
            switch (ii.getPosition()) {
                case BOTTOM_LEFT:
                    playAreaObject = new PlayAreaItem(new Coordinates(0, 0), ii, card);
                    l_PlayAreaCorners.add(playAreaObject);
                    break;
                case TOP_LEFT:
                    playAreaObject = new PlayAreaItem(new Coordinates(0, 1), ii, card);
                    l_PlayAreaCorners.add(playAreaObject);
                    break;
                case TOP_RIGHT:
                    playAreaObject = new PlayAreaItem(new Coordinates(1, 1), ii, card);
                    l_PlayAreaCorners.add(playAreaObject);
                    break;
                case BOTTOM_RIGHT:
                    playAreaObject = new PlayAreaItem(new Coordinates(1, 0), ii, card);
                    l_PlayAreaCorners.add(playAreaObject);
                    break;
                default:
                    System.out.println("Posizione sconosciuta: " + ii.getPosition());
                    break;
            }
        }
    }

    /**
     * Stampa le informazioni di tutti gli elementi presenti nella PlayArea.
     * Viene stampata una rappresentazione dettagliata di ogni PlayAreaItem e la lista dei simboli presenti.
     */
    // Metodo per stampare tutte le informazioni della PlayArea
    public void printPlayArea() {
        for (PlayAreaItem item : getPlayArea()) {
            System.out.println(item.printPlayAreaItem());
        }        
        System.out.println("Simboli nella PlayArea:");
        System.out.println(getSymbolList());
        System.out.println("Simboli speciali nella PlayArea:");
        System.out.println(getSpecialSymbolList());
    }
    
    /**
     * Stampa le informazioni della PlayArea ordinata.
     * Gli elementi vengono ordinati prima di essere stampati, insieme ai simboli presenti e speciali.
     */
    
    public void printSortedPlayArea() {
        for (PlayAreaItem item : getSortedPlayArea()) {
            System.out.println(item.printPlayAreaItem());
        }        
        System.out.println("Simboli nella PlayArea:");
        System.out.println(getSymbolList());
        System.out.println("Simboli speciali nella PlayArea:");
        System.out.println(getSpecialSymbolList());}
    /**
     * Rappresenta un elemento della PlayArea, contenente informazioni sulle coordinate, il corner e la carta associata.
     */
    // Classe interna per rappresentare un elemento nella PlayArea
    public class PlayAreaItem {
        private Coordinates coordinates;  // Sostituzione di x e y con l'oggetto Coordinates
        private Corner corner;
        private CornerPosition cornertype;
        private boolean covered;
        private Card card;
        private String simboloGenerale; // Include tutti i possibili simboli che possono essere presenti in un corner
        /**
         * Costruisce un nuovo PlayAreaItem.
         *
         * @param coordinates Le coordinate in cui si trova l'elemento.
         * @param corner Il corner associato all'elemento.
         * @param card La carta che contiene il corner.
         * @throws IllegalArgumentException se il corner o la carta sono null.
         */
        // Costruttore per PlayAreaItem che accetta l'oggetto Coordinates
        public PlayAreaItem(Coordinates coordinates, Corner corner, Card card) {
            if (corner == null || card == null) {
                throw new IllegalArgumentException("Corner or Card cannot be null.");
            }
            this.coordinates = coordinates;
            this.corner = corner;
            this.cornertype = corner.getPosition();
            this.covered = false;
            this.card = card;
            this.simboloGenerale = card.getAbbreviatedCorner(cornertype);
        }
        /**
         * Restituisce una rappresentazione stringa dettagliata dell'elemento della PlayArea.
         *
         * @return Una stringa contenente informazioni su coordinate, corner e stato dell'elemento.
         */
	    // metodo per una visualizzazione della PlayArea
	    public String printPlayAreaItem() {
	        return 	"PAI Coord: " + coordinates + ", " +
	        		"Cov: " + covered + ", " +
	        		"CorType: " + cornertype + ", " +
	        		"CorState: " + simboloGenerale + ", " +
	        		"Card: " + card.getNumber();
	    }
	    /**
	     * Restituisce la posizione del corner.
	     *
	     * @return La posizione del corner (es. TOP_LEFT, BOTTOM_RIGHT).
	     */
	    public CornerPosition getCornerPosition() {
	    	return this.cornertype;
	    }
	    /**
	     * Restituisce le coordinate nella PlayArea.
	     *
	     * @return Le coordinate 
	     */
	    
	    public Coordinates getCoordinates() {
	    	return this.coordinates;
	    }
	    /**
	     * Verifica se il corner è coperto da un altro elemento.
	     *
	     * @return true se l'elemento è coperto, false altrimenti.
	     */
	    public boolean isCovered() {
	    	return this.covered;
	    }
	    /**
	     * Restituisce il corner 
	     *
	     * @return Il corner 
	     */
	    public Corner getPlayAreaItemCorner() {
	    	return this.corner;
	    }
	    /**
	     * Restituisce la carta associata 
	     *
	     * @return La carta 
	     */
	    public Card getCard() {
	    	return this.card;
	    }
	    /**
	     * Restituisce il simbolo generale associato al corner.
	     *
	     * @return Il simbolo del corner.
	     */
	    public String getGeneralSymbol () {
	    	return this.simboloGenerale;
	    }
	    
	    public Corner getCorner() {
	    	return corner;
	    }
    }
    /**
     * Verifica se tutti gli elementi di una lista (lista1) sono presenti in un'altra lista (lista2).
     * 
     * @param <T> Il tipo di elementi nelle liste.
     * @param lista1 La lista da verificare.
     * @param lista2 La lista in cui verificare la presenza degli elementi di lista1.
     * @return true se tutti gli elementi di lista1 sono presenti in lista2, false altrimenti.
     */
    public static <T> boolean lista1ContenutaInLista2(ArrayList<T> lista1, ArrayList<T> lista2) {
        // copia della lista
        ArrayList<T> tempLista2 = new ArrayList<>(lista2);
        
        for (T item : lista1) {
            if (!tempLista2.contains(item)) {
                return false; // Se un elemento di lista1 non è presente in lista2, ritorna false
            } else {
                // Se l'elemento esiste, rimuovilo da tempLista2
                tempLista2.remove(item);
            }
        }        
        // Se tutti gli elementi di lista1 sono stati trovati, ritorna true
        return true;
    }
    /**
     * Stampa la griglia della PlayArea con bordi e simboli dei corner.
     * La griglia viene adattata alle dimensioni della PlayArea e contiene informazioni sui bordi e sui simboli presenti.
     */
    // Metodo per stampare la griglia in maniera decente
    public void printPlayAreaGrid() {

    	// Troviamo i limiti della griglia per la stampa (min e max per X e Y)
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (PlayAreaItem item : l_PlayAreaCorners) {
            int x = item.getCoordinates().getX();
            int y = item.getCoordinates().getY();
            if (x < minX) { minX = x; }
            if (y < minY) { minY = y; }
            if (x > maxX) { maxX = x; }
            if (y > maxY) { maxY = y; }
        }

        // Creiamo un array per memorizzare la griglia con simboli e bordi
        String[][] grid = new String[(maxY - minY + 1) * 2 + 1][(maxX - minX + 1) * 2 + 1];
        
        // Inizializza la griglia con spazi vuoti
        for (int i_y = 0; i_y < grid.length; i_y++) {
            for (int i_x = 0; i_x < grid[i_y].length; i_x++) {
                grid[i_y][i_x] = " "; 
            }
        }

        // Popola la griglia con i corner e i bordi
        for (PlayAreaItem item : l_PlayAreaCorners) {
            int x = item.getCoordinates().getX();
            int y = item.getCoordinates().getY();
            String symbol = item.getGeneralSymbol();
            String corner = item.getCornerPosition().toString();

            // Posizione dell'angolo nella griglia
            int gridX = (x - minX) * 2;
            int gridY = (y - minY) * 2;

            // Inserisco il simbolo del corner nella griglia
            grid[gridY][gridX] = symbol;
            if (item.getCoordinates().getX() == 0 && item.getCoordinates().getY() == 0) {grid[gridY][gridX] = symbol+"_0";}

            // Piazzo i bordini
            if (corner.equals("TOP_LEFT")) {
                if (gridY - 1 >= 0) { // Bordo verticale (sotto di me)
                    grid[gridY - 1][gridX] = " |";
                }
                if (gridX + 1 < grid[0].length) { // Bordo orizzontale (a destra di me)
                    grid[gridY][gridX + 1] = "---";
                }
            } 
            else if (corner.equals("TOP_RIGHT")) {
                if (gridY - 1 >= 0) { // Bordo verticale (sotto di me)
                    grid[gridY - 1][gridX] = " |";
                }
                if (gridX - 1 >= 0) { // Bordo orizzontale (a sinistra di me)
                    grid[gridY][gridX - 1] = "---";
                }
            }      
            else if (corner.equals("BOT_LEFT")) {
                if (gridY + 1 < grid.length) { // Bordo verticale (sopra di me)
                    grid[gridY + 1][gridX] = " |";
                }    
                if (gridX + 1 < grid[0].length) { // Bordo orizzontale (a destra di me)
                    grid[gridY][gridX + 1] = "---"; 
                }
                if (gridY + 1 < grid.length && gridY + 1 < grid.length) {
                	if (item.getCard().getType() == CardType.STARTING){
                		grid[gridY + 1][gridX+ 1] = "INI";
                	}
                	else {
                		grid[gridY + 1][gridX+ 1] = item.getCard().getKingdom().getAbbreviation();
                	}                	
                }
            }      
            else if (corner.equals("BOT_RIGHT")) {
                if (gridY + 1 < grid.length) { // Bordo verticale (sopra di me) 
                    grid[gridY + 1][gridX] = " |"; 
                }
                if (gridX - 1 >= 0) { // Bordo orizzontale (a sinistra di me)
                    grid[gridY][gridX - 1] = "---"; 
                }
            }

        }

        // Stampa la griglia
        for (int y = grid.length - 1; y >= 0; y--) {
            for (int x = 0; x < grid[y].length; x++) {
                System.out.print(grid[y][x] + "\t");
            }
            System.out.println();
        }
    }

    

    
}
