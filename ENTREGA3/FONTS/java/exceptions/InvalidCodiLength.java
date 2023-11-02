package exceptions;

/**
 * Salta si el nombre de posicions no és correcte.
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas.
 * 
 * @version 31.5.2021
 * @author Gerard Oliva Viñas
 */
public class InvalidCodiLength extends Exception {
    public InvalidCodiLength(int expected, int actual) {
        super("La longitud del codi ha de ser " + expected + " i has introduït:  " + actual + ".");
    }
}
