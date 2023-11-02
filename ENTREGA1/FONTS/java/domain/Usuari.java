package domain;

import java.util.List;

import domain.Codi.Colors;

public class Usuari implements Codemaker {

  /** Atributs **/

  private Codi userCode;

  /** Constructor **/

  public Usuari(Codi codi) {

    userCode = codi;
  }

  /** Funcions **/

  public List<Colors> getCode() {
    return userCode.getCodi();
  }

  public Boolean rol() {
    return true;
  }
}
