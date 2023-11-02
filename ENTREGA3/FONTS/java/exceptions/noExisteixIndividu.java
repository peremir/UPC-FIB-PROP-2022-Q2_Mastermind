package exceptions;

/**
 * Salta si s'intenta accedir a un individu que no existeix.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * 
 * @version 31.5.2021
 * @author Aina Gomez Pinyol
 */
public class noExisteixIndividu extends Exception {

    public noExisteixIndividu(int index) {
        super("No exiteix cap individu amb l'index " + index);
    }
}
