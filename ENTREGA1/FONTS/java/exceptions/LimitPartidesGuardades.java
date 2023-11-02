package exceptions;

public class LimitPartidesGuardades extends Exception {
    public LimitPartidesGuardades() {
        super("El sistema no pot guardar mes partides en aquest moment.");
    }
}
