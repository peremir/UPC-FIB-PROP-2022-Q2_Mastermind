package domain;

import java.util.ArrayList;
import java.util.List;

import exceptions.PartidaNoTrobada;

/**
 * Classe que representa un gestor de partides guardades (no accedeix a la capa
 * de persistència)
 * <p>
 * <strong>Author:</strong> Pol Contreras
 * 
 * @version 1.6.2023
 * @author Pol
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/GestorPartides.java">GestorPartides.java</a>
 */
public class GestorPartides {

    /**
     * Atributs de la classe
     */
    private List<Partida> partides_guardades;

    /**
     * Constructora de la classe
     */
    public GestorPartides() {
        this.partides_guardades = new ArrayList<Partida>();
    }

    /**
     * Mètode que retorna les partides guardades
     *
     * @return llista de partides amb totes les partides del guardades
     */
    public List<Partida> getPartides() {
        return this.partides_guardades;
    }

    /**
     * Mètode que retorna una partida de la llista de partides guardades
     *
     * @param id identificador de la partida que es vol carregar
     * @return partida que es vol carregar
     */
    public Partida getPartida(Integer id) {
        return partides_guardades.get(id);
    }

    /**
     * Mètode que retorna totes les partides guardades
     * 
     * @return matriu de Strings amb les partides guardades
     */
    public Object[][] getPartidesPresentacio() {

        Object[][] taula = new Object[partides_guardades.size()][5];

        int i = 0;
        for (Partida p : this.partides_guardades) {
            taula[i][0] = p.getID();
            taula[i][1] = p.getRondes();
            taula[i][2] = p.getDificultat();
            if (p.rol()) {
                taula[i][3] = "Codemaker";
            } else {
                taula[i][3] = "Codebreaker";
            }
            taula[i][4] = p.getNom();

            ++i;
        }
        return taula;
    }

    /**
     * Mètode que guarda una partida a la llista de partides guardades
     * 
     * @param p partida que es vol guardar
     * @throws exceptions.LimitPartidesGuardades si ja hi ha 20 partides guardades
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

    /**
     * Mètode que carrega una partida de la llista de partides guardades
     * 
     * @param p partida que es vol carregar
     * @throws PartidaNoTrobada si la partida p no està guardada a la llista
     */
    public Partida carregarPartida(Partida p) throws PartidaNoTrobada {
        for (Partida p2 : partides_guardades) {
            if (p2.getDataIni() == p.getDataIni()) {
                return p2;
            }
        }

        throw new exceptions.PartidaNoTrobada(p);
    }

    /**
     * Mètode que elimina una partida en concret de la llista de partides guardades
     * 
     * @param p partida que es vol eliminar
     * @throws PartidaNoTrobada si la partida que es vol eliminar no es troba a la
     *                          llista de partides guardades
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