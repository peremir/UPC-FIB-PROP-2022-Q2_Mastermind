package exceptions;

/**
 * Salta si una població supera el nombre d'individus permesos.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol.
 * 
 * @version 31.5.2021
 * @author Aina Gomez Pinyol
 */
public class massesIndividus extends Exception {

    public massesIndividus() {
        super("La poblacio supera el nombre maxim d'individus.");
    }
}
