
package game;


import java.util.ArrayList;
/**
 * La classe MainGame è il punto di ingresso principale per l'esecuzione del gioco. 
 * Si occupa di creare una nuova partita, gestire i turni dei giocatori e determinare il vincitore.
 */

// Definisco la classe main game
public class MainGame {

	/* Questa definisce il metodo "main", che è il punto di ingresso del programma
	
	public: il metodo main pu� essere chiamato in qualsiasi parte del programma
	static: il metodo appartiene alla classe, e non ad una sua specifica istanza
		classe:
		istanza:
	void: è l'output del metodo in caso di return. Non restituisce nulla
	String[] args: parametro che permette di passare argomenti da linea di comando
		anche se non viene usato è obbligatorio, in sto caso non gli passo nulla.
	*/
	public static void main(String[] args) {
		// Creo la nuova istanza del gioco "Game"
		final int pointsToEnd = 2;
		
		Game match = new Game();
		match.setPointsToEnd(pointsToEnd);
		
		// Dichiaro una lista di giocatori. Ricordo che la lista � espandibile
        ArrayList<Player> players;
        
        // Booleano per gioco non terminato
        boolean lastRound = false;
        boolean gameEnded = false;
        // chiamo il metodo "startGame" dell'oggetto match che � la mia istanza
        // dell'oggetto "Game"
        match.startGame();
        

        players = match.getPlayerList();

        do { // fai questo ciclo finchè il gioco non è finito
            for (Player currentPlayer : players) { // cicla sui giocatori
            	if (!lastRound || !currentPlayer.isFirst()) { // Se non è l'ultimo turno oppure è l'ultimo turno e il giocatore non è quello che ha iniziato
            		
            		match.turn(currentPlayer);
            		lastRound = match.getLastRound(); // controllo il last round, perchè, se sono finiti entrambi i deck, viene settato a true in "Game"
            		
	            	if (currentPlayer.getPoints() >= pointsToEnd) { // Se il player ha superato i tot punti allora scatta l'ultimo turno
	                	lastRound = true;
	                	match.setLastRound(true);
	                }	
	            	
            	} else {
            		gameEnded = true;
            		break;
            	}
            }
        } while (!gameEnded);
        
        
        for (Player player : players) {
        	match.assignObjectiveCardPoints (player);
        }
        
        match.determineWinner();
        
        // Punti assegnati dalle carte obiettivo e numero di carte obiettivo raggiunte.
        
        players = match.rankings();
        for (Player currentPlayer : players) {
            System.out.println("Il giocatore " + currentPlayer.getName() + " ha raggiunto un punteggio di  " + currentPlayer.getPoints());
        }
        
        System.out.println("Il vincitore " + players.get(0).getName() + " con un punteggio di  " + players.get(0).getPoints());
    }
}


