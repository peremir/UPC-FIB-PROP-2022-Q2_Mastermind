import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import domain.Individu;
import domain.Poblacio;

import domain.Codi;
import domain.Codi.Dificultat;
import exceptions.massesIndividus;
import exceptions.noExisteixIndividu;

public class PoblacioTest {
    
    @Test
    public void poblacioTest() {

        int maxIndividus = 150;
        Codi codiPoblacio = new Codi(Dificultat.DIFICIL);

        Poblacio poblacio = new Poblacio(maxIndividus, codiPoblacio);
        String error = null;
        try {
            poblacio.inicialitzaPoblacio(0);
        } catch (noExisteixIndividu e) {
            error = e.getMessage();
        }
        assertEquals(null, maxIndividus, poblacio.mida());

        Poblacio poblacio2 = new Poblacio(maxIndividus, codiPoblacio);
        try {
            poblacio2.inicialitzaPoblacio(100);
        } catch (noExisteixIndividu e) {
            error = e.getMessage();
        }
        assertEquals(null, 50, poblacio2.mida());

        try {
            Individu test = poblacio.getIndividu(123);
            poblacio2.add(test);
        } catch (noExisteixIndividu e) {
            error = e.getMessage();
        }
        assertEquals(null, 51, poblacio2.mida());

        try {
            poblacio2.getIndividu(62);
        } catch (noExisteixIndividu e) {
            error = e.getMessage();
        }
        assertTrue(error.contains("No exiteix cap individu amb l'index"));

        try {
            poblacio2.addAll(poblacio);
        } catch (massesIndividus | noExisteixIndividu e) {
            error = e.getMessage();
        }
        assertTrue(error.equals("La poblacio supera el nombre maxim d'individus."));
        

    }
}
