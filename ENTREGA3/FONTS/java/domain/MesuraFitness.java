package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

/**
 * En aquesta classe hi trobem definida la mesura de Fitness per al calcul
 * de l'algorisme genetic.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * 
 * @author Aina Gomez Pinyol
 * @version 31.5.2023
 * @see <a
 *      href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/MesuraFitness.java>
 *      MesuraFitness.java </a>
 */

public class MesuraFitness {

    private List<List<Colors>> jugadesAnteriors = new ArrayList<List<Colors>>();
    private List<List<Integer>> respostesAnteriors = new ArrayList<List<Integer>>();

    private Dificultat d;

    /**
     * Constructora.
     * 
     * @param dificultat: dificultat de la partida actual.
     */
    public MesuraFitness(Dificultat dificultat) {
        d = dificultat;
    }

    /**
     * Calcula la mesura de fitness d'un individu.
     * 
     * @param ind: individu del que volem calcular la mesura de fitness.
     * 
     * @return la mesura de fitness de l'individu 'ind'.
     */
    public int mesurarFitness(Individu ind) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        int fitness = 0;
        Codi combInd = new Codi(d);
        combInd.setCodi(ind.getCombinacio());

        for (int i = 0; i < jugadesAnteriors.size(); ++i) {

            List<Colors> resposta = combInd.checkCodi(jugadesAnteriors.get(i));
            List<Integer> respostaInt = respostaToInt(resposta);

            int DiferenciaBlancs = Math.abs(respostaInt.get(0) - respostesAnteriors.get(i).get(0));
            int DiferenticaNegres = Math.abs(respostaInt.get(1) - respostesAnteriors.get(i).get(1));

            fitness += DiferenciaBlancs + DiferenticaNegres;
        }
        return fitness;
    }

    /**
     * Passa una llista que conte només els colors BLANC i NEGRE a una llista
     * d'enters amb el format (#BLANCS, #NEGRES).
     * 
     * @param resposta: llista que conte els colors BLANC i NEGRE.
     * 
     * @return llista d'enters on: el primer element es el nombre de BLANCS de
     *         'resposta' i, on el segon, el nombre de NEGRES.
     * 
     * @throws InvalidCodiColors: si 'resposta' conte un color diferent a BLANC o
     *                            NEGRE.
     */
    private List<Integer> respostaToInt(List<Colors> resposta) throws InvalidCodiColors {

        List<Integer> respostaInt = new ArrayList<>();

        int nblancs = 0;
        int nnegres = 0;

        for (int i = 0; i < resposta.size(); ++i) {

            if (resposta.get(i).equals(Colors.NEGRE))
                ++nnegres;
            else if (resposta.get(i).equals(Colors.BLANC))
                ++nblancs;
            else
                throw new InvalidCodiColors(resposta.get(i));
        }
        respostaInt.add(nblancs);
        respostaInt.add(nnegres);

        return respostaInt;
    }

    /**
     * Afegeix un element a l'atribut 'respostesAnteriors'.
     * 
     * @param resposta: combinacio dels colors BLANC i NEGRE.
     */
    public void setResposta(List<Colors> resposta) throws InvalidCodiColors {

        List<Integer> respostaInt = respostaToInt(resposta);
        respostesAnteriors.add(respostaInt);
    }

    /**
     * Afegeix una combinacio a l'atribut 'jugadesAnteriors'.
     * 
     * @param jugada: combinacio de colors.
     * 
     * @throws InvalidCodiLength si la combinacio que volem guardar no te les
     *                           mateixes posicions que les que pertanyen al conjunt
     *                           'jugadesAnteriors'.
     */
    public void setJugada(List<Colors> jugada) throws InvalidCodiLength {

        if (!jugadesAnteriors.isEmpty() && jugada.size() != jugadesAnteriors.get(0).size()) {
            throw new InvalidCodiLength(jugadesAnteriors.get(0).size(), jugada.size());
        }
        jugadesAnteriors.add(jugada);
    }
}
