package exceptions;

public class codiAlreadyGenerated extends Exception {
    public codiAlreadyGenerated() {
        super("El codi ja ha estat generat");
    }
}
