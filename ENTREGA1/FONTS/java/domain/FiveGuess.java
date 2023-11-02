package domain;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.util.*;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;


public class FiveGuess implements Estrategia, Maquina {
    
    private List<ArbreNAri> PossibleCandidates = new ArrayList<ArbreNAri>();
    private List<ArbreNAri> AllCombinations = new ArrayList<ArbreNAri>();
    //private LinkedHashSet<NodeArbreNAri> CandidatesArrel = new LinkedHashSet<NodeArbreNAri>();
    
    private Integer NPOSITIONS;
    private Integer NCOLORS;
    private List<Colors> availColors = new ArrayList<Colors>();

    public FiveGuess(Codi codiPartida) {

        availColors = codiPartida.getAvailColors();

        NCOLORS = availColors.size();
        NPOSITIONS = codiPartida.getPosicions();

        // implementar arbre.
        for (int i = 0; i < NCOLORS; ++i) {

            ArbreNAri combColor = new ArbreNAri(availColors.get(i), NPOSITIONS);
            combColor.construirArbre(availColors);

            ArbreNAri combC = new ArbreNAri(availColors.get(i), NPOSITIONS);
            combC.construirArbre(availColors);

            PossibleCandidates.add(combColor);
            AllCombinations.add(combC);
            //CandidatesArrel.add(combColor.getNode());
        }
    
    }

    private List<Colors> getInitialGuess() {

        List<Colors> primeraJugada = new ArrayList<Colors>();
        int m = NPOSITIONS/2;

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
            //io.println("MINIMAX per arbre: " + AllCombinations.get(i).getArrel());
            nodeRec = AllCombinations.get(i).calculateMinimax(PossibleCandidates);

            if (nodeAbs.getInfo() == null || nodeAbs.getPoints() > nodeRec.getPoints()) {
                //io.println("nodeAbs > nodeRec : actualitzem minimax");
                nodeAbs = nodeRec;
            }
            else if (nodeAbs.getPoints() == nodeRec.getPoints() && !nodeAbs.getS() && nodeRec.getS()) {
                //io.println("nodeAbs == nodeRec.S : actualitzem minimax");
                nodeAbs = nodeRec;
            }
            //io.println("ARBRE " + AllCombinations.get(i).getArrel() + " minimax calculat: " + nodeAbs.getComb() + " " + nodeAbs.getPoints());
        }
        if (nodeAbs.getInfo() != null) resultat = nodeAbs.getComb();
        return resultat;
    }
    

    public List<Colors> jugarTornMaquina(List<Colors> cm, List<Colors> cb) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        List<Colors> resposta = new ArrayList<>();

        if (cb.isEmpty()) resposta = getInitialGuess();
        else {
            if (cm.isEmpty()) {
                //io.println("cas ELIMINAR COLOR SENCER");
                for (int i = 0; i < cb.size(); ++i) {

                    int a = 0;
                    while (a < PossibleCandidates.size()) {

                        //io.print("arrel que estem consultant: " + PossibleCandidates.get(a).getArrel() + " ");
                        if (PossibleCandidates.get(a).getArrel() == null) {
                            PossibleCandidates.remove(a);
                            //io.println("DELETED ARBRE : sense fills");
                        }
                        else if (PossibleCandidates.get(a).getArrel().equals(cb.get(i))) {
                            //io.println("DELETED ARBRE");
                            PossibleCandidates.remove(a);
                        }
                        else {
                            PossibleCandidates.get(a).eliminaColor(cb.get(i));
                            //io.println("eliminem color: " + PossibleCandidates.get(a).getArrel());
                            if (PossibleCandidates.get(a).getArrel() == null) {
                                PossibleCandidates.remove(a);
                                //io.println("DELETED SUBARBRE : sense fills");
                            }
                            else ++a;
                        }
                    }
                }
            }
            else {
                //io.println("cas ELIMINAR DIFERENTS");
                int i = 0;
                while (i < PossibleCandidates.size()) {
                    //io.println("arrel que estem consultant: " + PossibleCandidates.get(i).getArrel());
                    PossibleCandidates.get(i).eliminaComb(cm, cb);
                    //io.println("arrel despres eliminar: " + PossibleCandidates.get(i).getArrel());
                    if (PossibleCandidates.get(i).getArrel() == null) {
                        PossibleCandidates.remove(i);
                        //io.println("DELETED SUBARBRE : sense fills");
                    }
                    else ++i;
                }
            }
            //io.println("cridem a calcular Minimax");
            resposta = calcularMinimax();
        }
        return resposta;
    }

    private void reiniciar(Codi codiPartida) {

        availColors = codiPartida.getAvailColors();

        NCOLORS = availColors.size();
        NPOSITIONS = codiPartida.getPosicions();

        // implementar arbre.
        for (int i = 0; i < NCOLORS; ++i) {

            ArbreNAri combColor = new ArbreNAri(availColors.get(i), NPOSITIONS);
            combColor.construirArbre(availColors);

            ArbreNAri combC = new ArbreNAri(availColors.get(i), NPOSITIONS);
            combC.construirArbre(availColors);

            PossibleCandidates.add(combColor);
            AllCombinations.add(combC);
            //CandidatesArrel.add(combColor.getNode());
        }
    }

    public List<List<Integer>> solve(List<Integer> solution) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        Hashtable<Integer,Colors> colors = new Hashtable<>();
        colors.put(1, Colors.BLAU);
        colors.put(2, Colors.VERMELL);
        colors.put(3, Colors.VERD);
        colors.put(4, Colors.GROC);
        colors.put(5, Colors.TARONJA);
        colors.put(6, Colors.MAGENTA);

        Hashtable<Colors,Integer> ints = new Hashtable<>();
        ints.put(Colors.BLAU, 1);
        ints.put(Colors.VERMELL, 2);
        ints.put(Colors.VERD, 3);
        ints.put(Colors.GROC, 4);
        ints.put(Colors.TARONJA, 5);
        ints.put(Colors.MAGENTA, 6);

        List<List<Integer>> results = new ArrayList<>();
        List<Colors> scolors = new ArrayList<>();

        if (solution.size() != 4) throw new InvalidCodiLength(4, solution.size());

        for (int i = 0; i < solution.size(); ++i) {
            if (solution.get(i) <= 0 || solution.get(i) > 6) {
                throw new InvalidCodiColors(null);
            }
            else scolors.add(colors.get(solution.get(i)));
        }

        int NTORNS = 10;
        boolean win = false;
        int actTorn = 1;

        Codi c = new Codi(Dificultat.FACIL);
        c.setCodi(scolors);
        //io.println("CODI A ENDIVINAR " + scolors);

        List<Colors> cm = new ArrayList<>();
        List<Colors> cb = new ArrayList<>();

        while (!win && actTorn <= NTORNS) {

            cb = jugarTornMaquina(cm, cb);

            int index = ints.get(cb.get(0))-1;
            AllCombinations.get(index).removeCombination(cb);
            cm = c.checkCodi(cb);
            if (cm.size() == 4 && !cm.contains(Colors.NEGRE)) win = true;
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

    public int getNColors() {
        return PossibleCandidates.size();
    }

    public int getNPos() {
        return NPOSITIONS;
    }

    public List<Colors> ajudarUsuari(List<Colors> cm, List<Colors> cb) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        List<Colors> result = new ArrayList<>();
        result = jugarTornMaquina(cm, cb);
        return result;
    }
}
