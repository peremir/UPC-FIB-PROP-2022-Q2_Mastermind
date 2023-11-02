package exceptions;

/**
 * Salta si s'intenta jugar o continuar una partida que ja ha finalitzat.
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @version 31.5.2021
 * @author Gerard Oliva Viñas
 */
public class partidaAcabada extends Exception {
    public partidaAcabada() {
        super("La partida ja ha acabat.");
    }
}
