package domain;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

/**
 * Defineix una de les dues estrategies conegudes pel CodeBreaker.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * @author Aina Gomez Pinyol
 * @version 2.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/FiveGuess.java>FiveGuess.java</a>
 */

public class FiveGuess implements Estrategia, Maquina {

    private List<ArbreNAri> PossibleCandidates = new ArrayList<ArbreNAri>();
    private List<ArbreNAri> AllCombinations = new ArrayList<ArbreNAri>();

    private Integer NPOSITIONS;
    private Integer NCOLORS;
    private List<Colors> availColors = new ArrayList<Colors>();

    /**
     * Constructora.
     * @param codiPartida El codi de la partida que conté el nombre de posicions, els
     *                    colors disponibles i la dificultat.
     */
    public FiveGuess(Codi codiPartida) {

        availColors = codiPartida.getAvailColors();

        NCOLORS = availColors.size();
        NPOSITIONS = codiPartida.getPosicions();

        for (int i = 0; i < NCOLORS; ++i) {

            ArbreNAri combColor = new ArbreNAri(availColors.get(i), NPOSITIONS);
            combColor.construirArbre(availColors);

            ArbreNAri combC = new ArbreNAri(availColors.get(i), NPOSITIONS);
            combC.construirArbre(availColors);

            PossibleCandidates.add(combColor);
            AllCombinations.add(combC);
        }

    }

    private List<Colors> getInitialGuess() {

        List<Colors> primeraJugada = new ArrayList<Colors>();
        int m = NPOSITIONS / 2;

        for (int i = 0; i < m; ++i) {
            primeraJugada.add(availColors.get(0));
        }

        for (int j = m; j < NPOSITIONS; ++j) {
            primeraJugada.add(availColors.get(1));
        }
        return primeraJugada;
    }

    private List<Colors> calcularMinimax() throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        List<Colors> resultat = new ArrayList<>();
        NodeArbreNAri nodeAbs = new NodeArbreNAri();

        for (int i = 0; i < AllCombinations.size(); ++i) {

            NodeArbreNAri nodeRec = new NodeArbreNAri();
            nodeRec = AllCombinations.get(i).calculateMinimax(PossibleCandidates);

            if (nodeAbs.getInfo() == null || nodeAbs.getPoints() > nodeRec.getPoints()) {
                nodeAbs = nodeRec;
            } else if (nodeAbs.getPoints() == nodeRec.getPoints() && !nodeAbs.getS() && nodeRec.getS()) {
                nodeAbs = nodeRec;
            }
        }
        if (nodeAbs.getInfo() != null)
            resultat = nodeAbs.getComb();
        return resultat;
    }

    /**
     * Calcula la següent combinació a jugar.
     * @param cm La resposta obtinguda pel CodeMaker de la combinació jugada el torn
     *           anterior.
     * @param cb Combinació jugada el torn anterior.
     * @return Combinació a jugar.
     */
    public List<Colors> jugarTornMaquina(List<Colors> cm, List<Colors> cb)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        List<Colors> resposta = new ArrayList<>();

        if (cb.isEmpty())
            resposta = getInitialGuess();
        else {
            if (cm.isEmpty()) {

                for (int i = 0; i < cb.size(); ++i) {

                    int a = 0;
                    while (a < PossibleCandidates.size()) {

                        if (PossibleCandidates.get(a).getArrel() == null) {
                            PossibleCandidates.remove(a);
                        } else if (PossibleCandidates.get(a).getArrel().equals(cb.get(i))) {
                            PossibleCandidates.remove(a);
                        } else {
                            PossibleCandidates.get(a).eliminaColor(cb.get(i));

                            if (PossibleCandidates.get(a).getArrel() == null) {
                                PossibleCandidates.remove(a);
                            } else
                                ++a;
                        }
                    }
                }
            } else {

                int i = 0;
                while (i < PossibleCandidates.size()) {

                    PossibleCandidates.get(i).eliminaComb(cm, cb);

                    if (PossibleCandidates.get(i).getArrel() == null) {
                        PossibleCandidates.remove(i);
                    } else
                        ++i;
                }
            }
            resposta = calcularMinimax();
        }
        return resposta;
    }

    private void reiniciar(Codi codiPartida) {

        availColors = codiPartida.getAvailColors();

        NCOLORS = availColors.size();
        NPOSITIONS = codiPartida.getPosicions();

        for (int i = 0; i < NCOLORS; ++i) {

            ArbreNAri combColor = new ArbreNAri(availColors.get(i), NPOSITIONS);
            combColor.construirArbre(availColors);

            ArbreNAri combC = new ArbreNAri(availColors.get(i), NPOSITIONS);
            combC.construirArbre(availColors);

            PossibleCandidates.add(combColor);
            AllCombinations.add(combC);
        }
    }

    /**
     * Mètode que implementa l'algorisme Five Guess per trobar la combinació que 
     * passem com a parametre.
     * @param solution Combinació a encertar.
     * @return Llistat amb totes les combinacions jugades fins arribar a la combinació
     * a cercar o al final dels torns.
     */
    public List<List<Integer>> solve(List<Integer> solution) throws Exception {

        Hashtable<Integer, Colors> colors = new Hashtable<>();
        colors.put(1, Colors.BLAU);
        colors.put(2, Colors.VERMELL);
        colors.put(3, Colors.VERD);
        colors.put(4, Colors.GROC);
        colors.put(5, Colors.TARONJA);
        colors.put(6, Colors.MAGENTA);

        Hashtable<Colors, Integer> ints = new Hashtable<>();
        ints.put(Colors.BLAU, 1);
        ints.put(Colors.VERMELL, 2);
        ints.put(Colors.VERD, 3);
        ints.put(Colors.GROC, 4);
        ints.put(Colors.TARONJA, 5);
        ints.put(Colors.MAGENTA, 6);

        List<List<Integer>> results = new ArrayList<>();
        List<Colors> scolors = new ArrayList<>();

        if (solution.size() != 4)
            throw new InvalidCodiLength(4, solution.size());

        for (int i = 0; i < solution.size(); ++i) {
            if (solution.get(i) <= 0 || solution.get(i) > 6) {
                throw new InvalidCodiColors(null);
            } else
                scolors.add(colors.get(solution.get(i)));
        }

        int NTORNS = 10;
        boolean win = false;
        int actTorn = 1;

        Codi c = new Codi(Dificultat.FACIL);
        c.setCodi(scolors);

        List<Colors> cm = new ArrayList<>();
        List<Colors> cb = new ArrayList<>();

        while (!win && actTorn <= NTORNS) {

            cb = jugarTornMaquina(cm, cb);

            int index = ints.get(cb.get(0)) - 1;
            AllCombinations.get(index).removeCombination(cb);
            cm = c.checkCodi(cb);
            if (cm.size() == 4 && !cm.contains(Colors.NEGRE))
                win = true;
            ++actTorn;

            List<Integer> combination = new ArrayList<>();
            for (int i = 0; i < cb.size(); ++i) {
                combination.add(ints.get(cb.get(i)));
            }
            results.add(combination);
        }
        reiniciar(new Codi(Dificultat.FACIL));

        System.out.println(results);
        return results;
    }

}
