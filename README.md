Questo progetto è un'implementazione in Java del gioco da tavolo Codex Naturalis. 

Caratteristiche
•	Regole di Codex Naturalis: Il gioco simula le regole ufficiali di Codex Naturalis, incluse le diverse tipologie di (risorsa, oro, obiettivo), i requisiti di piazzamento e il sistema di punteggio basato su obiettivi.
•	Interfaccia a linea di comando: L'interazione con il gioco avviene tramite un'intuitiva interfaccia a linea di comando, che guida i giocatori attraverso le scelte e le azioni disponibili.
•	Gestione dei turni e fine partita: Il gioco gestisce automaticamente i turni dei giocatori, assicurando che tutti abbiano la stessa opportunità di completare il proprio manoscritto. La partita termina quando un giocatore raggiunge 20 punti o quando le carte a disposizione si esauriscono, garantendo un ultimo round a tutti i giocatori.
•	Visualizzazione del manoscritto: Il gioco offre una chiara visualizzazione del manoscritto di ciascun giocatore all'inizio e alla fine di ogni turno, permettendo di pianificare le mosse e valutare l'avanzamento degli avversari.
•	Calcolo dei punteggi e determinazione del vincitore: Al termine della partita, il gioco calcola automaticamente i punteggi finali dei giocatori, tenendo conto degli obiettivi completati e delle risorse accumulate, e dichiara il vincitore.

Requisiti
•	Java Development Kit (JDK): Assicurati di avere installato un JDK compatibile (versione 8 o superiore).
Come eseguire il gioco
1.	Compilazione: Compila il codice sorgente Java utilizzando un compilatore come javac.
2.	Esecuzione: Esegui il file .class compilato utilizzando il comando java.
   
Come giocare
1.	Avvio del gioco: All'avvio, il gioco chiederà il numero di giocatori (da 2 a 4) e i loro nomi.
2.	Svolgimento dei turni: Ad ogni turno, il giocatore dovrà scegliere una carta dalla propria mano o dalle carte visibili sul tavolo, e posizionarla sul proprio manoscritto rispettando le regole di piazzamento diagonale. Successivamente, dovrà pescare una nuova carta per rimpiazzare quella giocata.
3.	Fine del gioco: La partita termina quando un giocatore raggiunge 20 punti o quando le carte disponibili si esauriscono. A questo punto, il gioco calcola i punteggi finali e dichiara il vincitore.
Struttura del progetto
•	game: Contiene la classe principale Game che gestisce la logica del gioco, i turni, i punteggi e la visualizzazione.
•	cards: Contiene le classi necessarie a rappresentare le carte (risorsa, oro, obiettivo).
•	initialCard: Contiene la classe che rappresenta la carta iniziale del manoscritto.
•	objectivecards: Contiene le classi che rappresentano le carte obiettivo.
•	resourceCard: Contiene le classi che rappresentano le carte risorsa.
•	goldCard: Contiene le classi che rappresentano le carte oro.

Note aggiuntive
•	Implementazione in corso: Alcune funzionalità del gioco potrebbero essere ancora in fase di sviluppo o non completamente implementate.


"Questo progetto digitale è stato realizzato esclusivamente per scopi educativi e non commerciali. Codex Naturalis è un prodotto protetto da copyright, e tutti i diritti relativi al nome, 
ai contenuti e alle immagini di Codex Naturalis sono di proprietà dei legittimi proprietari. L'utilizzo di Codex Naturalis in questo progetto è inteso unicamente a fini didattici 
e non implica alcuna affiliazione o approvazione da parte dei proprietari del copyright."
