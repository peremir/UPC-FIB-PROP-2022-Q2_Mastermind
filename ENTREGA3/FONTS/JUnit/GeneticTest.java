import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import domain.Maquina;
import domain.Codi.Dificultat;
import domain.Codi;
//import domain.Codi.Dificultat;
import domain.Genetic;
//import domain.Maquina;

public class GeneticTest {

    List<Integer> colorsMitja = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    List<Integer> colorsDificil = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

    private List<List<Integer>> generaAllComb(int npos, List<Integer> colors) {

        List<List<Integer>> allComb = new ArrayList<>();

        for (int x = 0; x < colors.size(); ++x) {
            List<Integer> comb = new ArrayList<>();
            comb.add(x + 1);
            for (int y = 0; y < colors.size(); ++y) {
                comb.add(y + 1);
                for (int z = 0; z < colors.size(); ++z) {
                    comb.add(z + 1);
                    for (int k = 0; k < colors.size(); ++k) {
                        comb.add(k+1);
                        if (npos >= 5) comb.add(7);
                        if (npos >= 6) comb.add(8);

                        List<Integer> aux = new ArrayList<>(comb);
                        allComb.add(aux);

                        if (npos >= 6) comb.remove(5);
                        if (npos >= 5) comb.remove(4);
                        comb.remove(3);
                    }
                    comb.remove(2);
                }
                comb.remove(1);
            }
            comb.remove(0);
        }

        return allComb;
    }

    @Test
    @Ignore
    public void testAllCombinacionsFacil() {

        List<Integer> colors = Arrays.asList(1, 2, 3, 4, 5, 6);
        int npos = 4;

        List<List<Integer>> allComb = generaAllComb(npos, colors);
        assertEquals(null, 1296, allComb.size());

        int i = 0;
        int mitjaTorns = 0;
        while (i < allComb.size()) {

            Codi codi = new Codi(Dificultat.FACIL);
            Maquina GA = new Genetic(codi);

            // System.out.println(allComb.get(i));

            try {

                List<List<Integer>> result = GA.solve(allComb.get(i));
                int lastElement = result.size() - 1;

                // System.out.println("Comb #" + i + " torns " + result.size());
                mitjaTorns += result.size();
                assertEquals(null, allComb.get(i), result.get(lastElement));

            } catch (Exception e) {
                e.getMessage();
            }
            // assertTrue(error.equals(null));
            ++i;
        }
        mitjaTorns /= allComb.size();
        System.out.println("Mitja de torns amb dificultat FACIL: " + mitjaTorns);
    } 

    @Test
    @Ignore
    public void testAllCombinacionsMitja() {

        List<Integer> colors = Arrays.asList(1,2,3,4,5,6,7);
        int npos = 5;

        List<List<Integer>> allComb = generaAllComb(npos, colors);
        assertEquals(null, 2401, allComb.size());

        int i = 0;
        int mitjaTorns = 0;
        while (i < allComb.size()) {

            Codi codi = new Codi(Dificultat.MITJA);
            Maquina GA = new Genetic(codi);

            //System.out.println(allComb.get(i));

            try {

                List<List<Integer>> result = GA.solve(allComb.get(i));
                int lastElement = result.size() - 1;

                //System.out.println("Comb #" + i + " torns " + result.size());
                mitjaTorns += result.size();
                assertEquals(null, allComb.get(i), result.get(lastElement));

            } catch (Exception e) {
                e.getMessage();
            }
            //assertTrue(error.equals(null));
            ++i;
        }
        mitjaTorns /= allComb.size();
        System.out.println("Mitja de torns amb dificultat MITJA: " + mitjaTorns);
    } 

    @Test
    @Ignore
    public void testAllCombinacionsDificil() {

        List<Integer> colors = Arrays.asList(1,2,3,4,5,6,7,8);
        int npos = 6;

        List<List<Integer>> allComb = generaAllComb(npos, colors);
        assertEquals(null, 4096, allComb.size());

        int i = 0;
        int mitjaTorns = 0;
        while (i < 100) {

            Codi codi = new Codi(Dificultat.DIFICIL);
            Maquina GA = new Genetic(codi);

            //System.out.println(allComb.get(i));

            try {

                List<List<Integer>> result = GA.solve(allComb.get(i));
                int lastElement = result.size() - 1;

                //System.out.println("Comb #" + i + " torns " + result.size());
                mitjaTorns += result.size();
                assertEquals(null, allComb.get(i), result.get(lastElement));

            } catch (Exception e) {
                e.getMessage();
            }
            //assertTrue(error.equals(null));
            ++i;
        }
        mitjaTorns /= 100;
        System.out.println("Mitja de torns amb dificultat DIFICIL: " + mitjaTorns);
    } 
}
