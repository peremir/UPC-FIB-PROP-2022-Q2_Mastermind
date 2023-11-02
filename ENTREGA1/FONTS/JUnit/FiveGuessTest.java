import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.Codi;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import domain.FiveGuess;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

public class FiveGuessTest {

    private Codi codiFacil = new Codi(Dificultat.FACIL);

    private FiveGuess fg;

    @Before
    @Test
    public void setUpFiveGuessFacil() {

        fg = new FiveGuess(codiFacil);

        int ncolors = fg.getNColors();
        Integer npos = fg.getNPos();

        assertEquals(codiFacil.getAvailColors().size(), ncolors);
        assertEquals(codiFacil.getPosicions(), npos);
    }

    @Test
    public void initialGuessFacil() {

        List<Colors> expected = new ArrayList<Colors>();
        List<Colors> resultat = new ArrayList<Colors>();

        try {
            resultat = fg.jugarTornMaquina(expected, expected);
        } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        expected = Arrays.asList(Colors.BLAU, Colors.BLAU, Colors.VERMELL, Colors.VERMELL);

        assertEquals(expected, resultat);
    }

    @Test
    public void testFg() {

        Codi codi = new Codi(Dificultat.FACIL);
        FiveGuess fgFacil = new FiveGuess(codi);

        List<Integer> solution = Arrays.asList(4,1,3,6);

        List<List<Integer>> solve = new ArrayList<List<Integer>>();        

        try {
            solve = fgFacil.solve(solution);
            System.out.println(solve);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert(solve.contains(solution));

        solution = Arrays.asList(4,2,3,5);
        
        try {
            solve = fgFacil.solve(solution);
            System.out.println(solve);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert(solve.contains(solution));

        solution = Arrays.asList(5,2,1,1);
        
        try {
            solve = fgFacil.solve(solution);
            System.out.println(solve);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert(solve.contains(solution));

        solution = Arrays.asList(3,6,2,1);
        
        try {
            solve = fgFacil.solve(solution);
            System.out.println(solve);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert(solve.contains(solution));

        solution = Arrays.asList(5,2,0,1);
        String error = null;
        try {
            solve = fgFacil.solve(solution);
            System.out.println(solve);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            error = e.getMessage();
        }
        assertNotNull(null, error);

        solution = Arrays.asList(5,2,2,2,1);
        error = null;
        try {
            solve = fgFacil.solve(solution);
            System.out.println(solve);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            error = e.getMessage();
        }
        assertNotNull(null, error);

    }

}
