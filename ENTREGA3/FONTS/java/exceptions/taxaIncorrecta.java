package exceptions;

/**
 * Salta quan s'introdueix una taxa més gran que 1 o més petita
 * que 0.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol.
 * 
 * @version 31.5.2021
 * @author Aina Gomez Pinyol
 */
public class taxaIncorrecta extends Exception {

    public taxaIncorrecta(double t) {
        super("La taxa " + t + "no es correcta");
    }
}
