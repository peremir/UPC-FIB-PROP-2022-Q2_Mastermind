package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Codi.Colors;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;

/**
 * Aquesta classe defineix una de les dues estrategies que coneix el
 * CodeBreaker.
 * Com indica el seu nom, aquesta estrategia segueix la estructura de
 * l'algorisme Genetic.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * 
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Genetic.java">
 *      Genetic.java </a>
 * 
 * @author Aina Gomez Pinyol
 * @version 31.5.2023
 */

public class Genetic implements Estrategia, Maquina {

    private static int maxGeneracions;
    private static int maxMillorsIndividus;
    private static int maxIndividusPoblacio;

    private static final double taxaRecombinacio = 0.5;
    private static final double taxaMutacio = 0.2;
    private static final double taxaPermutacio = 0.1;

    private Codi codiPartida;
    private MesuraFitness fitness;

    /**
     * Constructora.
     * 
     * @param codiPartida: codi que utilitza la partida actual.
     */
    public Genetic(Codi codiPartida) {

        this.codiPartida = codiPartida;
        fitness = new MesuraFitness(codiPartida.getDificultat());

        if (codiPartida.getPosicions() >= 6) {
            maxGeneracions = 600;
            maxMillorsIndividus = 80;
            maxIndividusPoblacio = 300;
        } else {
            maxGeneracions = 100;
            maxMillorsIndividus = 60;
            maxIndividusPoblacio = 150;
        }
    }

    /**
     * Metode que calcula la primera jugada de la partida.
     * 
     * @return combinacio aleatoria.
     */
    private List<Colors> jugarPrimerTorn() {

        Individu indRand = new Individu(codiPartida.getAvailColors(), codiPartida.getPosicions());
        return indRand.getCombinacio();
    }

    /**
     * Calcula les combinacions amb mes probabilitats de guanyar i en selecciona una
     * aleatoriament.
     * 
     * @param response: la resposta a la combinacio del torn anterior.
     * 
     * @param lastTorn: combinacio jugada al torn anterior.
     * 
     * @return combinacio de colors amb mes probabilitats d'encertar.
     */
    public List<Colors> jugarTornMaquina(List<Colors> response, List<Colors> lastTorn) throws Exception {

        List<Colors> guess = new ArrayList<>();

        if (lastTorn.isEmpty())
            guess = jugarPrimerTorn();
        else {

            fitness.setJugada(lastTorn);
            fitness.setResposta(response);

            Poblacio poblacioInicial = new Poblacio(maxIndividusPoblacio, codiPartida);
            Poblacio millorsCombinacions = new Poblacio(maxMillorsIndividus, codiPartida);

            poblacioInicial.inicialitzaPoblacio(0);
            int h = 0;

            while (h <= maxGeneracions && millorsCombinacions.mida() <= maxMillorsIndividus) {

                Poblacio pobleNou = new Poblacio(maxIndividusPoblacio, codiPartida);

                int p = 0;
                while (p < maxIndividusPoblacio) {

                    int rand1 = (int) (Math.random() * maxIndividusPoblacio);
                    int rand2 = (int) (Math.random() * maxIndividusPoblacio);
                    while (rand1 == rand2)
                        rand2 = (int) (Math.random() * maxIndividusPoblacio);

                    Individu parent1 = poblacioInicial.getIndividu(rand1);
                    Individu parent2 = poblacioInicial.getIndividu(rand2);

                    Individu child = parent1.recombinaIndividus(taxaRecombinacio, parent2.getCombinacio());
                    child.mutaIndividu(taxaMutacio, codiPartida.getAvailColors());
                    child.permutaIndividu(taxaPermutacio);

                    if (pobleNou.add(child)) {
                        ++p;

                        int fitnessChild = fitness.mesurarFitness(child);

                        if (fitnessChild == 0)
                            millorsCombinacions.add(child);
                    }
                }
                ++h;

                poblacioInicial = new Poblacio(maxIndividusPoblacio, codiPartida);
                poblacioInicial.addAll(millorsCombinacions);
                poblacioInicial.inicialitzaPoblacio(poblacioInicial.mida());
            }
            if (millorsCombinacions.mida() == 0) {
                int indexGuess = (int) (Math.random() * poblacioInicial.mida());
                guess = poblacioInicial.getIndividu(indexGuess).getCombinacio();
            } else {
                int indexGuess = (int) (Math.random() * millorsCombinacions.mida());
                guess = millorsCombinacions.getIndividu(indexGuess).getCombinacio();
            }
        }
        return guess;
    }

    /**
     * Metode que implementa l'algorisme genetic per trobar la combinacio que
     * passem com a parametre.
     * 
     * @param solution combinacio a encertar.
     * 
     * @return llista amb totes les combinacions jugades fins arribar a la
     *         combinacio trobada o al final de torns.
     * 
     * @throws Exception si el codi te una longitud diferent a 4, 5 o
     *                   6 posicions.
     *                   si el parametre 'solution' conte un color no
     *                   disponible en la dificultat del joc.
     */
    public List<List<Integer>> solve(List<Integer> solution) throws Exception {

        List<Colors> allColors = codiPartida.getAvailColors();
        List<List<Integer>> r = new ArrayList<List<Integer>>();

        // EXEPCIONS!!
        if (solution.size() > 6)
            throw new InvalidCodiLength(6, solution.size());
        else if (solution.size() < 4)
            throw new InvalidCodiLength(4, solution.size());

        List<Colors> response = new ArrayList<>();
        List<Colors> lastTorn = new ArrayList<>();

        List<Colors> sol = new ArrayList<>();
        for (int i = 0; i < solution.size(); ++i) {

            int index = solution.get(i) - 1;
            if (index < 0 || index >= allColors.size())
                throw new InvalidCodiColors(null);

            sol.add(allColors.get(index));
        }
        Codi codiSolve = new Codi(codiPartida.getDificultat());
        codiSolve.setCodi(sol);

        int maxTorns = 20;
        int torns = 0;
        boolean win = false;

        while (!win && torns <= maxTorns) {

            List<Colors> aux = jugarTornMaquina(response, lastTorn);
            lastTorn = new ArrayList<>(aux);
            response = codiSolve.checkCodi(lastTorn);

            if (response.size() == codiSolve.getPosicions() && !response.contains(Colors.NEGRE)) {
                win = true;
            }
            ++torns;

            List<Integer> lastTornInt = new ArrayList<>();
            for (int i = 0; i < lastTorn.size(); ++i) {
                int color = allColors.indexOf(lastTorn.get(i)) + 1;
                lastTornInt.add(color);
            }
            r.add(lastTornInt);
        }
        return r;
    }

}
