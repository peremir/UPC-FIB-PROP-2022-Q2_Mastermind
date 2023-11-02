package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

/**
 * Aquesta classe defineix una ronda de la partida.
 * Una ronda es defineix per un codemaker, un codebreaker, un codi i una
 * dificultat.
 * A mes, una ronda pot estar acabada o no.
 * Si una ronda esta acabada, es pot saber si ha estat guanyada o no.
 * 
 * Aquesta classe es la encarregada de gestionar les jugades de la partida.
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @author Gerard Oliva Viñas
 * @version 31.5.2021
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/Ronda.java">Ronda.java</a>
 */
public class Ronda {

    /** Atributs **/
    private Integer torn_actual;
    private Codi codi;
    private transient Codemaker codemaker;
    private Codebreaker codebreaker;
    private Codebreaker ajuda;
    private Dificultat dificultat;
    private List<Colors> codi_resposta;
    private Boolean acabada = false;
    private Boolean guanyada = false;
    private Integer punts_codemaker;
    private List<Colors> last_guess;
    private List<Colors> last_response;

    /**
     * Constructora de la classe Ronda
     * 
     * @throws InvalidCodiColors
     * @throws InvalidCodiLength
     * @throws codiAlreadyGenerated
     * 
     * @param dificultat:  dificultat de la ronda.
     * @param codebreaker: codebreaker de la ronda.
     * @param codemaker:   codemaker de la ronda.
     **/
    public Ronda(Dificultat dificultat, Codebreaker codebreaker, Codemaker codemaker)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        this.torn_actual = 0;
        this.codi = new Codi(dificultat);
        this.codemaker = codemaker;
        this.codebreaker = codebreaker;
        this.codi.setCodi(codemaker.getCode());
        this.dificultat = dificultat;
        this.punts_codemaker = 0;
        last_guess = new ArrayList<Colors>();
        last_response = new ArrayList<Colors>();
        this.ajuda = new Codebreaker(false, this.codi);
    }

    /**
     * Mètode que retorna la dificultat de la ronda.
     * 
     * @return dificultat de la ronda.
     */
    public Dificultat getDificultat() {
        return this.dificultat;
    }

    /**
     * Mètode que estableix el codemaker de la ronda
     * 
     * @param codemaker Codemaker de la ronda
     */
    public void setCodemaker(Codemaker codemaker) {
        this.codemaker = codemaker;
    }

    /**
     * Mètode que estableix el codebreaker de la ronda
     * 
     * @param codebreaker Codebreaker de la ronda
     */
    public void setCodebreaker(Codebreaker codebreaker) {
        this.codebreaker = codebreaker;
        this.ajuda = new Codebreaker(false, this.codi);
    }

    /**
     * Mètode que retorna si la ronda esta acabada o no.
     * 
     * @param codi Resposta del codemaker
     * @return true si la ronda esta acabada, false altrament.
     */
    private Boolean checkAcabada(List<Colors> codi) {
        Integer sum_blanques = 0;
        for (Colors c : codi) {
            if (c == Colors.BLANC)
                sum_blanques++;
        }
        if (sum_blanques == this.codi.getPosicions()) {
            this.guanyada = true;
        }
        return (this.torn_actual == this.codi.getTorns()) || this.guanyada;
    }

    /**
     * Mètode que retorna si la ronda ha estat guanyada o no.
     * 
     * @return true si la ronda ha estat guanyada, false altrament.
     */
    public Boolean rondaGuanyada() {
        return this.guanyada;
    }

    /**
     * Mètode que simula avançar una tirada en la ronda.
     * 
     * @param codi Resposta del codemaker
     */
    private void jugarTorn(List<Colors> codi) {
        this.torn_actual++;
        if (checkAcabada(codi))
            this.acabada = true;
        else
            this.punts_codemaker++;
    }

    /**
     * Mètode que retorna una possible solució al codi de la ronda
     * 
     * @return possible solució al codi de la ronda
     * @throws Exception
     */
    public List<Colors> getGuess() throws Exception {
        if (this.torn_actual == 0) {
            List<Colors> guess = new ArrayList<Colors>();
            return this.codebreaker.jugarTorn(guess);
        } else
            return this.codebreaker.jugarTorn(this.codi_resposta);
    }

    /**
     * Mètode que retorna si la resposta del humà que juga com a codemaker
     * és correcte o no.
     * 
     * @param negres   Número de posicions negres de la resposta
     * @param blanques Número de posicions blanques de la resposta
     * @param guess    Codi que ha introduit el codebreaker
     * @return true si la resposta del codemaker és correcte, false altrament
     * @throws InvalidCodiColors Si el codi introduït conté colors no vàlids
     * @throws InvalidCodiLength Si el codi introduït no té la longitud correcte
     */
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
        Boolean correcte = (sum_negres == negres && sum_blanques == blanques);
        if (correcte) {
            jugarTorn(resposta);
        }
        return correcte;
    }

    /**
     * Mètode que retorna si la ronda està acabada o no.
     * 
     * @return true si la ronda està acabada, false altrament.
     */
    public Boolean rondaAcabada() {
        return this.acabada;
    }

    /**
     * Mètode que retorna la resposta del codemaker al codebreaker
     * 
     * @param codi Codi que ha introduit el codebreaker
     * @return resposta del codemaker al codebreaker
     * @throws InvalidCodiColors Si el codi introduït conté colors no vàlids
     * @throws InvalidCodiLength Si el codi introduït no té la longitud correcte
     */
    public List<Colors> comprovarCodi(List<Colors> codi) throws InvalidCodiColors, InvalidCodiLength {
        List<Colors> codi_resposta = this.codi.checkCodi(codi);
        this.last_guess = codi;
        this.last_response = codi_resposta;
        this.ajuda.afegirJugada(codi);
        jugarTorn(codi_resposta);
        return codi_resposta;
    }

    /**
     * Retona una possible jugada per ajudar al codebreaker
     * 
     * @return possible jugada
     * @throws Exception
     */
    public List<Colors> getAjuda() throws Exception {
        return this.ajuda.getAjuda();
    }

    /**
     * Mètode que retorna la última resposta del codebreaker al codemaker
     * 
     * @return última resposta del codebreaker al codemaker
     */
    public List<Colors> getLastGuess() {
        return this.last_guess;
    }

    /**
     * Mètode que retorna la última resposta del codemaker al codebreaker
     * 
     * @return última resposta del codemaker al codebreaker
     */
    public List<Colors> getLastResponse() {
        return this.last_response;
    }

    /**
     * Mètode que retorna el nombre de posicions del codi
     * 
     * @return nombre de posicions del codi
     */
    public Integer posicions() {
        return this.codi.getPosicions();
    }

    /**
     * Mètode que retorna els colors disponibles per a la ronda
     * 
     * @return colors disponibles per a la ronda
     */
    public List<Colors> colorsAvailable() {
        return this.codi.getAvailColors();
    }

    /**
     * Mètode que retorna el codi de la ronda
     * 
     * @return codi de la ronda
     */
    public List<Colors> getCodi() {
        return this.codi.getCodi();
    }

    /**
     * Mètode que retorna el torn actual de la ronda
     * 
     * @return torn actual de la ronda
     */
    public Integer getTornActual() {
        return this.torn_actual;
    }

    /**
     * Mètode que retorna si el codemaker és humà o no
     * 
     * @return true si el codemaker és humà, false altrament
     */
    public Boolean rol() {
        return this.codemaker.rol();
    }

    /**
     * Mètode que retorna el objecte de la classe Codi
     * 
     * @return objecte de la classe Codi
     */
    public Codi getCodiObject() {
        return this.codi;
    }

    /**
     * Mètode que retorna el nombre de punts del codemaker
     * 
     * @return nombre de punts del codemaker
     */
    public Integer getPunts() {
        return this.punts_codemaker;
    }

    /**
     * Mètode que retorna el nombre de torns de la ronda
     * 
     * @return nombre de torns de la ronda
     */
    public Integer getTorns() {
        return this.codi.getTorns();
    }
}