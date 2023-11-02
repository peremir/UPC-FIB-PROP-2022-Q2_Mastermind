package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.Codi.Colors;

import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

/**
 * Respresenta la maquina quan juga com a codemaker.
 * <p>
 * Aquesta classe consisteix en un userCode que representa el codi que la maquina
 * genera i un atribut random que es el que s'encarrega de generar el codi aleatori.
 * <p>
 * <strong>Author:</strong> Pere Mir Olivares
 * @author Pere Mir Olivares
 * @version 1.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Machine.java> Machine.java </a>
 */

public class Machine implements Codemaker {

  /** Atributs **/

  /**
   * Atributs privats de la classe Machine.
   */
  private Random random;
  private Codi userCode;

  /** Constructor **/

  /**
   * Constructora de la classe Machine.
   * 
   * @param codi Codi que la maquina ha generat com a codi per a la partida que es juga.
   */
  public Machine(Codi codi) {

    userCode = codi;
    random = new Random(System.currentTimeMillis());
    GenerarCodi();
  }

  /** Funcions **/

  /**
   * Mètode que genera el codi de la maquina.
   */
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

  /**
   * Mètode que retorna el codi de la maquina com a llista.
   * 
   * @return Llista de colors del codi de la maquina.
   */
  public List<Colors> getCode() {
    return userCode.getCodi();
  }

  /**
   * Mètode que retorna si el rol de la maquina es codemaker o no.
   * 
   * @return false si la maquina es codemaker, true altrament.
   */
  public Boolean rol() {
    return false;
  }
}