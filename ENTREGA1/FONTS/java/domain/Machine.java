package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.Codi.Colors;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

public class Machine implements Codemaker {

  /** Atributs **/

  private Random random;
  private Codi userCode;

  /** Constructor **/

  public Machine(Codi codi) {

    userCode = codi;
    random = new Random(System.currentTimeMillis());
    GenerarCodi();
  }

  /** Funcions **/

  private void GenerarCodi() {

    List<Colors> colorList = new ArrayList<Colors>();
    List<Colors> returnCode = new ArrayList<Colors>();

    colorList = userCode.getAvailColors();
    int num = userCode.getPosicions();

    for (int i = 0; i < num; ++i) {

      int rand = random.nextInt(num);
      returnCode.add(colorList.get(rand));
    }

    try {
      userCode.setCodi(returnCode);
    } catch (codiAlreadyGenerated e) {
      // CodialreadyGenerated
      e.printStackTrace();
    } catch (InvalidCodiLength e) {
      // InvalidCodiLength
      e.printStackTrace();
    } catch (InvalidCodiColors e) {
      // InvalidCodiColors
      e.printStackTrace();
    }
  }

  public List<Colors> getCode() {
    return userCode.getCodi();
  }

  public Boolean rol() {
    return false;
  }
}