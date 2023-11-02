package domain;

import java.util.List;

/**
 * Interfície Maquina per a testejar els dos algorismes.
 * @author PROP
 * @version 2
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Maquina.java>Maquina.java</a>
 */

public interface Maquina {

    /**
     * Given the solution code, the solve operation uses one of the proposed
     * algorithms (either five guess or the genetic one) to create the list of codes
     * that will lead to the solution. If the algorithm is unable to find the
     * solution
     * in less than maxSteps steps, the returned list will contain a list composed
     * of
     * maxSteps codes.
     * @param solution Codi a encertar.
     * @return Totes les combinacions jugades per a trobar el codi 'solution'.
     */
    public List<List<Integer>> solve(List<Integer> solution) throws Exception;
}
