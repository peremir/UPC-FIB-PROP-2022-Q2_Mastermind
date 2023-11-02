package exceptions;

import domain.Partida;

public class PartidaNoTrobada extends Exception {
    public PartidaNoTrobada(Partida p) {
        super("La partida amb id = " + p.getID() + " no esta guardada");
    }
}