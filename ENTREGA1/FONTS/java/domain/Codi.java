package domain;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

public class Codi {
    public enum Dificultat {
        FACIL, MITJA, DIFICIL
    }

    public enum Colors {
        BLANC, NEGRE, BLAU, VERMELL, VERD, GROC, TARONJA, MAGENTA, CIAN, LIMA
    }

    private List<Colors> availColors;
    private int posicions;
    private List<Colors> codi;

    public Codi(Dificultat dificultat) {
        switch (dificultat) {
            case FACIL:
                posicions = 4;
                availColors = List.of(Colors.BLAU, Colors.VERMELL, Colors.VERD, Colors.GROC, Colors.TARONJA,
                        Colors.MAGENTA);
                break;
            case MITJA:
                posicions = 5;
                availColors = List.of(Colors.BLAU, Colors.VERMELL, Colors.VERD, Colors.GROC, Colors.TARONJA,
                        Colors.MAGENTA, Colors.CIAN);
                break;
            case DIFICIL:
                posicions = 6;
                availColors = List.of(Colors.BLAU, Colors.VERMELL, Colors.VERD, Colors.GROC, Colors.TARONJA,
                        Colors.MAGENTA, Colors.CIAN, Colors.LIMA);
                break;

        }
    }

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

    public List<Colors> getCodi() {
        return codi;
    }

    public List<Colors> getAvailColors() {
        return availColors;
    }

    public Integer getPosicions() {
        return posicions;
    }

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

    public List<Colors> checkCodiSeq(List<Colors> test) throws InvalidCodiColors, InvalidCodiLength {
        //if (test.size() != this.posicions) {
          //  throw new InvalidCodiLength(this.posicions, test.size());
        //}
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

}
