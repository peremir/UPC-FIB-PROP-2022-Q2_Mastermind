import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import domain.GestorPartides;
import domain.Partida;
import exceptions.LimitPartidesGuardades;
import exceptions.PartidaNoTrobada;

@RunWith(MockitoJUnitRunner.class)
public class GestorTest {
    @Mock
    Partida partidaMock;

    @Test
    public void testGuardarPartida() {
        assertNotNull(partidaMock);
        GestorPartides GP = new GestorPartides();
        String error = null;
        try {
            GP.guardarPartida(partidaMock);
        } catch (LimitPartidesGuardades e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        assertTrue(GP.getPartides().contains(partidaMock));
    }

    @Test
    public void testEliminarPartida() {
        assertNotNull(partidaMock);
        GestorPartides GP = new GestorPartides();
        String error = null;
        try {
            GP.guardarPartida(partidaMock);
        } catch (LimitPartidesGuardades e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        assertTrue(GP.getPartides().contains(partidaMock));
        error = null;
        try {
            GP.eliminarPartida(partidaMock);
        } catch (PartidaNoTrobada e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        assertFalse(GP.getPartides().contains(partidaMock));
    }

    @Test
    public void testCarregarPartida() {
        assertNotNull(partidaMock);
        GestorPartides GP = new GestorPartides();
        String error = null;
        try {
            GP.guardarPartida(partidaMock);
        } catch (LimitPartidesGuardades e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        assertTrue(GP.getPartides().contains(partidaMock));
        Partida partida = null;
        error = null;
        try {
            partida = GP.carregarPartida(partidaMock);
        } catch (PartidaNoTrobada e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        assertEquals(partidaMock, partida);
    }
}