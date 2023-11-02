package domain;

import java.util.List;

import domain.Codi.Colors;

public interface Codemaker {

  /** Funcions **/

  // Genera el codi, si es Machine aleatori, si es Usuari, el que li passi
  // l'usuari

  public List<Colors> getCode();

  // Retorna el codi generated

  public Boolean rol();
}