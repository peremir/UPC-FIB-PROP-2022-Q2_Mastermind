package exceptions;

public class InvalidCodiLength extends Exception {
    public InvalidCodiLength(int expected, int actual) {
        super("La longitud del codi ha de ser " + expected + " i has introduït:  " + actual + ".");
    }
}
