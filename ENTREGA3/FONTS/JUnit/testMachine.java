
import org.junit.Test;

import domain.Codi;
import domain.Codi.Dificultat;
import domain.Machine;

public class testMachine {

  Codi codi1 = new Codi(Dificultat.FACIL);
  Machine maquina1 = new Machine(codi1);
  Codi codi2 = new Codi(Dificultat.MITJA);
  Machine maquina2 = new Machine(codi2);
  Codi codi3 = new Codi(Dificultat.DIFICIL);
  Machine maquina3 = new Machine(codi3);

  @Test
  public void testMaquinaGeneradorCodiFacil() {
    assert (maquina1.getCode().size() == codi1.getPosicions());
  }

  @Test
  public void testMaquinaGeneradorCodiMitja() {
    assert (maquina2.getCode().size() == codi2.getPosicions());
  }

  @Test
  public void testMaquinaGeneradorCodiDificil() {
    assert (maquina3.getCode().size() == codi3.getPosicions());
  }
}
