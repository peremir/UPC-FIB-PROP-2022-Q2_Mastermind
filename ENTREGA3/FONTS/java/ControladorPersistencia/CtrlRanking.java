package ControladorPersistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import domain.Partida;

/**
 * Classe que representa el controlador del ranking de partides
 * <p>
 * <strong>Author:</strong> Pol Contreras and Gerard Oliva
 * 
 * @version 01.06.2023
 * @author Pol Contreras and Gerard Oliva
 * @see <a
 *      href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/ControladorPersistencia/CtrlRanking.java>CtrlRanking.java</a>
 */
public class CtrlRanking {
    /**
     * Atributs de la classe
     */
    private List<Partida> partides;
    Gson gson;

    /**
     * Constructora de la classe
     */
    public CtrlRanking() {
        partides = new ArrayList<Partida>();
        GsonBuilder gsonBilder = new GsonBuilder();
        this.gson = gsonBilder.setPrettyPrinting().create();
        carregarRankingDesDeArxiu();
    }

    /**
     * Mètode privat que guarda les partides del ranking al fitxer dadesRanking.json
     */
    private void guardarRankingEnArxiu() {
        File fitxer = new File("dadesRanking.json");
        if (!fitxer.exists()) {
            try {
                fitxer.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        Type tipusList = new TypeToken<List<Partida>>() {
        }.getType();
        String json = this.gson.toJson(partides, tipusList);

        try (FileWriter fw = new FileWriter("dadesRanking.json")) {
            fw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode privat que carrega a l'atribut partides, les partides del ranking
     * guardades a dadesRanking.json
     */
    private void carregarRankingDesDeArxiu() {
        File fitxer = new File("dadesRanking.json");
        if (!fitxer.exists()) {
            try {
                fitxer.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (fitxer.length() == 0) {
            try (FileWriter fw = new FileWriter("dadesRanking.json")) {
                fw.write("[]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileReader fr = new FileReader("dadesRanking.json")) {
            Type tipusList = new TypeToken<List<Partida>>() {
            }.getType();
            partides = this.gson.fromJson(fr, tipusList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode que afegeix una partida al ranking
     * 
     * @param p partida que es guardarà al ranking
     */
    public void afegirPartida(Partida p) {
        // carregarRankingDesDeArxiu();
        partides.add(p);
        Collections.sort(partides, new Ordena());
        guardarRankingEnArxiu();
    }

    /**
     * Mètode que retorna el ranking de partides guardades
     * 
     * @return matriu de Strings amb el ranking
     */
    public Object[][] getRanking() {
        carregarRankingDesDeArxiu();

        Object[][] taula = new Object[partides.size()][4];

        int i = 0;
        for (Partida p : partides) {
            taula[i][0] = p.getNom();
            taula[i][1] = p.getRondes();
            taula[i][2] = p.getDificultat();

            if (!p.rol())
                taula[i][3] = "Codebreaker";
            else
                taula[i][3] = "Codemaker";
            ++i;
        }

        return taula;
    }

    /**
     * Mètode que reinicia el ranking esborrant les dades de les partides guardades
     */
    public void reiniciarRanking() {
        partides.clear();
        guardarRankingEnArxiu();
    }

    /**
     * Classe que implementa el mètode amb els criteris d'ordenació del ranking
     *
     * @version 1.6.2023
     * @author Pol
     */
    class Ordena implements Comparator<Partida> {
        @Override
        public int compare(Partida p1, Partida p2) {
            float c1 = (float) p1.getPuntuacioHuma() / (float) p1.getPuntuacioMaquina();
            float c2 = (float) p2.getPuntuacioHuma() / (float) p2.getPuntuacioMaquina();

            if (p1.posicions() > p2.posicions())
                return -1;
            else if (p1.posicions() < p2.posicions())
                return 1;
            else {
                if (c1 > c2)
                    return -1;
                else if (c1 < c2)
                    return 1;
                else
                    return p2.getRondes() - p1.getRondes();
            }
        }
    }
}