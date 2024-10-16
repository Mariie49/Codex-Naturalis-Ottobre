package cards;

import java.util.Random;

/**
 * Enum che rappresenta i diversi simboli che una carta può avere.
 */
// Enumerazione per i simboli normali (animale, fungo, insetto, vegetale)
public enum Symbol {
	PLANT_KINGDOM,
	ANIMAL_KINGDOM,
	FUNGI_KINGDOM,
	INSECT_KINGDOM;
	
	/**
     * Restituisce un simbolo casuale dall'enumerazione.
     *
     * @return un simbolo casuale.
     */
	public static Symbol getRandomSymbol() {
        Symbol[] Symbols = Symbol.values();
        Random random = new Random();
        int i = random.nextInt(Symbols.length);
        return Symbols[i];
    }
	

	 public String getAbbreviation() {
	        switch (this) {
	            case PLANT_KINGDOM:
	                return "P_K";
	            case ANIMAL_KINGDOM:
	                return "A_K";
	            case FUNGI_KINGDOM:
	                return "F_K";
	            case INSECT_KINGDOM:
	                return "I_K";   
	            default:
	                return name().charAt(0) + "_K"; 
	        }
	    } 
	

	
}