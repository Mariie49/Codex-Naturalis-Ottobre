package cards;

import java.util.Random;

/**
 * Enum representing the special symbols that a card can have.
 */
// Enumerazione per i simboli speciali
public enum SpecialSymbol {
	INKWELL, MANUSCRIPT, QUILL;
	/**
     * Returns a random special symbol from the enum.
     *
     * @return a random special symbol
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