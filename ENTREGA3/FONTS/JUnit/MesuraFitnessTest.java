import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.*;

import domain.Individu;
import domain.MesuraFitness;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

public class MesuraFitnessTest {
    
    @Test
    public void MFtest() {

        MesuraFitness fitness = new MesuraFitness(Dificultat.FACIL);

        List<Colors> jugada = Arrays.asList(Colors.GROC, Colors.BLAU, Colors.VERMELL, Colors.BLAU);
        List<Colors> resposta = Arrays.asList(Colors.NEGRE);

        try {
            fitness.setJugada(jugada);
        } catch (InvalidCodiLength e) {
            e.getMessage();
        }
        try {
            fitness.setResposta(resposta);
        } catch (InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        jugada = Arrays.asList(Colors.GROC, Colors.VERMELL, Colors.GROC, Colors.VERMELL);
        resposta = new ArrayList<>();

        try {
            fitness.setJugada(jugada);
        } catch (InvalidCodiLength e) {
            e.getMessage();
        }
        try {
            fitness.setResposta(resposta);
        } catch (InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        jugada = Arrays.asList(Colors.BLAU, Colors.VERD, Colors.MAGENTA, Colors.TARONJA);
        resposta = Arrays.asList(Colors.BLANC, Colors.NEGRE, Colors.NEGRE);

        try {
            fitness.setJugada(jugada);
        } catch (InvalidCodiLength e) {
            e.getMessage();
        }
        try {
            fitness.setResposta(resposta);
        } catch (InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        jugada = Arrays.asList(Colors.BLAU, Colors.GROC, Colors.GROC, Colors.GROC);
        resposta = Arrays.asList(Colors.BLANC);

        try {
            fitness.setJugada(jugada);
        } catch (InvalidCodiLength e) {
            e.getMessage();
        }
        try {
            fitness.setResposta(resposta);
        } catch (InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<Colors> comb = Arrays.asList(Colors.BLAU, Colors.TARONJA, Colors.VERD, Colors.VERD);
        Individu ind = new Individu(comb);
        try {
            int a = fitness.mesurarFitness(ind);
            assertEquals(null, 0, a);

        } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
            e.getMessage();
        }

        comb = Arrays.asList(Colors.VERD, Colors.TARONJA, Colors.GROC, Colors.VERD);
        Individu ind2 = new Individu(comb);
        try {
            int a = fitness.mesurarFitness(ind2);
            assertEquals(null, 2, a);
            
        } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
            e.getMessage();
        }

    }
}
