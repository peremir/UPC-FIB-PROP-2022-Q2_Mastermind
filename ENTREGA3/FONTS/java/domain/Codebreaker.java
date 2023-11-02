package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Codi.Colors;

/**
 * Representa el jugador amb rol CodeBreaker quan aquest és màquina.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * 
 * @author Aina Gomez Pinyol
 * @version 2.6.2023
 * @see <a
 *      href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Codebreaker.java>
 *      Codebreaker.java </a>
 */

public class Codebreaker {

    private List<List<Colors>> jugades = new ArrayList<>();
    private transient Estrategia estrategia;
    private Codi codi;

    /**
     * Constructora
     * 
     * @param fg Boolea que ens indica quina estrategia vol utilitzar el
     *           CodeBreaker.
     *           Si és TRUE farem servir l'algorisme de FiveGuess, si es FALSE
     *           utilitzarem l'algorisme Genètic.
     * @param c  Codi que conté les dades de la partida, com la dificultat, els
     *           colors
     *           disponibles i el nombre de posicions.
     */
    public Codebreaker(boolean fg, Codi c) {

        if (fg)
            estrategia = new FiveGuess(c);
        else
            estrategia = new Genetic(c);
        this.codi = c;
    }

    /**
     * Calcula la següent combinació a jugar.
     * 
     * @param cm Resposta obtinguda pel CodeMaker el torn anterior. Si és el primer
     *           torn, aquesta serà buida.
     * @return La combinació de colors que decideix jugar el CodeBreaker.
     * @throws Exception
     */
    public List<Colors> jugarTorn(List<Colors> cm) throws Exception {
        List<Colors> cb = new ArrayList<>();
        if (jugades.size() != 0) {
            int indexLastElement = jugades.size() - 1;
            cb = jugades.get(indexLastElement);
        }
        cb = estrategia.jugarTornMaquina(cm, cb);
        jugades.add(cb);
        return cb;
    }

    /**
     * Afegeix una jugada a la llista de jugades.
     * 
     * @param jugada Jugada a afegir
     */
    public void afegirJugada(List<Colors> jugada) {
        jugades.add(jugada);
    }

    /**
     * Retorna una possible solució al a jugar
     * 
     * @return Una possible solució al codi
     * @throws Exception
     */
    public List<Colors> getAjuda() throws Exception {
        List<Colors> cb = new ArrayList<>();
        List<Colors> cm = new ArrayList<>();
        if (jugades.size() != 0) {
            int indexLastElement = jugades.size() - 1;
            cb = jugades.get(indexLastElement);
            cm = this.codi.checkCodi(cb);
        }
        cb = estrategia.jugarTornMaquina(cm, cb);
        return cb;
    }
}
