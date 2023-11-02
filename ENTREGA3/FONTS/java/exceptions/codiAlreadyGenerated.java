package exceptions;

/**
 * Salta si el codi ja ha estat generat
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * @version 31.5.2021
 * @author Gerard Oliva Viñas
 */
public class codiAlreadyGenerated extends Exception {
    public codiAlreadyGenerated() {
        super("El codi ja ha estat generat");
    }
}
