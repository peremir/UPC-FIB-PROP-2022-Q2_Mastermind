package domain;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

/**
 * Aquesta classe defineix el codi que s'utilitza per jugar una partida del
 * Mastermind
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @version 31.5.2023
 * @author Gerard Oliva Viñas
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Codi.java">Codi.java</a>
 */

public class Codi {
    /**
     * Dificultats disponibles per a una partida del Mastermind
     */
    public enum Dificultat {
        FACIL, MITJA, DIFICIL
    }

    /**
     * Colors disponibles per a una partida del Mastermind
     */
    public enum Colors {
        BLANC, NEGRE, BLAU, VERMELL, VERD, GROC, TARONJA, MAGENTA, CIAN, LIMA
    }

    /**
     * Atributs de la classe Codi
     */
    private List<Colors> availColors;
    private int posicions;
    private List<Colors> codi;
    private Integer torns;

    /**
     * Constructor de la classe Codi
     * 
     * @param dificultat Dificultat de la partida
     */
    public Codi(Dificultat dificultat) {
        switch (dificultat) {
            case FACIL:
                posicions = 4;
                availColors = List.of(Colors.BLAU, Colors.VERMELL, Colors.VERD, Colors.GROC, Colors.TARONJA,
                        Colors.MAGENTA);
                torns = 16;
                break;
            case MITJA:
                posicions = 5;
                availColors = List.of(Colors.BLAU, Colors.VERMELL, Colors.VERD, Colors.GROC, Colors.TARONJA,
                        Colors.MAGENTA, Colors.CIAN);
                torns = 14;
                break;
            case DIFICIL:
                posicions = 6;
                availColors = List.of(Colors.BLAU, Colors.VERMELL, Colors.VERD, Colors.GROC, Colors.TARONJA,
                        Colors.MAGENTA, Colors.CIAN, Colors.LIMA);
                torns = 12;
                break;

        }
    }

    /**
     * Mètode que retorna el nombre de torns màxims a jugar depenent del codi
     * 
     * @return torns màxims a jugar
     */
    public Integer getTorns() {
        return torns;
    }

    /**
     * Mètode per establir el codi
     * 
     * @param colors Llista de colors que formen el codi
     * @throws codiAlreadyGenerated Si el codi ja ha estat generat
     * @throws InvalidCodiLength    Si la llargada del codi no és correcta
     * @throws InvalidCodiColors    Si algun color del codi no és correcte
     */
    public void setCodi(List<Colors> colors) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        if (codi != null) {
            throw new codiAlreadyGenerated();
        }
        if (colors.size() != posicions) {
            throw new InvalidCodiLength(posicions, colors.size());
        }
        codi = new ArrayList<>();
        for (int i = 0; i < posicions; i++) {
            if (!availColors.contains(colors.get(i))) {
                throw new InvalidCodiColors(colors.get(i));
            }
            codi.add(colors.get(i));
        }
    }

    /**
     * Mètode per obtenir el codi
     * 
     * @return Llista de colors que formen el codi
     */
    public List<Colors> getCodi() {
        return codi;
    }

    /**
     * Mètode per obtenir els colors disponibles per el codi
     * 
     * @return Llista de colors disponibles per el codi
     */
    public List<Colors> getAvailColors() {
        return availColors;
    }

    /**
     * Mètode per obtenir el nombre de posicions del codi
     * 
     * @return Nombre de posicions del codi
     */
    public Integer getPosicions() {
        return posicions;
    }

    /**
     * Mètode per obtenir la dificultat del codi
     * 
     * @return Dificultat del codi
     */
    public Dificultat getDificultat() {

        if (posicions == 4)
            return Dificultat.FACIL;
        else if (posicions == 5)
            return Dificultat.MITJA;
        else
            return Dificultat.DIFICIL;
    }

    /**
     * Mètode per comprovar si el codi és correcte
     * 
     * @param test Llista de colors que formen el codi a comprovar
     * @return Llista de colors que formen la resposta
     * @throws InvalidCodiColors Si algun color del codi no és correcte
     * @throws InvalidCodiLength Si la llargada del codi no és correcta
     */
    public List<Colors> checkCodi(List<Colors> test) throws InvalidCodiColors, InvalidCodiLength {
        if (test.size() != posicions) {
            throw new InvalidCodiLength(posicions, test.size());
        }
        List<Colors> visitatCodi = new ArrayList<Colors>();
        List<Colors> visitatTest = new ArrayList<Colors>();
        for (int c = 0; c < test.size(); ++c) {
            visitatTest.add(Colors.NEGRE);
        }
        for (int c = 0; c < this.posicions; ++c) {
            visitatCodi.add(Colors.NEGRE);
        }
        List<Colors> resposta = new ArrayList<Colors>();
        for (int i = 0; i < test.size(); ++i) {
            if (!this.availColors.contains(test.get(i))) {
                throw new InvalidCodiColors(test.get(i));
            } else if (codi.get(i) == test.get(i)) {
                visitatCodi.set(i, test.get(i));
                visitatTest.set(i, test.get(i));
                resposta.add(Colors.BLANC);
            }
        }
        for (int j = 0; j < this.posicions; ++j) {
            for (int k = 0; k < test.size(); ++k) {
                if (j != k && this.codi.get(j).equals(test.get(k)) && visitatCodi.get(j).equals(Colors.NEGRE)
                        && visitatTest.get(k).equals(Colors.NEGRE)) {
                    resposta.add(Colors.NEGRE);
                    visitatCodi.set(j, test.get(k));
                    visitatTest.set(k, test.get(k));
                }
            }
        }
        return resposta;
    }

    /**
     * Mètode per comprovar si el codi és correcte
     * 
     * @param test Llista de colors que formen el codi a comprovar
     * @return Llista de colors que formen la resposta
     * @throws InvalidCodiColors Si algun color del codi no és correcte
     * @throws InvalidCodiLength Si la llargada del codi no és correcta
     */
    public List<Colors> checkCodiSeq(List<Colors> test) throws InvalidCodiColors, InvalidCodiLength {
        List<Colors> visitatCodi = new ArrayList<Colors>();
        List<Colors> visitatTest = new ArrayList<Colors>();
        for (int c = 0; c < test.size(); ++c) {
            visitatTest.add(Colors.NEGRE);
        }
        for (int c = 0; c < this.posicions; ++c) {
            visitatCodi.add(Colors.NEGRE);
        }
        List<Colors> resposta = new ArrayList<Colors>();
        for (int i = 0; i < test.size(); ++i) {
            if (!this.availColors.contains(test.get(i))) {
                throw new InvalidCodiColors(test.get(i));
            } else if (this.codi.get(i) == test.get(i)) {
                visitatCodi.set(i, test.get(i));
                visitatTest.set(i, test.get(i));
                resposta.add(Colors.BLANC);
            }
        }
        for (int j = 0; j < this.posicions; ++j) {
            for (int k = 0; k < test.size(); ++k) {
                if (j != k && this.codi.get(j).equals(test.get(k)) && visitatCodi.get(j).equals(Colors.NEGRE)
                        && visitatTest.get(k).equals(Colors.NEGRE)) {
                    resposta.add(Colors.NEGRE);
                    visitatCodi.set(j, test.get(k));
                    visitatTest.set(k, test.get(k));
                }
            }
        }
        return resposta;
    }

    /**
     * Mètode per convertir un String a una llista de Colors
     * 
     * @param codi String que representa el codi
     * @return Llista de colors que representa el codi
     */
    public List<Colors> stringToColors(String[] codi) {
        List<Colors> colors = new ArrayList<Colors>();
        for (int i = 0; i < codi.length; ++i) {
            switch (codi[i]) {
                case "BLANC":
                    colors.add(Colors.BLANC);
                    break;
                case "NEGRE":
                    colors.add(Colors.NEGRE);
                    break;
                case "BLAU":
                    colors.add(Colors.BLAU);
                    break;
                case "VERMELL":
                    colors.add(Colors.VERMELL);
                    break;
                case "VERD":
                    colors.add(Colors.VERD);
                    break;
                case "GROC":
                    colors.add(Colors.GROC);
                    break;
                case "TARONJA":
                    colors.add(Colors.TARONJA);
                    break;
                case "MAGENTA":
                    colors.add(Colors.MAGENTA);
                    break;
                case "CIAN":
                    colors.add(Colors.CIAN);
                    break;
                case "LIMA":
                    colors.add(Colors.LIMA);
                    break;
            }
        }
        return colors;
    }

    /**
     * Mètode per convertir una llista de Colors a un String
     * 
     * @param codi Llista de colors que formen el codi
     * @return String que representa el codi
     */
    public String[] colorsToString(List<Colors> codi) {
        String[] colors = new String[codi.size()];
        for (int i = 0; i < codi.size(); ++i) {
            switch (codi.get(i)) {
                case BLANC:
                    colors[i] = "BLANC";
                    break;
                case NEGRE:
                    colors[i] = "NEGRE";
                    break;
                case BLAU:
                    colors[i] = "BLAU";
                    break;
                case VERMELL:
                    colors[i] = "VERMELL";
                    break;
                case VERD:
                    colors[i] = "VERD";
                    break;
                case GROC:
                    colors[i] = "GROC";
                    break;
                case TARONJA:
                    colors[i] = "TARONJA";
                    break;
                case MAGENTA:
                    colors[i] = "MAGENTA";
                    break;
                case CIAN:
                    colors[i] = "CIAN";
                    break;
                case LIMA:
                    colors[i] = "LIMA";
                    break;
            }
        }
        return colors;
    }

}
