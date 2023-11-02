import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.Codi;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;

public class CodiTest {
    private Codi codiFacil;
    private Codi codiMitja;
    private Codi codiDificil;

    @Before
    public void setUpFacil() throws Exception {
        codiFacil = new Codi(Dificultat.FACIL);
    }

    @Before
    public void setUpMitjal() throws Exception {
        codiMitja = new Codi(Dificultat.MITJA);
    }

    @Before
    public void setUpDificil() throws Exception {
        codiDificil = new Codi(Dificultat.DIFICIL);
    }

    @Test
    public void testCodiFacil() {
        String error = null;
        try {
            codiFacil.setCodi(List.of(Colors.BLAU, Colors.GROC, Colors.BLAU, Colors.VERMELL));
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        assertEquals(codiFacil.getCodi(), List.of(Colors.BLAU, Colors.GROC, Colors.BLAU, Colors.VERMELL));
    }

    @Test
    public void testCodiMitja() {
        String error = null;
        try {
            codiMitja.setCodi(
                    List.of(Colors.MAGENTA, Colors.GROC, Colors.VERD, Colors.VERD, Colors.TARONJA));
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        assertEquals(codiMitja.getCodi(),
                List.of(Colors.MAGENTA, Colors.GROC, Colors.VERD, Colors.VERD, Colors.TARONJA));
    }

    @Test
    public void testCodiDificil() {
        String error = null;
        try {
            codiDificil.setCodi(List.of(Colors.MAGENTA, Colors.GROC, Colors.VERMELL, Colors.VERMELL, Colors.TARONJA,
                    Colors.CIAN));
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        assertEquals(codiDificil.getCodi(), List.of(Colors.MAGENTA, Colors.GROC, Colors.VERMELL, Colors.VERMELL,
                Colors.TARONJA, Colors.CIAN));
    }

    @Test
    public void testCheckCodiFacil() {
        String error = null;
        try {
            codiFacil.setCodi(List.of(Colors.BLAU, Colors.GROC, Colors.BLAU, Colors.VERMELL));
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC, Colors.BLANC, Colors.BLANC, Colors.BLANC),
                    codiFacil.checkCodi(List.of(Colors.BLAU, Colors.GROC, Colors.BLAU, Colors.VERMELL)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC, Colors.BLANC, Colors.NEGRE, Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.BLAU, Colors.GROC, Colors.VERMELL, Colors.BLAU)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.NEGRE, Colors.NEGRE, Colors.NEGRE, Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.VERMELL, Colors.BLAU, Colors.GROC, Colors.BLAU)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(),
                    codiFacil.checkCodi(List.of(Colors.TARONJA, Colors.TARONJA, Colors.TARONJA, Colors.TARONJA)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC, Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.BLAU, Colors.TARONJA, Colors.VERMELL, Colors.TARONJA)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.TARONJA, Colors.TARONJA, Colors.TARONJA, Colors.BLAU)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.NEGRE, Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.VERD, Colors.BLAU, Colors.GROC, Colors.GROC)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.NEGRE, Colors.NEGRE, Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.GROC, Colors.TARONJA, Colors.VERMELL, Colors.BLAU)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC),
                    codiFacil.checkCodi(List.of(Colors.BLAU, Colors.TARONJA, Colors.MAGENTA, Colors.TARONJA)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC, Colors.NEGRE, Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.BLAU, Colors.TARONJA, Colors.VERMELL, Colors.BLAU)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC, Colors.NEGRE, Colors.NEGRE, Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.BLAU, Colors.BLAU, Colors.VERMELL, Colors.GROC)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC, Colors.BLANC, Colors.NEGRE),
                    codiFacil.checkCodi(List.of(Colors.BLAU, Colors.GROC, Colors.MAGENTA, Colors.BLAU)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC, Colors.BLANC),
                    codiFacil.checkCodi(List.of(Colors.BLAU, Colors.GROC, Colors.MAGENTA, Colors.TARONJA)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
        error = null;
        try {
            assertEquals(List.of(Colors.BLANC, Colors.BLANC, Colors.BLANC),
                    codiFacil.checkCodi(
                            List.of(Colors.BLAU, Colors.TARONJA, Colors.BLAU, Colors.VERMELL)));
        } catch (InvalidCodiColors | InvalidCodiLength e) {
            // TODO Auto-generated catch block
            error = e.getMessage();
        }
        assertNull(error);
    }
}
