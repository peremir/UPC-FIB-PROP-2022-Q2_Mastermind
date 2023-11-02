package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Codi.Colors;
import exceptions.massesIndividus;
import exceptions.noExisteixIndividu;

/**
 * Respresenta el conjunt d'Individus que va generant l'algorisme genetic
 * a cada generació.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * 
 * @author Aina Gomez Pinyol
 * @version 31.5.2023
 * @see <a href="https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Poblacio.java"> Poblacio.java</a>
 */

public class Poblacio {

    private int maxIndividus;
    private int nposicions;

    private List<Colors> availColors = new ArrayList<>();
    private List<Individu> individus = new ArrayList<>();

    /**
     * Constructora de la classe Població.
     * 
     * @param maxInds: nombre d'individus màxims que pot tenir la població.
     * @param codiPartida: codi de la partida actual, conté el nombre de posicions
     *                     i els colors disponibles. Aquests son sempre els mateixos
     *                     per tots els individus d'aquesta població.
     */
    public Poblacio(int maxInds, Codi codiPartida) {

        maxIndividus = maxInds;

        nposicions = codiPartida.getPosicions();
        availColors = codiPartida.getAvailColors();
    }

    /**
     * Inicialitza la població generant Individus aleatoris a partir del parametre
     * 'index', és a dir, inicialitza tots els Individus des de la variable 'index'
     * fins a l'últim element (maxIndividus).
     * 
     * @param index indica la posició del conjunt 'individus' per la que comencem a
     *              inicialitzar.
     * @throws noExisteixIndividu
     */
    public void inicialitzaPoblacio(int index) throws noExisteixIndividu {

        if (index > maxIndividus || index < 0) throw new noExisteixIndividu(index);

        int i = index;
        while (i < maxIndividus) {

            Individu individu = new Individu(availColors, nposicions);
            if (!conte(individu)) {
                individus.add(individu);
                ++i;
            }
        }
    }

    /**
     * Consulta si la combinació de l'Individu 'ind' forma part de la població 
     * del p.i.
     * 
     * @param Ind Individu inicialitzat.
     * @return TRUE si existeix un Individu en el p.i que conté la mateixa combinació
     * que l'Individu 'ind'. Altrament FALSE.
     */
    private boolean conte(Individu Ind) {

        boolean resultat = false;
        for (int i = 0; !resultat && i < individus.size(); ++i) {
            if (Ind.getCombinacio().equals(individus.get(i).getCombinacio())) {
                resultat = true;
            }
        }
        return resultat;
    }

    /**
     * Retorna l'Individu amb l'índex 'index' del p.i.
     * @param index L'index de l'Individu que volem obtenir.
     * @return Individu amb índex 'index'.
     * @throws noExisteixIndividu
     */
    public Individu getIndividu(int index) throws noExisteixIndividu {

        if (index > individus.size() || index < 0) throw new noExisteixIndividu(index);
        return individus.get(index);
    }

    /**
     * Retorna la mida del conjunt d'Individus del p.i.
     * @return mida del conjunt 'individus'.
     */
    public int mida() {
        return individus.size();
    }

    /**
     * Afegeix un Individu al p.i.
     * @param ind Individu que volem afegir al conjunt d'individus.
     * @return Per afegir un individu al conjunt s'han de complir les següents 
     * condicions: (1) no existeix cap Individu amb la mateixa combinació que 'ind' 
     * al p.i i (2) el conjunt d'individus no té la mida màxima permesa. 
     * <p>
     * Per tant, el resultat serà TRUE si es compleixen les condicions anteriors i, 
     * per tant, s'ha pogut afegit l'Individu. FALSE altrament.
     */
    public boolean add(Individu ind) {

        if (individus.size() <= maxIndividus && !conte(ind)) {
            individus.add(ind);
            return true;
        } else
            return false;
    }

    /**
     * Afegeix tots els elements d'una Població a la Població del p.i.
     * @param pob Població que afegit tots els seus Individus a la població del p.i.
     * @throws massesIndividus
     * @throws noExisteixIndividu
     */
    public void addAll(Poblacio pob) throws massesIndividus, noExisteixIndividu {

        if (individus.size() + pob.mida() <= maxIndividus) {
            for (int i = 0; i < pob.mida(); ++i) {
                individus.add(pob.getIndividu(i));
            }
        }
        else throw new massesIndividus();
    }
}
