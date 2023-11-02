package exceptions;

/**
 * Salta al definir el nombre de torns com un nombre imparell.
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas.
 * 
 * @version 31.5.2021
 * @author Gerard Oliva Viñas
 */
public class NombreTornsInvalid extends Exception {
    public NombreTornsInvalid(Integer torns) {
        super("El nombre de rondes ha de ser un nombre parell més gran o igual que 2, i has introduït: " + torns + ".");
    }
}
