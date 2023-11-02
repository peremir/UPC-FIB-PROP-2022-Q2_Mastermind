package exceptions;

public class NombreTornsInvalid extends Exception {
    public NombreTornsInvalid(Integer torns) {
        super("El nombre de torns ha d'estar entre 6 i 12, i has introduït:  " + torns + ".");
    }
}
