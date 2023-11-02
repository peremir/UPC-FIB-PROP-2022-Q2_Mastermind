package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe que representa un gestor de ranking (no accedeix a la capa de
 * persistència)
 * <p>
 * <strong>Author:</strong> Pol Contreras
 * 
 * @version 1.6.2023
 * @author Pol
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Estadistiques.java">Estadistiques.java</a>
 */
public class Estadistiques {

    /**
     * Atributs de la classe
     */
    private List<Partida> ranking;

    /**
     * Constructora de la classe
     */
    public Estadistiques() {
        this.ranking = new ArrayList<Partida>();
    }

    /**
     * Mètode que afegeix una partida al ranking
     * 
     * @param p partida que es vol afegir al ranking
     */
    public void afegirPartida(Partida p) {
        if (p.partidaAcabada()) {
            ranking.add(p);
            Collections.sort(ranking, new Ordena());
        }
    }

    /**
     * Mètode que esborra totes les dades del ranking de partides actual
     */
    public void reiniciarRanking() {
        ranking.clear();
    }

    /**
     * Mètode que retorna el ranking
     *
     * @return llista de partides amb totes les partides del ranking
     */
    public List<Partida> getRanking() {
        return ranking;
    }

    /**
     * Mètode que retorna el ranking de partides
     * 
     * @return matriu de Strings amb el ranking
     */
    public Object[][] getRankingPresentacio() {

        Object[][] taula = new Object[ranking.size()][5];

        int i = 0;
        for (Partida p : this.ranking) {
            taula[i][0] = p.getID();
            taula[i][1] = p.getRondes();
            taula[i][2] = p.getDificultat();
            if (!p.rol())
                taula[i][3] = "Codebreaker";
            else
                taula[i][3] = "Codemaker";
            taula[i][4] = p.getNom();

            ++i;
        }
        return taula;
    }

}

/**
 * Classe que implementa el mètode amb els criteris d'ordenació del ranking
 *
 * @version 1.6.2023
 * @author Pol
 */
class Ordena implements Comparator<Partida> {
    @Override
    public int compare(Partida p1, Partida p2) {
        float c1 = (float) p1.getPuntuacioHuma() / (float) p1.getPuntuacioMaquina();
        float c2 = (float) p2.getPuntuacioHuma() / (float) p2.getPuntuacioMaquina();

        if (p1.posicions() > p2.posicions())
            return -1;
        else if (p1.posicions() < p2.posicions())
            return 1;
        else {
            if (c1 > c2)
                return -1;
            else if (c1 < c2)
                return 1;
            else
                return p2.getRondes() - p1.getRondes();
        }
    }
}
