package exceptions;

import domain.Codi.Colors;

public class InvalidCodiColors extends Exception {
    public InvalidCodiColors(Colors color) {
        super("El color " + color + " no està disponible per aquesta dificultat.");
    }
}
