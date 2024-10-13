// Dichiarazione l'appartenenza al pacchetto "game", che contiene altre classi 
package game;


import java.util.ArrayList;

// Definisco la classe main game, e fin qui...
public class MainGame {

	/* Questa definisce il metodo "main", che è il punto di ingresso del programma
	
	public: il metodo main può essere chiamato in qualsiasi parte del programma
	static: il metodo appartiene alla classe, e non ad una sua specifica istanza
		classe:
		istanza:
	void: è l'output del metodo in caso di return. Non restituisce nulla
	String[] args: parametro che permette di passare argomenti da linea di comando
		anche se non viene usato è obbligatorio, in sto caso non gli passo nulla.
	*/
	public static void main(String[] args) {
		// Creo la nuova istanza del gioco "Game"
		Game match = new Game();
		
		// Dichiaro una lista di giocatori. Ricordo che la lista è espandibile
        ArrayList<Player> players;
        
        // Booleano per gioco non terminato
        boolean gameNotEnded = true;
        // chiamo il metodo "startGame" dell'oggetto match che è la mia istanza
        // dell'oggetto "Game"
        match.startGame();
        

        players = match.getPlayerList();

        do {
            for (Player currentPlayer : players) {
                if (match.turn(currentPlayer)) {
                    gameNotEnded = false;

                }
            }
            
        } while (gameNotEnded);
        players = match.rankings();
        for (Player currentPlayer : players) {
            System.out.println("Il giocatore " + currentPlayer.getName() + " ha raggiunto un punteggio di  " + currentPlayer.totalPoints());
        }
        
        System.out.println("Il vincitore " + players.get(0).getName() + " con un punteggio di  " + players.get(0).totalPoints());
    }
}


