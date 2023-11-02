package exceptions;

/**
 * Salta si s'intenta guardar més partides de les permeses.
 * <p>
 * <strong>Author:</strong> Pol Contreras.
 * 
 * @version 31.5.2021
 * @author Pol
 */
public class LimitPartidesGuardades extends Exception {
    public LimitPartidesGuardades() {
        super("El sistema no pot guardar mes partides en aquest moment.");
    }
}
