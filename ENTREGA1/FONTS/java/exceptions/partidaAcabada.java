package exceptions;

public class partidaAcabada extends Exception {
    public partidaAcabada() {
        super("La partida ja ha acabat.");
    }
}
