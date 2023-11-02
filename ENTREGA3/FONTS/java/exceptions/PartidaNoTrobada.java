package exceptions;

import domain.Partida;

/**
 * Salta quan s'intenta continuar una partida que no ha estat 
 * guardada.
 * <p>
 * <strong>Author:</strong> Pol Contreras.
 * 
 * @version 31.5.2021
 * @author Pol
 */
public class PartidaNoTrobada extends Exception {
    public PartidaNoTrobada(Partida p) {
        super("La partida amb id = " + p.getID() + " no esta guardada");
    }
}