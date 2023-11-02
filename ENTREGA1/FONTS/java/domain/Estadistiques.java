package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import domain.Codi.Colors;

public class Estadistiques {

    // Atributs
    private List<Partida> ranking;

    // CONSTRUCTORA
    public Estadistiques() {
        this.ranking = new ArrayList<Partida>();
    }

    /*
     * AFEGIR PARTIDA
     * pre: La partida p ha acabat
     * post: S'afegeix p al ranking de partides a la seva posicio corresponent
     */
    public void afegirPartida(Partida p) {
        if (p.partidaAcabada()) {
            ranking.add(p);
            Collections.sort(ranking, new Ordena());
        }
    }

    /*
     * REINICIAR RANKING
     * pre:
     * post: La taula de records torna a estar buida
     */
    public void reiniciarRanking() {
        ranking.clear();
    }

    // GETTER DE RANKING
    public List<Partida> getRanking() {
        return ranking;
    }

}

class Ordena implements Comparator<Partida> {
    @Override
    public int compare(Partida p1, Partida p2) {
        List<Colors> c1 = p1.getCodi();
        List<Colors> c2 = p2.getCodi();

        if (c1.size() == c2.size()) {
            return p1.getTornActual() - p2.getTornActual();
        } else
            return c2.size() - c1.size();
    }
}
