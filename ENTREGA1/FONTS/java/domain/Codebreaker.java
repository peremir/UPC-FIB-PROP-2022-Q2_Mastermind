package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Codi.Colors;;

public class Codebreaker {

    private List<List<Colors>> jugades = new ArrayList<>();
    private Estrategia estrategia;

    public Codebreaker(boolean fg, Codi c) {

        if (fg)
            estrategia = new FiveGuess(c);
        // else GENETIC.

    }

    public List<Colors> jugarTorn(List<Colors> cm) throws Exception {

        List<Colors> cb = new ArrayList<>();
        if (jugades.size() != 0) {
            int indexLastElement = jugades.size() - 1;
            cb = jugades.get(indexLastElement);
        }
        cb = estrategia.jugarTornMaquina(cm, cb);
        jugades.add(cb);
        return cb;

    }

}
