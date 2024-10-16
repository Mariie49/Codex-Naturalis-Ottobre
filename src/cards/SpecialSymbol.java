package cards;

import java.util.Random;

/**
 * Enum che rappresenta i simboli speciali che una carta può avere.
 */
public enum SpecialSymbol {
    INKWELL, MANUSCRIPT, QUILL;

    /**
     * Restituisce un simbolo speciale casuale dall'enum.
     *
     * @return un simbolo speciale casuale
     */
	public static SpecialSymbol getRandomSpecialSymbol() {
        SpecialSymbol[] specialSymbols = SpecialSymbol.values();
        Random random = new Random();
        int index = random.nextInt(specialSymbols.length);
        return specialSymbols[index];
    }
	 public String getAbbreviation() {
	        switch (this) {
	            case INKWELL:
	                return "INK";
	            case MANUSCRIPT:
	                return "MAN";
	            case QUILL:
	                return "QUI";
	            default:
	                return name().charAt(0) + "_O"; 
	        }
	    }
}