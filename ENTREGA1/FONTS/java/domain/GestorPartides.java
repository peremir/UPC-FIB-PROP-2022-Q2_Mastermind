package domain;

import java.util.ArrayList;
import java.util.List;

import exceptions.PartidaNoTrobada;

public class GestorPartides {

    // Atributs
    private List<Partida> partides_guardades;

    // CONSTRUCTORA
    public GestorPartides() {
        this.partides_guardades = new ArrayList<Partida>();
    }

    public List<Partida> getPartides() {
        return this.partides_guardades;
    }

    /*
     * GUARDAR PARTIDA
     * pre: El sistema te menys de 20 partides guardades
     * post: El sistema afegeix la partida p a la llista de partides guardades
     */
    public void guardarPartida(Partida p) throws exceptions.LimitPartidesGuardades {
        if (this.partides_guardades.size() >= 20)
            throw new exceptions.LimitPartidesGuardades();
        else {
            List<Partida> partides_a_eliminar = new ArrayList<Partida>();
            for (Partida p2 : this.partides_guardades) {
                if (p2.getDataIni() == p.getDataIni()) {
                    partides_a_eliminar.add(p2);
                }
            }
            this.partides_guardades.removeAll(partides_a_eliminar);
            this.partides_guardades.add(p);
        }
    }

    /*
     * CARREGAR PARTIDA
     * pre: La partida p esta a la llista de partides guardades
     * post: Retorna la partida p
     */
    public Partida carregarPartida(Partida p) throws PartidaNoTrobada {
        for (Partida p2 : partides_guardades) {
            if (p2.getDataIni() == p.getDataIni()) {
                return p2;
            }
        }

        throw new exceptions.PartidaNoTrobada(p);
    }

    /*
     * ELIMINAR PARTIDA
     * pre: La partida p esta a la llista de partides guardades
     * post: S'elimina la partida p de la llista de partides guardades
     */
    public void eliminarPartida(Partida p) throws PartidaNoTrobada {
        List<Partida> partides_a_eliminar = new ArrayList<Partida>();
        for (Partida p2 : this.partides_guardades) {
            if (p2.getDataIni() == p.getDataIni()) {
                partides_a_eliminar.add(p2);
            }
        }
        if (partides_a_eliminar.size() == 0)
            throw new exceptions.PartidaNoTrobada(p);
        else
            this.partides_guardades.removeAll(partides_a_eliminar);
    }
}