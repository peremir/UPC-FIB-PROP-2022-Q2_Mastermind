package exceptions;

import domain.Codi.Colors;

/**
 * Salta si un color és invàlid. És a dir, no es troba com a disponible a la
 * dificultat sel·leccionada.
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas.
 * @version 31.5.2021
 * @author Gerard Oliva Viñas
 */
public class InvalidCodiColors extends Exception {
    public InvalidCodiColors(Colors color) {
        super("El color " + color + " no està disponible per aquesta dificultat.");
    }
}
