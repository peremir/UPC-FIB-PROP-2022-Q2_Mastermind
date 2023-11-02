import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import domain.Partida;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

public class testPartida {

    private Dificultat dificultat = Dificultat.FACIL;
    private Boolean codemaker1 = true;
    private Boolean codebreaker1 = false;
    private Partida p1;
    private List<Colors> colors = List.of(Colors.VERD, Colors.VERD, Colors.VERD, Colors.VERD);

    @Before
    public void setUp() {
        try {
            p1 = new Partida(10, dificultat, codebreaker1, codemaker1);
        } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p1.setEstrategia(true);
        p1.setRondes(2);
    }

    @Test
    public void testPartides() {
        assertNotNull(p1);
        try {
            p1.setupRonda(colors);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert (p1.getRondaActual() == 0);
        assert (p1.partidaAcabada() == false);
        assert (p1.partidaGuanyada() == false);
        assert (p1.rol() == true);

        p1.nextTurn();
        assert (p1.rol() == false);

        try {
            p1.setupRonda(colors);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert (p1.getRondaActual() == 1);
    }
}