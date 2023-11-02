import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import domain.Individu;
import domain.Codi;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiLength;
import exceptions.taxaIncorrecta;

public class IndividuTest {
    
    @Test
    public void testCreadoraIndividu() {

        Codi codiFacil = new Codi(Dificultat.FACIL);
        Codi codiMitja = new Codi(Dificultat.MITJA);
        Codi codiDificil = new Codi(Dificultat.DIFICIL);

        for (int i = 0; i < 10; ++i) {

            Individu IndFacil = new Individu(codiFacil.getAvailColors(), codiFacil.getPosicions());
            assertEquals(null, 4, IndFacil.getCombinacio().size());
            assertFalse(null, IndFacil.getCombinacio().contains(Colors.CIAN));
            assertFalse(null, IndFacil.getCombinacio().contains(Colors.LIMA));

            Individu IndMitja = new Individu(codiMitja.getAvailColors(), codiMitja.getPosicions());
            assertEquals(null, 5, IndMitja.getCombinacio().size());
            assertFalse(null, IndMitja.getCombinacio().contains(Colors.LIMA));

            Individu IndDificil = new Individu(codiDificil.getAvailColors(), codiDificil.getPosicions());
            assertEquals(null, 6, IndDificil.getCombinacio().size());
        }

        Individu IndividuAllColors = new Individu(codiDificil.getAvailColors());
        assertEquals(null, codiDificil.getAvailColors(), IndividuAllColors.getCombinacio());
    }

    @Test
    public void testRecombinaMutaPermuta() {

        Codi codiFacil = new Codi(Dificultat.FACIL);
        Codi codiMitja = new Codi(Dificultat.MITJA);
        Codi codiDificil = new Codi(Dificultat.DIFICIL);

        Individu IndFacil1 = new Individu(codiFacil.getAvailColors(), codiFacil.getPosicions());
        Individu IndMitja1 = new Individu(codiMitja.getAvailColors(), codiMitja.getPosicions());
        Individu IndDificil1 = new Individu(codiDificil.getAvailColors(), codiDificil.getPosicions());

        Individu IndFacil2 = new Individu(codiFacil.getAvailColors(), codiFacil.getPosicions());
        Individu IndMitja2 = new Individu(codiMitja.getAvailColors(), codiMitja.getPosicions());
        Individu IndDificil2 = new Individu(codiDificil.getAvailColors(), codiDificil.getPosicions());

        String error = null;

        Individu RecFacil;
        try {
            RecFacil = IndFacil1.recombinaIndividus(0, IndFacil2.getCombinacio());
            assertEquals(null, IndFacil2.getCombinacio(), RecFacil.getCombinacio());

            Individu PermutaFacil = RecFacil.permutaIndividu(0.1);
            assertTrue(PermutaFacil.getCombinacio().contains(RecFacil.getCombinacio().get(0)));
            assertTrue(PermutaFacil.getCombinacio().contains(RecFacil.getCombinacio().get(1)));
            assertTrue(PermutaFacil.getCombinacio().contains(RecFacil.getCombinacio().get(2)));
            assertTrue(PermutaFacil.getCombinacio().contains(RecFacil.getCombinacio().get(3)));
            
        } catch (InvalidCodiLength | taxaIncorrecta e) {
            error = e.getMessage();
        }
        
        Individu RecMitja;
        try {
            RecMitja = IndMitja1.recombinaIndividus(1, IndMitja2.getCombinacio());
            assertEquals(null, IndMitja1.getCombinacio(), RecMitja.getCombinacio());

            Individu MutaMitja = RecMitja.mutaIndividu(1, codiMitja.getAvailColors());
            assertNotEquals(null, RecMitja.getCombinacio(), MutaMitja.getCombinacio());
        } catch (InvalidCodiLength | taxaIncorrecta e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }

        Individu RecDificil;
        try {
            RecDificil = IndDificil1.recombinaIndividus(0.5, IndDificil2.getCombinacio());
            assertEquals(null, 6, RecDificil.getCombinacio().size());

            Individu MutaDificil = RecDificil.mutaIndividu(0, codiDificil.getAvailColors());
            assertEquals(null, RecDificil.getCombinacio(), MutaDificil.getCombinacio());
        } catch (InvalidCodiLength | taxaIncorrecta e) {
            error = e.getMessage();
        }
        
        try {
            IndFacil1.recombinaIndividus(0.5, IndDificil2.getCombinacio());
        } catch (InvalidCodiLength | taxaIncorrecta e) {
            error = e.getMessage();
        }
        assertTrue(error.contains("La longitud del codi ha de ser "));

        try {
            IndFacil1.recombinaIndividus(3, IndFacil2.getCombinacio());
        } catch (InvalidCodiLength | taxaIncorrecta e) {
            error = e.getMessage();
        }
        assertTrue(error.contains("La taxa"));
    }
    

}
