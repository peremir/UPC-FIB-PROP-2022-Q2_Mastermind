package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

/**
 * Aquesta classe representa una partida del joc MasterMind
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @author Gerard Oliva Viñas
 * @version 31.5.2023
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Partida.java">Partida.java</a>
 */

public class Partida {

    /** Atributs **/
    private Integer id;
    private Date dataIni;
    private Integer rounds;
    private Integer ronda_actual;
    private transient Codemaker codemaker;
    private Codebreaker codebreaker;
    private Dificultat dificultat;
    private String nom;
    private Integer punts_huma;
    private Integer punts_maquina;
    private List<Ronda> rondes;
    private Boolean current_codemaker;
    private Boolean current_codebreaker;
    private Boolean estretegia;

    /**
     * Constructor
     * 
     * @throws InvalidCodiColors    Si el codi introduït conté colors no vàlids
     * 
     * @throws InvalidCodiLength    Si el codi introduït no té la longitud correcte
     * 
     * @throws codiAlreadyGenerated Si el codi introduït ja ha estat generat
     * 
     * @param rondes:      nombre de rondes que tindra la partida
     * 
     * @param dificultat:  dificultat de la partida
     * 
     * @param codebreaker: true si el huma es el codebreaker, false en el cas
     *                     contrari
     * 
     * @param codemaker:   true si el huma es el codemaker, false en el cas
     *                     contrari
     */
    public Partida(Integer rondes, Dificultat dificultat, Boolean codebreaker, Boolean codemaker)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        this.dataIni = new Date();
        this.dataIni.setTime(System.currentTimeMillis());
        this.ronda_actual = 0;
        this.rounds = rondes;
        this.dificultat = dificultat;
        this.rondes = new ArrayList<Ronda>();
        this.current_codebreaker = codebreaker;
        this.current_codemaker = codemaker;
        this.punts_huma = 0;
        this.punts_maquina = 0;
        this.id = 0;
    }

    /**
     * Mètode per assignar un id a la partida
     * 
     * @param id: id de la partida
     */
    public void setID(Integer id) {
        this.id = id;
    }

    /**
     * Mètode per crear una nova ronda
     * 
     * @throws InvalidCodiColors    Si el codi introduït conté colors no vàlids
     * 
     * @throws InvalidCodiLength    Si el codi introduït no té la longitud correcte
     * 
     * @throws codiAlreadyGenerated Si el codi introduït ja ha estat generat
     * 
     * @param codi: codi que s'utilitzarà a la ronda
     */
    public void setupRonda(List<Colors> codi) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        Codi code = new Codi(this.dificultat);
        if (codi == null) {
            List<Colors> colors = new ArrayList<>();
            for (int i = 0; i < code.getPosicions(); i++) {
                colors.add(Colors.BLAU);
            }
            codi = colors;
        }
        if (this.current_codemaker && !this.current_codebreaker) {
            code.setCodi(codi);
            this.codemaker = new Usuari(code);
            this.codebreaker = new Codebreaker(estretegia, code);
        } else {
            this.codemaker = new Machine(code);
            this.codebreaker = new Codebreaker(estretegia, code);
        }
        Ronda r = new Ronda(this.dificultat, this.codebreaker, this.codemaker);
        this.rondes.add(r);
        if (this.rondes.size() > 1)
            this.ronda_actual++;
    }

    /**
     * Mètode que es crida quan es vol carregar una partida,
     * per carregar la ronda actual
     * 
     * @throws InvalidCodiColors    Si el codi introduït conté colors no vàlids
     * 
     * @throws InvalidCodiLength    Si el codi introduït no té la longitud correcte
     * 
     * @throws codiAlreadyGenerated Si el codi introduït ja ha estat generat
     * 
     */
    public void carregarPartida() throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        Codi code = new Codi(this.dificultat);
        List<Colors> codi = rondes.get(ronda_actual).getCodi();
        if (this.current_codemaker && !this.current_codebreaker) {
            code.setCodi(codi);
            this.codemaker = new Usuari(code);
            this.codebreaker = new Codebreaker(estretegia, code);
        } else {
            this.codemaker = new Machine(code);
            this.codebreaker = new Codebreaker(estretegia, code);
        }
        rondes.get(ronda_actual).setCodebreaker(this.codebreaker);
        rondes.get(ronda_actual).setCodemaker(this.codemaker);
    }

    /**
     * Retorna una possible solució al codi
     * 
     * @return solució al codi
     * @throws Exception
     */
    public List<Colors> getAjuda() throws Exception {
        return this.rondes.get(ronda_actual).getAjuda();
    }

    /**
     * Mètode per obtenir si el codemaker està instanciat
     * 
     * @return true si el codemaker està instanciat, false en cas contrari
     */
    public Boolean codeSet() {
        return this.codemaker != null;
    }

    /**
     * Mètode que retorna el id de la partida
     * 
     * @return id de la partida
     */
    public Integer getID() {
        return this.id;
    }

    /**
     * Mètode per posar el nom a la partida
     * 
     * @param nom: nom de la partida
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Mètode per obtenir el nom de la partida
     * 
     * @return nom de la partida
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Mètode per obtenir la dificultat de la partida
     * 
     * @return dificultat de la partida
     */
    public Dificultat getDificultat() {
        return this.dificultat;
    }

    /**
     * Mètode per obtenir la data en que has va començar la partida
     * 
     * @return data en que has va començar la partida
     */
    public Date getDataIni() {
        return this.dataIni;
    }

    /**
     * Mètode per obtenir una possible solució
     * 
     * @return possible solució
     * 
     * @throws Exception
     */

    public List<Colors> getGuess() throws Exception {
        return this.rondes.get(this.ronda_actual).getGuess();
    }

    /**
     * Comprova si la correcció feta per l'humà és correcte
     * 
     * @param negres:   número de posicions negres
     * 
     * @param blanques: número de posicions blanques
     * 
     * @param guess:    possible solució que ha fet la màquina
     * 
     * @return true si la correcció és correcte, false en cas contrari
     * 
     * @throws InvalidCodiColors Si el codi introduït conté colors no vàlids
     * @throws InvalidCodiLength Si el codi introduït no té la longitud correcte
     */
    public Boolean checkCorreccio(Integer negres, Integer blanques, List<Colors> guess)
            throws InvalidCodiColors, InvalidCodiLength {
        return this.rondes.get(this.ronda_actual).checkCorreccio(negres, blanques, guess);
    }

    /**
     * Mètode que ens indica si la partida ja ha acabat
     * 
     * @return true si la partida ha acabat, false en cas contrari
     */
    public Boolean partidaAcabada() {
        return ((this.ronda_actual + 1) >= this.rounds) && getRondaAcabada();
    }

    /**
     * Comprova si donada una possible solució, aquesta és correcte
     * 
     * @param codi: possible solució
     * 
     * @return correcció de la possible solució
     * @throws InvalidCodiColors Si el codi introduït conté colors no vàlids
     * @throws InvalidCodiLength Si el codi introduït no té la longitud correcte
     */
    public List<Colors> comprovarCodi(List<Colors> codi) throws InvalidCodiColors, InvalidCodiLength {
        return this.rondes.get(this.ronda_actual).comprovarCodi(codi);
    }

    /**
     * Mètode que ens indica quantes posicions té el codi que estem fent
     * servir en aquesta partida
     * 
     * @return número de posicions del codi
     */
    public Integer posicions() {
        return this.rondes.get(this.ronda_actual).posicions();
    }

    /**
     * Mètode que ens indica quants colors diferents podem fer servir per generar
     * el codi que estem fent
     * servir en aquesta partida
     * 
     * @return número de colors diferents del codi
     */
    public List<Colors> colorsAvailable() {
        return this.rondes.get(this.ronda_actual).colorsAvailable();
    }

    /**
     * Mètode que ens retorna el codi que estem fent servir en aquesta partida
     * 
     * @return codi que estem fent servir en aquesta partida
     */
    public List<Colors> getCodi() {
        return this.rondes.get(this.ronda_actual).getCodi();
    }

    /**
     * Mètode que ens retorna quin és el torn en el que estem dins
     * la ronda actual
     * 
     * @return torn actual
     */
    public Integer getTornActual() {
        return this.rondes.get(this.ronda_actual).getTornActual();
    }

    /**
     * Mètode que ens indica si l'humà està jugant com a codemaker
     * 
     * @return true si l'humà està jugant com a codemaker, false en cas contrari
     */
    public Boolean rol() {
        return this.current_codemaker;
    }

    /**
     * Retorna l'objecte codi que s'està utilitzant en aquesta partida
     * 
     * @return objecte codi
     */
    public Codi getCodiObject() {
        return this.rondes.get(this.ronda_actual).getCodiObject();
    }

    /**
     * Mètode que ens indica si la partida ha estat guanyada per l'humà
     * 
     * @return true si la partida ha estat guanyada per l'humà, false en cas
     *         contrari
     */
    public Boolean partidaGuanyada() {
        return (punts_huma > punts_maquina);
    }

    /**
     * Mètode per establir l'estrategia que s'utilitzarà en aquesta partida
     * 
     * @param estrategia: true si es vol utilitzar l'estrategia FiveGuess, false en
     *                    cas que vulguis utilitzar l'estrategia Genetic
     */
    public void setEstrategia(Boolean estrategia) {
        this.estretegia = estrategia;
    }

    /**
     * Mètode que ens indica si la ronda que s'està jugant ha acabat
     * 
     * @return true si la ronda ha acabat, false en cas contrari
     */
    public Boolean getRondaAcabada() {
        return this.rondes.get(this.ronda_actual).rondaAcabada();
    }

    /**
     * Mètode que ens indica si la ronda que s'està jugant ha estat guanyada
     * 
     * @return true si la ronda ha estat guanyada, false en cas contrari
     */
    public Boolean getRondaGuanyada() {
        return this.rondes.get(this.ronda_actual).rondaGuanyada();
    }

    /**
     * Mètode per establir el número de rondes que es jugaran en aquesta partida
     * 
     * @param rondes: número de rondes que es jugaran en aquesta partida
     */
    public void setRondes(Integer rondes) {
        this.rounds = rondes;
    }

    /**
     * Mètode que canvia els rols i suma la puntuació de la ronda actual
     */
    public void nextTurn() {
        if (current_codemaker) {
            this.current_codemaker = false;
            this.current_codebreaker = true;
            this.punts_huma += this.rondes.get(this.ronda_actual).getPunts();
        } else {
            this.current_codemaker = true;
            this.current_codebreaker = false;
            this.punts_maquina += this.rondes.get(this.ronda_actual).getPunts();
        }
    }

    /**
     * Mètode que ens retorna la ronda actual
     * 
     * @return ronda actual
     */
    public Integer getRondaActual() {
        return this.ronda_actual;
    }

    /**
     * Mètode que ens retorna quin ha estat l'últim intent del codebeaker d'esbrinar
     * el codi
     * 
     * @return últim intent del codebreaker
     */
    public List<Colors> getLastGuess() {
        return this.rondes.get(this.ronda_actual).getLastGuess();
    }

    /**
     * Mètode que ens retorna quina ha estat la resposta a l'últim intent del
     * codebreaker
     * 
     * @return resposta a l'últim intent del codebreaker
     */
    public List<Colors> getLastResponse() {
        return this.rondes.get(this.ronda_actual).getLastResponse();
    }

    /**
     * Mètode que ens retorna el número de rondes que es jugaran en aquesta partida
     * 
     * @return número de rondes que es jugaran en aquesta partida
     */
    public Integer getRondes() {
        return this.rounds;
    }

    /**
     * Mètode que ens retorna el número de torns que es jugaran en la ronda actual
     * 
     * @return número de torns que es jugaran en la ronda actual
     */
    public Integer getTorns() {
        return this.rondes.get(this.ronda_actual).getTorns();
    }

    /**
     * Mètode que ens retorna la puntuació de l'humà en aquesta partida
     * 
     * @return puntuació de l'humà en aquesta partida
     */
    public Integer getPuntuacioHuma() {
        return this.punts_huma;
    }

    /**
     * Mètode que ens retorna la puntuació de la màquina en aquesta partida
     * 
     * @return puntuació de la màquina en aquesta partida
     */
    public Integer getPuntuacioMaquina() {
        return this.punts_maquina;
    }
}