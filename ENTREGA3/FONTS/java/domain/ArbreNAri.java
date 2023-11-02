package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

/**
 * Respresenta l'estructura de dades que utilitza l'estrategia FiveGuess.
 * <p>
 * Aquesta classe consisteix en un Arbre N Ari, on cada arbre té com a mida el 
 * nombre de posicions del codi de la Partida i cada node representa un color.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * @author Aina Gomez Pinyol
 * @version 31.5.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/ArbreNAri.java> ArbreNAri.java </a>
 */

public class ArbreNAri {

    private NodeArbreNAri arrel;
    private NodeArbreNAri minimax;

    private int nivells;

    private Hashtable<List<Colors>, Integer> indexPoints = new Hashtable<>();
    private ArrayList<Integer> points = new ArrayList<Integer>();

    /**
     * Constructora buida.
     */
    public ArbreNAri() {
        arrel = new NodeArbreNAri();
        minimax = new NodeArbreNAri();
    }

    /**
     * Crea un arbre sense fills on l'arrel és el color 'c'.
     * @param c El color que volem que sigui l'arrel.
     * @param h L'alçada màxima de l'arbre, és a dir, el nombre de posicions que té 
     *          cada combinació en la partida actual.
     */
    public ArbreNAri(Colors c, int h) {

        arrel = new NodeArbreNAri(c);
        minimax = new NodeArbreNAri();

        nivells = h;
        setHashtable();
    }

    /**
     * Inicialitza la Hash Table per al calcul del minmax. 
     * <p>
     * Com FiveGuess només es disponible en la dificultat FACIL, tindrem 14 possibles
     * ajudes (combinacions de BLANC i NEGRE). Per tant creem una Hash Table que 
     * tradueixi l'ajuda rebuda a l'index del vector 'points' que hem d'actualitzar.
     */
    private void setHashtable() {

        indexPoints.put(Arrays.asList(), 0);
        indexPoints.put(Arrays.asList(Colors.NEGRE), 1);
        indexPoints.put(Arrays.asList(Colors.NEGRE, Colors.NEGRE), 2);
        indexPoints.put(Arrays.asList(Colors.NEGRE, Colors.NEGRE, Colors.NEGRE), 3);
        indexPoints.put(Arrays.asList(Colors.NEGRE, Colors.NEGRE, Colors.NEGRE, Colors.NEGRE), 4);
        indexPoints.put(Arrays.asList(Colors.BLANC), 5);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.NEGRE), 6);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.NEGRE, Colors.NEGRE), 7);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.NEGRE, Colors.NEGRE, Colors.NEGRE), 8);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC), 9);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC, Colors.NEGRE), 10);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC, Colors.NEGRE, Colors.NEGRE), 11);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC, Colors.BLANC), 12);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC, Colors.BLANC, Colors.BLANC), 13);
    }

    /**
     * Consulta el color de l'arrel del p.i.
     * @return El color del primer node de l'arbre.
     */
    public Colors getArrel() {
        return arrel.getInfo();
    }

    /**
     * Consulta l'arrel de l'arbre del p.i.
     * @return El primer node de l'arbre.
     */
    public NodeArbreNAri getNode() {
        return arrel;
    }

    /**
     * Metode recursiu per a la construcció de totes les combinacions. 
     * @param n Node que estem modificant.
     * @param col Llista amb tots els colors disponibles a la partida.
     * @param comb Combinació actual. Conté els colors de tots els nodes pares de 'n', 
     *             aquest últim inclòs.
     * @param h Alçada actual que ens trobem en l'arbre del p.i.
     */
    private void construirArbreRec(NodeArbreNAri n, List<Colors> col, List<Colors> comb, int h) {

        for (int i = 0; h < nivells - 1 && i < col.size(); ++i) {

            comb.add(col.get(i));

            NodeArbreNAri next = new NodeArbreNAri(col.get(i));
            if (comb.size() == nivells) {
                next.setComb(comb);
                next.setLeaf();
                next.setS(true);
            }
            n.setChild(next);

            construirArbreRec(next, col, comb, h + 1);

            int indexOfLastElement = comb.size() - 1;
            comb.remove(indexOfLastElement);
        }
    }

    /**
     * Constueix un arbre amb totes les combinacions possibles.
     * <p>
     * Aquest mètode crida a un altre mètode privat que construeix l'arbre de forma
     * recursiva.
     * @param colors Llista amb tots els colors disponibles a la partida actual.
     */
    public void construirArbre(List<Colors> colors) {

        List<Colors> comb = new ArrayList<Colors>();
        comb.add(arrel.getInfo());

        construirArbreRec(arrel, colors, comb, 0);
    }

    /**
     * Mètode recursiu per eliminar tot un color de l'arbre.
     * @param n Node que estem consultant.
     * @param c Color que volem eliminar.
     */
    private void eliminaColorRec(NodeArbreNAri n, Colors c) {

        if (n != null) {

            int i = 0;
            while (i < n.getNumChilds()) {

                if (n.getChild(i).getInfo().equals(c))
                    n.removeChild(i);
                else {
                    eliminaColorRec(n.getChild(i), c);
                    ++i;
                }
            }
            for (int x = 0; x < n.getNumChilds(); ++x) {
                if (!n.getChild(x).esFulla() && n.getChild(x).getNumChilds() == 0) {
                    n.removeChild(x);
                }
            }

        }
    }

    /**
     * Elimina un color de tot l'arbre del p.i.
     * <p>
     * Aquest mètode crida a un mètode privat recursiu. Quan aquest es troba amb un 
     * node que presenta un fill amb el color 'c', poda tot el fill i, per tant,
     * tots els seus subfills.
     * <p>
     * Si al podar un subarbre, el node que estem consultant es queda sense fills,
     * també l'eliminarem, ja que no ens interessen aquelles combinacions més curtes 
     * que l'alçada màxima de l'arbre (nombre de posicions del codi de la partida).
     * @param c Color que volem eliminar.
     */
    public void eliminaColor(Colors c) {

        if (arrel.getInfo() != null) {
            if (arrel.getInfo().equals(c))
                arrel = new NodeArbreNAri();
            else
                eliminaColorRec(arrel, c);

            if (arrel.getNumChilds() == 0)
                arrel = new NodeArbreNAri();
        }

    }

    private boolean compleixCond(List<Colors> code, List<Colors> solution, List<Colors> seq)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        List<Colors> resposta = new ArrayList<>();
        Codi solucio = new Codi(Dificultat.FACIL);
        solucio.setCodi(solution);

        int NBres = 0;
        int NBcode = 0;
        int NNres = 0;
        int NNcode = 0;

        resposta = solucio.checkCodiSeq(seq);
        for (int i = 0; i < resposta.size(); ++i) {
            if (resposta.get(i).equals(Colors.BLANC))
                NBres += 1;
            else
                NNres += 1;
        }
        for (int i = 0; i < code.size(); ++i) {
            if (code.get(i).equals(Colors.BLANC))
                NBcode += 1;
            else
                NNcode += 1;
        }

        boolean r = true;
        if (NBres > NBcode)
            r = false;
        else if (seq.size() == nivells && (NBcode != NBres || NNcode != NNres))
            r = false;
        return r;
    }

    private void eliminaCombRec(List<Colors> code, List<Colors> solution, List<Colors> seq, NodeArbreNAri n)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        if (n != null) {

            int i = 0;
            while (i < n.getNumChilds()) {

                seq.add(n.getChild(i).getInfo());

                if (compleixCond(code, solution, seq)) {
                    eliminaCombRec(code, solution, seq, n.getChild(i));
                    ++i;
                } else {
                    n.removeChild(i);
                }

                int indexOfLastElement = seq.size() - 1;
                seq.remove(indexOfLastElement);
            }
            int x = 0;
            while (x < n.getNumChilds()) {
                if (!n.getChild(x).esFulla() && n.getChild(x).getNumChilds() == 0)
                    n.removeChild(x);
                else
                    ++x;
            }
        }
    }

    /**
     * Elimina totes aquelles combinacions de l'arbre, que al comparar-les amb
     * una altra combinació obtenen una determinada resposta.
     * <p>
     * Hem de tenir present, que si un node que no es troba a l'alçada màxima i es
     * queda sense fills, l'haurem d'eliminar. Ja que no volem combinacions de 
     * llargaria menor al nombre de posicions del codi utilitzat a la partida.
     * 
     * @param code Combinació de BLANC i NEGRE amb la resposta obtinguda del torn
     *             anterior.
     * @param solution Combinació utilitzada al torn anterior, que al comparar-la amb
     *                 el codi a encertar, hem obtingut la resposta 'code'.
     * @throws codiAlreadyGenerated
     * @throws InvalidCodiLength
     * @throws InvalidCodiColors
     */
    public void eliminaComb(List<Colors> code, List<Colors> solution)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        List<Colors> seq = new ArrayList<Colors>();
        seq.add(arrel.getInfo());

        if (compleixCond(code, solution, seq))
            eliminaCombRec(code, solution, seq, arrel);
        else
            arrel = new NodeArbreNAri();

        if (arrel.getNumChilds() == 0)
            arrel = new NodeArbreNAri();
    }

    /**
     * Consulta l'alçada de l'arbre p.i
     * @return Un enter amb l'alçada de l'arbre.
     */
    public int getNivell() {
        return nivells;
    }
/*
    private void getLastOneRec(NodeArbreNAri n) {

        if (n.esFulla())
            minimax = n;
        else
            getLastOneRec(n.getChild(0));
    }

    public List<Colors> getLastOne() {

        getLastOneRec(arrel);
        return minimax.getComb();
    }
*/
    /**
     * Metode recursiu per eliminar una combinació de l'arbre del p.i.
     * @param n Node que estem consultant.
     * @param c Combinació de colors que volem eliminar.
     * @param i Index de la combinació 'c' que ens trobem. És a dir, si trobem un 
     *          node del color de c[i], incrementarem 'i' i buscarem pels seus fills.
     */
    private void removeCombinationRec(NodeArbreNAri n, List<Colors> c, int i) {

        if (n != null) {
            for (int x = 0; x < n.getNumChilds(); ++x) {
                if (n.getChild(x).esFulla() && n.getChild(x).getInfo().equals(c.get(i))) {
                    n.removeChild(x);
                    return;
                } else if (n.getChild(x).getInfo().equals(c.get(i))) {
                    removeCombinationRec(n.getChild(x), c, i + 1);
                }
            }
            int x = 0;
            while (x < n.getNumChilds()) {
                if (!n.getChild(x).esFulla() && n.getChild(x).getNumChilds() == 0)
                    n.removeChild(x);
                else
                    ++x;
            }
        }
    }

    /**
     *  Elimina una combinació de colors de l'arbre del p.i.
     * <p>
     * Aquest mètode realitza una cerca recursiva de la combinació i elimina
     * l'ultim fill d'aquesta. Si pel camí un d'aquests nodes es queda sense fills,
     * també l'eliminarem, perquè significa que ja hem eliminat les altres possibles
     * combinacions que comencen pels nodes visitats.
     * @param c Combinació de colors que volem eliminar.
     */
    public void removeCombination(List<Colors> c) {

        removeCombinationRec(arrel, c, 1);
    }

    private void recorreSegonArbre(NodeArbreNAri r, List<Colors> comb)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        if (r != null) {

            for (int i = 0; i < r.getNumChilds(); ++i) {
                recorreSegonArbre(r.getChild(i), comb);
            }
            if (r.esFulla()) {
                Codi code = new Codi(Dificultat.FACIL);
                code.setCodi(comb);

                List<Colors> res = new ArrayList<>();
                res = code.checkCodi(r.getComb());

                int index = indexPoints.get(res);
                points.set(index, points.get(index) + 1);
            }
        }
    }

    private void recorrePrimerArbre(List<ArbreNAri> r, NodeArbreNAri n)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        if (n != null) {

            for (int i = 0; i < n.getNumChilds(); ++i) {
                recorrePrimerArbre(r, n.getChild(i));
            }
            if (n.esFulla()) {

                int max = -1;
                boolean isOnSet = false;

                if (points.size() != 0)
                    points.clear();
                for (int p = 0; p < 14; ++p)
                    points.add(0);

                for (int i = 0; i < r.size(); ++i) {
                    recorreSegonArbre(r.get(i).getNode(), n.getComb());
                }
                int maxcomb = points.get(0);
                for (int x = 1; x < points.size(); ++x) {
                    if (maxcomb <= points.get(x))
                        maxcomb = points.get(x);
                    if (x == points.size() - 1 && points.get(x) == 1)
                        isOnSet = true;
                }

                if (max == -1 || maxcomb > max)
                    max = maxcomb;
                n.setPuntuacio(max);
                n.setS(isOnSet);

                if (minimax.getInfo() == null || minimax.getPoints() > n.getPoints())
                    minimax = n;
                else if (minimax.getPoints() == n.getPoints() && n.getS() && !minimax.getS())
                    minimax = n;
            }
        }
    }

    /**
     * Calcula la combinació amb més probabilitats d'encertar utilitzant la 
     * tècnica minmax.
     * <p>
     * Per fer això, necessitem recòrre dos llistats arbres (recordem que guardavem
     * un arbre per cada color): (1) llistat d'arbres amb totes les combinacions 
     * existents i (2) llistat d'arbres amb les combinacions que poden ser el codi a
     * encertar.
     * <p>
     * La tècnica minmax consisteix en comparar totes les combinacions existents amb
     * les que tenen possibilitat a encertar. D'aquestes, quedar-nos amb la combinació,
     * que, en el cas pitjor, elimina més combinacions del segon llistat (llistat amb 
     * possibilitat a encertar).
     * @param r Llistat amb totes les combinacions possibles a encertar.
     * @return Un NodeArbreNAri amb la informació de la combinació a probar el següent
     *         torn.
     * @throws codiAlreadyGenerated
     * @throws InvalidCodiLength
     * @throws InvalidCodiColors
     */
    public NodeArbreNAri calculateMinimax(List<ArbreNAri> r)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        minimax = new NodeArbreNAri();

        recorrePrimerArbre(r, arrel);
        return minimax;
    }
}
