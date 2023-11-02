package domain;

import java.util.List;

import domain.Codi.Colors;

/**
 * Interficie on es defineixen els procediments dels dos algoritmes per la resolució
 * de cada torn.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * @author Aina Gomez Pinyol
 * @version 2.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Estrategia.java>Estrategia.java</a>
 */

public interface Estrategia {

    /**
     * Calcula la següent millor combinació a jugar.
     * @param response Resposta obtinguda pel CodeMaker.
     * @param lastTorn Combinació jugada al torn anterior.
     * @return Combinació amb més probabilitats d'encertar.
     * @throws Exception
     */
    public List<Colors> jugarTornMaquina(List<Colors> response, List<Colors> lastTorn) throws Exception;

}