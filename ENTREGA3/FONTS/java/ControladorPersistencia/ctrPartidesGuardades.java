package ControladorPersistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import domain.Partida;
import exceptions.PartidaNoTrobada;

/**
 * Classe que representa el controlador de persistència de les partides
 * guardades
 * <p>
 * <strong>Author:</strong> Pol Contreras and Gerard Oliva
 * 
 * @version 1.6.2023
 * @author Pol Contreras and Gerard Oliva
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/ControladorPersistencia/ctrPartidesGuardades.java">ctrPartidesGuardades.java</a>
 */
public class ctrPartidesGuardades {

    /**
     * Atributs de la classe
     */
    private List<Partida> partides_guardades;
    Gson gson;
    Integer id;

    /**
     * Constructora de la classe
     */
    public ctrPartidesGuardades() {
        this.partides_guardades = new ArrayList<Partida>();
        GsonBuilder gsonBilder = new GsonBuilder();
        this.gson = gsonBilder.setPrettyPrinting().create();
        this.id = 0;
        carregarPartidesDesDeArxiu();
        for (Partida p : partides_guardades) {
            if (p.getID() > this.id) {
                this.id = p.getID();
            }
        }
        this.id++;
    }

    /**
     * Mètode privat que guarda la llista de partides al fitxer
     * dadesPartidesGuardades.json
     */
    private void guardarPartidesGuardadesEnArxiu() {
        File fitxer = new File("dadesPartidesGuardades.json");
        if (!fitxer.exists()) {
            try {
                fitxer.createNewFile();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        Type tipusList = new TypeToken<List<Partida>>() {
        }.getType();
        String json = this.gson.toJson(partides_guardades, tipusList);
        try (FileWriter fw = new FileWriter("dadesPartidesGuardades.json")) {
            fw.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode privat que carrega a partides la llista de partides guardades al
     * fitxer dadesPartidesGuardades.json
     */
    private void carregarPartidesDesDeArxiu() {
        File fitxer = new File("dadesPartidesGuardades.json");
        if (!fitxer.exists()) {
            try {
                fitxer.createNewFile();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (fitxer.length() == 0) {
            try (FileWriter fw = new FileWriter("dadesPartidesGuardades.json")) {
                fw.write("[]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Type tipusList = new TypeToken<List<Partida>>() {
            }.getType();
            this.partides_guardades = this.gson.fromJson(new FileReader("dadesPartidesGuardades.json"), tipusList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Mètode que carrega una partida de la llista de partides guardades
     *
     * @param id identificador de la partida que es vol carregar
     * @return Partida a carregar
     */
    public Partida getPartida(Integer id) {
        carregarPartidesDesDeArxiu();
        Partida p = null;
        for (Partida partida : partides_guardades) {
            if (partida.getID().equals(id)) {
                p = partida;
                break;
            }
        }
        return p;
    }

    /**
     * Mètode que retorna una matriu de Strings amb les partides guardades
     * 
     * @return Matriu de String amb les partides guardades
     */
    public Object[][] getPartidesPresentacio() {
        carregarPartidesDesDeArxiu();

        Object[][] taula = new Object[partides_guardades.size()][5];

        int i = 0;
        for (Partida p : this.partides_guardades) {
            taula[i][0] = p.getID();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            String date = sdf.format(p.getDataIni());
            taula[i][1] = date;
            taula[i][3] = p.getDificultat();
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            String date1 = sdf1.format(p.getDataIni());
            taula[i][2] = date1;
            if (p.rol()) {
                taula[i][4] = "Codemaker";
            } else {
                taula[i][4] = "Codebreaker";
            }

            ++i;
        }
        return taula;
    }

    /**
     * Mètode que guarda una partida a la llista de partides guardades
     * 
     * @param p partida que es vol guardar
     * @throws exceptions.LimitPartidesGuardades si ja hi ha 20 partides guardades
     */
    public void guardarPartida(Partida p) throws exceptions.LimitPartidesGuardades {
        carregarPartidesDesDeArxiu();
        if (this.partides_guardades.size() >= 20)
            throw new exceptions.LimitPartidesGuardades();
        else {
            if (p.getID() == 0) {
                p.setID(this.id);
                this.id++;
                this.partides_guardades.add(p);
            } else {
                List<Partida> partides_a_eliminar = new ArrayList<Partida>();
                for (Partida p2 : this.partides_guardades) {
                    if (p2.getID() == p.getID()) {
                        partides_a_eliminar.add(p2);
                    }
                }
                this.partides_guardades.removeAll(partides_a_eliminar);
                this.partides_guardades.add(p);
            }
        }
        guardarPartidesGuardadesEnArxiu();
    }

    /**
     * Mètode que elimina una partida en concret de la llista de partides guardades
     * 
     * @param p partida que es vol eliminar
     * @throws PartidaNoTrobada si la partida que es vol eliminar no es troba a la
     *                          llista de partides guardades
     */
    public void eliminarPartida(Partida p) throws PartidaNoTrobada {
        carregarPartidesDesDeArxiu();
        List<Partida> partides_a_eliminar = new ArrayList<Partida>();
        for (Partida p2 : this.partides_guardades) {
            if (p2.getID() == p.getID()) {
                partides_a_eliminar.add(p2);
            }
        }
        if (partides_a_eliminar.size() == 0)
            throw new exceptions.PartidaNoTrobada(p);
        else
            this.partides_guardades.removeAll(partides_a_eliminar);
        guardarPartidesGuardadesEnArxiu();
    }
}
