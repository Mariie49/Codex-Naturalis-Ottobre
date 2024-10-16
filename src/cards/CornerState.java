package cards;


/**
 * Enumerazione che rappresenta lo stato di un angolo di una carta.
 * 
 * Gli stati disponibili per un angolo possono essere:
 * 
 *   NULL --> Stato nullo, nessun valore associato.
 *   EMPTY --> L'angolo è vuoto, senza simboli.
 *   SYMBOL --> L'angolo contiene un simbolo.
 *   SPECIALSYMBOL - L'angolo contiene un simbolo speciale.
 * 
 * Questi stati vengono utilizzati per determinare come si comporta un angolo nel gioco.
 */
public enum CornerState {
	NULL,
	EMPTY,
	SYMBOL,
	SPECIALSYMBOL
}