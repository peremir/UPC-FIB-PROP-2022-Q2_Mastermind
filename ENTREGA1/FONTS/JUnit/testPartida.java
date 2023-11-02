import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import domain.Codebreaker;
import domain.Codemaker;
import domain.Codi;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import domain.Partida;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

@RunWith(MockitoJUnitRunner.class)
public class testPartida {
    @Mock
    Codemaker cm;
    @Mock
    Codebreaker cb1;
    @Mock
    Codebreaker cb2;

    private Codi codi;
    private Dificultat dificultat = Dificultat.FACIL;
    private Partida p1;
    private Partida p2;

    @Before
    public void setUp() {
        when(cm.getCode()).thenReturn(List.of(Colors.VERD, Colors.VERD, Colors.VERD, Colors.VERD));
        try {
            when(cb1.jugarTorn(List.of())).thenReturn(List.of(Colors.VERD, Colors.VERD, Colors.VERD, Colors.VERD));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            when(cb2.jugarTorn(List.of())).thenReturn(List.of(Colors.VERD, Colors.VERD, Colors.BLAU, Colors.VERMELL));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            p1 = new Partida(1, 10, dificultat, cb1, cm);
        } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            p2 = new Partida(2, 10, dificultat, cb2, cm);
        } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.codi = new Codi(dificultat);
        try {
            this.codi.setCodi(cm.getCode());
        } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckCorreccio() {
        assertNotNull(p1);
        assertNotNull(p2);
        Boolean a = false;
        Boolean b = false;
        try {
            b = p1.checkCorreccio(4, 0, cb1.jugarTorn(List.of()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert (p1.getTornActual() == 1);
        assert (p1.partidaAcabada() == true);
        assert (p1.partidaGuanyada() == true);
        try {
            a = p2.checkCorreccio(2, 0, cb2.jugarTorn(List.of()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert (p2.getTornActual() == 1);
        assert (p2.partidaAcabada() == false);
        assert (p2.partidaGuanyada() == false);
        assertTrue(a);
        assertTrue(b);
    }
}