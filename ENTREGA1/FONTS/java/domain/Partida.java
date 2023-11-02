package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

public class Partida {

    /** Atributs **/
    private Integer id;
    private Date dataIni;
    private Integer torns;
    private Integer torn_actual;
    private Codi codi;
    private Codemaker codemaker;
    private Dificultat dificultat;
    private Codebreaker codebreaker;
    private List<Colors> codi_resposta;
    private Boolean acabada = false;
    private Boolean guanyada = false;
    private String nom;

    /**
     * Constructor
     * 
     * @throws InvalidCodiColors
     * @throws InvalidCodiLength
     * @throws codiAlreadyGenerated
     **/
    public Partida(Integer id, Integer torns, Dificultat dificultat, Codebreaker codebreaker, Codemaker codemaker)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        this.id = id;
        this.dataIni = new Date();
        this.dataIni.setTime(System.currentTimeMillis());
        this.torn_actual = 0;
        this.torns = torns;
        this.codi = new Codi(dificultat);
        this.codemaker = codemaker;
        this.codebreaker = codebreaker;
        this.codi.setCodi(codemaker.getCode());
        this.dificultat = dificultat;
    }

    public Integer getID() {
        return this.id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public Dificultat getDificultat() {
        return this.dificultat;
    }

    public Date getDataIni() {
        return this.dataIni;
    }

    private Boolean checkAcabada(List<Colors> codi) {
        Integer sum_negres = 0;
        for (Colors c : codi) {
            if (c == Colors.BLANC)
                sum_negres++;
        }
        if (sum_negres == this.codi.getPosicions()) {
            this.acabada = true;
            this.guanyada = true;
        }
        return this.acabada;
    }

    public Boolean partidaGuanyada() {
        return this.guanyada;
    }

    private void jugarTorn(List<Colors> codi) {
        this.torn_actual++;
        if (this.torn_actual == this.torns)
            this.acabada = true;
        else if (checkAcabada(codi))
            this.acabada = true;
    }

    public List<Colors> getGuess() throws Exception {
        if (this.torn_actual == 0) {
            List<Colors> guess = new ArrayList<Colors>();
            return this.codebreaker.jugarTorn(guess);
        } else
            return this.codebreaker.jugarTorn(this.codi_resposta);
    }

    public Boolean checkCorreccio(Integer negres, Integer blanques, List<Colors> guess)
            throws InvalidCodiColors, InvalidCodiLength {
        Integer sum_negres = 0;
        Integer sum_blanques = 0;
        List<Colors> resposta = this.codi.checkCodi(guess);
        for (Colors c : resposta) {
            if (c == Colors.NEGRE)
                sum_negres++;
            else if (c == Colors.BLANC)
                sum_blanques++;
        }
        this.codi_resposta = resposta;
        Boolean correcte = (sum_negres == blanques && sum_blanques == negres);
        if (correcte) {
            jugarTorn(resposta);
        }
        return correcte;
    }

    public Boolean partidaAcabada() {
        return this.acabada;
    }

    public List<Colors> comprovarCodi(List<Colors> codi) throws InvalidCodiColors, InvalidCodiLength {
        List<Colors> codi_resposta = this.codi.checkCodi(codi);
        jugarTorn(codi_resposta);
        return codi_resposta;
    }

    public Integer posicions() {
        return this.codi.getPosicions();
    }

    public List<Colors> colorsAvailable() {
        return this.codi.getAvailColors();
    }

    public List<Colors> getCodi() {
        return this.codi.getCodi();
    }

    public Integer getTornActual() {
        return this.torn_actual;
    }

    public Integer getTorns() {
        return this.torns;
    }

    public Boolean rol() {
        return this.codemaker.rol();
    }

    public Codi getCodiObject() {
        return this.codi;
    }
}