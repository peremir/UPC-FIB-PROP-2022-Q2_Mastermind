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
import domain.Ronda;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

@RunWith(MockitoJUnitRunner.class)
public class testRonda {

    @Mock
    Codemaker cm;
    @Mock
    Codebreaker cb1;
    @Mock
    Codebreaker cb2;
    private Codi codi;
    private Dificultat dificultat = Dificultat.FACIL;
    private Ronda r1;
    private Ronda r2;

   @Before
   public void setUp() {
   when(cm.getCode()).thenReturn(List.of(Colors.VERD, Colors.VERD, Colors.VERD,
   Colors.VERD));
   try {
   when(cb1.jugarTorn(List.of())).thenReturn(List.of(Colors.VERD, Colors.VERD,
   Colors.VERD, Colors.VERD));
   } catch (Exception e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
   }
   try {
   when(cb2.jugarTorn(List.of())).thenReturn(List.of(Colors.VERD, Colors.VERD,
   Colors.BLAU, Colors.VERMELL));
   } catch (Exception e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
   }
   try {
   r1 = new Ronda(dificultat, cb1, cm);
   } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
   }
   try {
   r2 = new Ronda(dificultat, cb2, cm);
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
        assertNotNull(r1);
        assertNotNull(r2);
        Boolean a = false;
        Boolean b = false;
        try {
            b = r1.checkCorreccio(0, 4, cb1.jugarTorn(List.of()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert (r1.getTornActual() == 1);
        assert (r1.rondaAcabada() == true);
        assert (r1.rondaGuanyada() == true);
        try {
            a = r2.checkCorreccio(0, 2, cb2.jugarTorn(List.of()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assert (r2.getTornActual() == 1);
        assert (r2.rondaAcabada() == false);
        assert (r2.rondaGuanyada() == false);
        assertTrue(a);
        assertTrue(b);
    }
}