package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Codi.Colors;
import exceptions.InvalidCodiLength;
import exceptions.taxaIncorrecta;

/**
 * Representa els Individus de l'algorisme Genètic. Aquests són les combinacions
 * que es van generant.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * @author Aina Gomez Pinyol
 * @version 2.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Individu.java>Individu.java</a>
 */

public class Individu {

    private List<Colors> comb = new ArrayList<>();

    /**
     * Constructora.
     * @param combinacio Combinació de colors.
     */
    public Individu(List<Colors> combinacio) {
        
        comb = new ArrayList<>(combinacio);
    }

    /**
     * Constructora aleatòria (genera la combinació aleatòriament).
     * @param colors Colors disponibles a la partida actual.
     * @param npos Nombre de posicions per a la combinació.
     */
    public Individu(List<Colors> colors, int npos) {

        generarCombinacioRand(colors, npos);
    }

    private void generarCombinacioRand(List<Colors> colors, int npos) {

        for (int i = 0; i < npos; ++i) {
            int rand = (int) (Math.random() * colors.size());
            comb.add(colors.get(rand));
        }
    }

    /**
     * Consulta la combinació del p.i.
     * @return Combinació de colors.
     */
    public List<Colors> getCombinacio() {
        return comb;
    }

    /**
     * Recombina les dues combinacions de dos individus.
     * <p>
     * Segons el parametre 'taxaRecombinació', agafara el color del Individu A o
     * del Individu B i generara un nou Individu C.
     * @param taxaRecombinacio Taxa per sel·leccionar amb més o menys probabilitat
     *                         quins color seleccionar dels dos Individus. Aquesta
     *                         no pot ser més gran que 1.
     * @param Ind2 Combinació amb el que volem recombinar la combinació del p.i.
     * @return Retorna l'Individu restant de recombinar les combinacions del p.i i 
     *         l''Ind2'.
     * @throws InvalidCodiLength
     * @throws taxaIncorrecta
     */
    public Individu recombinaIndividus(double taxaRecombinacio, List<Colors> Ind2) throws InvalidCodiLength, taxaIncorrecta {

        if (comb.size() != Ind2.size()) throw new InvalidCodiLength(comb.size(), Ind2.size());
        if (taxaRecombinacio > 1 || taxaRecombinacio < 0) throw new taxaIncorrecta(taxaRecombinacio);

        List<Colors> combChild = new ArrayList<>();

        for (int i = 0; i < comb.size(); ++i) {
            double rand = Math.random();

            if (rand <= taxaRecombinacio)
                combChild.add(comb.get(i));
            else
                combChild.add(Ind2.get(i));
        }
        Individu child = new Individu(combChild);

        return child;
    }

    /**
     * Muta la combinació d'un Individu.
     * <p>
     * És a dir, de determinats colors de la combinació, els canvia per uns generats
     * aleatòriament.
     * @param taxaMutacio Taxa per sel·leccionar amb més o menys probabilitat quants
     *                    colors generem aleatòriament de la combinació del p.i.
     * @param allColors Llistat amb tots els colors disponibles.
     * @return L'Individu restant de mutar la combinació del p.i.
     * @throws taxaIncorrecta
     */
    public Individu mutaIndividu(double taxaMutacio, List<Colors> allColors) throws taxaIncorrecta {

        if (taxaMutacio > 1 || taxaMutacio < 0) throw new taxaIncorrecta(taxaMutacio);
        List<Colors> combMutada = new ArrayList<>();

        for (int i = 0; i < comb.size(); ++i) {
            double rand = Math.random();

            if (rand <= taxaMutacio) {
                int randColor = (int) (Math.random() * allColors.size());
                combMutada.add(allColors.get(randColor));
            } else
                combMutada.add(comb.get(i));
        }
        Individu mutat = new Individu(combMutada);
        return mutat;
    }

    /**
     * Permuta la combinació d'un Individu.
     * <p>
     * És a dir, intercanvia la posició de certs colors dintre la combinació.
     * @param taxaPermutacio Taxa que permet regular amb quina probabilitat 
     *                       intercanviem dos colors de la combinació.
     * @return L'Individu amb la combinació permutada.
     * @throws taxaIncorrecta
     */
    public Individu permutaIndividu(double taxaPermutacio) throws taxaIncorrecta {

        if (taxaPermutacio > 1 || taxaPermutacio < 0) throw new taxaIncorrecta(taxaPermutacio);

        List<Colors> combPermutada = new ArrayList<>(comb);

        for (int i = 0; i < comb.size(); ++i) {
            double rand = Math.random();

            if (rand <= taxaPermutacio) {

                int pos = (int) (Math.random() * comb.size());
                Colors aux = combPermutada.get(i);

                combPermutada.set(i, combPermutada.get(pos));
                combPermutada.set(pos, aux);
            }
        }
        Individu permutat = new Individu(combPermutada);
        return permutat;

    }

}
