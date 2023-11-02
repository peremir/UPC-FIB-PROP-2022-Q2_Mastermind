package domain;

import java.util.List;

import domain.Codi.Colors;

/**
 * Respresenta el jugador que juga com a codemaker.
 * <p>
 * Aquesta interficie no conte cap atribut, pero si que conte dos metodes, un que
 * retorna el codi que ha generat el codemaker i un altre que retorna el rol
 * <p>
 * <strong>Author:</strong> Pere Mir Olivares
 * @author Pere Mir Olivares
 * @version 1.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Codemaker.java> Codemaker.java </a>
 */

public interface Codemaker {

  /** Funcions **/

  /**
   * Mètode que retorna el codi del jugador com a llista.
   * 
   * @return Llista de colors del codi del jugador.
   */
  public List<Colors> getCode();

  /**
   * Mètode que retorna si el rol del jugador es codemaker o no.
   * 
   * @return true si l'usuari es codemaker, altrament false si es maquina.
   */
  public Boolean rol();
}