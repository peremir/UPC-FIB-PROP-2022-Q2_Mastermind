package exceptions;

/**
 * Salta quan s'intenta guardar una partida que ja ha estat guardada.
 * <p>
 * <strong>Author:</strong> Pol Contreras
 * 
 * @version 31.5.2021
 * @author Pol
 */
public class partidaJaGuardada extends Exception {
    public partidaJaGuardada() {
        super("Partida ja guardada");
    }
}
