package domain;

import java.util.List;

import domain.Codi.Colors;

/**
 * Respresenta l'usuari que escriu el codi.
 * <p>
 * Aquesta classe consisteix en un userCode que es un Codi que conte un llistat de Colors
 * els quals son els que l'usuari ha escrit com a codi per a la partida que es juga.
 * <p>
 * <strong>Author:</strong> Pere Mir Olivares
 * @author Pere Mir Olivares
 * @version 1.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Usuari.java> Usuari.java </a>
 */

public class Usuari implements Codemaker {

  /** Atributs **/

  /**
   * Atribut privat de la classe Usuari.
   */
  private Codi userCode;

  /** Constructora **/

  /**
  * Constructora de la classe Usuari.
  * 
  * @param codi Codi que l'usuari ha escrit com a codi per a la partida que es juga.
  */
  public Usuari(Codi codi) {

    userCode = codi;
  }

  /** Funcions **/

  /**
     * Mètode que retorna el codi de l'usuari com a llista.
     * 
     * @return Llista de colors del codi de l'usuari.
     */
  public List<Colors> getCode() {
    return userCode.getCodi();
  }

  /**
     * Mètode que retorna si el rol de l'usuari es codemaker o no.
     * 
     * @return true si l'usuari es codemaker, false altrament.
     */
  public Boolean rol() {
    return true;
  }
}
