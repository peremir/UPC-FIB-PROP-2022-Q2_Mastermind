package ControladorsDomini;

import java.util.List;

import ControladorPersistencia.CtrlRanking;
import ControladorPersistencia.ctrPartidesGuardades;
import domain.Codi;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import domain.Partida;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.LimitPartidesGuardades;
import exceptions.PartidaNoTrobada;
import exceptions.codiAlreadyGenerated;

/**
 * Classe que representa el controlador de domini
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @version 31.5.2023
 * @author Gerard Oliva Viñas
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/ControladorsDomini/CtrDomini.java">CtrDomini.java</a>
 */
public class CtrDomini {

    /**
     * Atributs de la classe
     */
    private Boolean codemaker;
    private Boolean codebreaker;
    private Partida partida;
    private Dificultat dificultat;
    private Codi codi;
    private ctrPartidesGuardades gestor;
    private CtrlRanking estadistiques;
    private Boolean estrategia;
    private Integer rondes;

    /**
     * Constructora de la classe
     */
    public CtrDomini() {
        inicialitzar();
    }

    /**
     * Mètode que inicialitza els atributs de la classe
     */
    public void inicialitzar() {
        this.codemaker = false;
        this.codebreaker = true;
        this.partida = null;
        this.gestor = new ctrPartidesGuardades();
        this.estadistiques = new CtrlRanking();
    }

    /**
     * Mètode que estableix els rols dels jugadors
     * 
     * @param codemaker   true si l'humà juga com a codemaker, false altrament
     * @param codebreaker true si l'humà juga com a codebreaker, false altrament
     */
    public void setRol(Boolean codemaker, Boolean codebreaker) {
        this.codemaker = codemaker;
        this.codebreaker = codebreaker;
    }

    /**
     * Retorna una possible resposta al codi
     * 
     * @return String[] amb una possible resposta al codi
     * @throws Exception
     */
    public String[] getAjuda() throws Exception {
        return this.codi.colorsToString(this.partida.getAjuda());
    }

    /**
     * Mètode que retorna si l'humà juga com a codemaker
     * 
     * @return true si l'humà juga com a codemaker, false altrament
     */
    public Boolean getRolCodemaker() {
        return this.partida.rol();
    }

    /**
     * Mètode que estableix la dificultat l'estrategia a utilitzar pel codebreaker
     * 
     * @param estrategia 1 si l'estrategia és la de l'algorisme Genetic,
     *                   0 si és FiveGuess
     */
    public void setEstrategia(Integer estrategia) {
        if (estrategia == 0)
            this.estrategia = true;
        else
            this.estrategia = false;
    }

    /**
     * Mètode que instancia una partida amb els paràmetres establerts, i n'estableix
     * la dificultat de la mateixa
     * 
     * @throws codiAlreadyGenerated si s'intenta generar un codi quan ja n'hi ha un
     * @throws InvalidCodiLength    si la longitud del codi és incorrecta
     * @throws InvalidCodiColors    si els colors del codi són incorrectes
     */
    public void setPartida()
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        this.partida = new Partida(this.rondes, this.dificultat, this.codebreaker, this.codemaker);
        this.partida.setEstrategia(this.estrategia);
    }

    /**
     * Mètode que crea una ronda nova amb el codi que s'ha passat per paràmetre
     * 
     * @param codi codi que s'utilitzarà per a la ronda
     * @throws codiAlreadyGenerated si s'intenta generar un codi quan ja n'hi ha un
     * @throws InvalidCodiLength    si la longitud del codi és incorrecta
     * @throws InvalidCodiColors    si els colors del codi són incorrectes
     */
    public void setCodi(String[] codi) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        List<Colors> code = this.codi.stringToColors(codi);
        this.partida.setupRonda(code);
    }

    /**
     * Mètode que retorna si el codemaker està instanciat
     * 
     * @return true si el codemaker està instanciat, false altrament
     */
    public Boolean codeSet() {
        return this.partida.codeSet();
    }

    /**
     * Mètode que estableix la dificultat de la partida
     * 
     * @param dificultat dificultat de la partida
     */
    public void setDificultat(Dificultat dificultat) {
        this.dificultat = dificultat;
        this.codi = new Codi(dificultat);
    }

    /**
     * Mètode que estableix el nombre de rondes de la partida
     * 
     * @param rondes nombre de rondes de la partida
     * @throws exceptions.NombreTornsInvalid si el nombre de rondes és incorrecte
     */
    public void setRondes(Integer rondes) throws exceptions.NombreTornsInvalid {
        if (rondes % 2 != 0 && rondes > 1)
            throw new exceptions.NombreTornsInvalid(rondes);
        this.rondes = rondes;
    }

    /**
     * Mètode que retorna una llista amb el colors disponibles per a la partida
     * 
     * @return llista amb el colors disponibles per a la partida
     */
    public List<Colors> getAvailableColors() {
        return this.codi.getAvailColors();
    }

    /**
     * Mètode que retorna el nombre de colors disponibles per a la partida
     * 
     * @return nombre de colors disponibles per a la partida
     */
    public Integer getNumberColors() {
        return this.codi.getAvailColors().size();
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
     * Mètode que retorna una solució per al codi de la partida
     * 
     * @return String amb la solució per al codi de la partida
     * @throws Exception
     */
    public String[] getGuessCodi() throws Exception {
        return this.codi.colorsToString(this.partida.getGuess());
    }

    /**
     * Mètode que comprova si la correcció del codemaker humà és correcta
     * 
     * @param negres   Nombre de colors correctes en posició incorrecta
     * @param blanques Nombre de colors correctes en posició correcta
     * @param codi     Codi que codebraquer ha proposat
     * @return true si la correcció és correcta, false altrament
     * @throws InvalidCodiColors si els colors del codi són incorrectes
     * @throws InvalidCodiLength si la longitud del codi és incorrecta
     */
    public Boolean checkCorreccio(Integer negres, Integer blanques, String[] codi)
            throws InvalidCodiColors, InvalidCodiLength {
        return this.partida.checkCorreccio(negres, blanques, this.codi.stringToColors(codi));
    }

    /**
     * Mètode que retorna si la partida ha acabat
     * 
     * @return true si la partida ha acabat, false altrament
     */
    public Boolean partidaAcabada() {
        return this.partida.partidaAcabada();
    }

    /**
     * Mètode que retorna la solució del codemaker a la proposta del codebreaker
     * 
     * @return Solució del codemaker a la proposta del codebreaker
     * 
     * @param codi Codi que codebreaker ha proposat
     * 
     * @throws InvalidCodiColors si els colors del codi són incorrectes
     * @throws InvalidCodiLength si la longitud del codi és incorrecta
     */
    public List<Colors> comprovarCodi(List<Colors> codi) throws InvalidCodiColors, InvalidCodiLength {
        return this.partida.comprovarCodi(codi);
    }

    /**
     * Mètode que retorna la dificultat de la partida
     * 
     * @return dificultat de la partida
     */
    public Dificultat getDificultat() {
        return this.partida.getDificultat();
    }

    /**
     * Mètode que retorna el codi de la partida
     * 
     * @return String amb el codi de la partida
     */
    public String[] getCodi() {
        return this.codi.colorsToString(this.partida.getCodi());
    }

    /**
     * Mètode que elimina una partida del sistema gestor de partides
     * 
     * @param partida partida a eliminar
     * @throws PartidaNoTrobada si la partida no existeix
     */
    public void eliminarPartida(Partida partida) throws PartidaNoTrobada {
        this.gestor.eliminarPartida(partida);
    }

    /**
     * Mètode que elimina una partida del sistema gestor de partides
     * 
     * @param id identificador de la partida a eliminar
     * @throws PartidaNoTrobada si la partida no existeix
     */
    public void eliminarPartidaPresentacio(Integer id) throws PartidaNoTrobada {
        eliminarPartida(gestor.getPartida(id));
    }

    /**
     * Mètode que carrega una partida del sistema gestor de partides
     * 
     * @param id identificador de la partida a carregar
     * @throws PartidaNoTrobada     si la partida no existeix
     * @throws codiAlreadyGenerated si el codi ja ha estat generat
     * @throws InvalidCodiLength    si la longitud del codi és incorrecta
     * @throws InvalidCodiColors    si els colors del codi són incorrectes
     */
    public void carregarPartida(Integer id)
            throws PartidaNoTrobada, codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        this.partida = this.gestor.getPartida(id);
        if (this.partida.rol()) {
            this.codemaker = true;
            this.codebreaker = false;
        } else {
            this.codemaker = false;
            this.codebreaker = true;
        }
        this.codi = this.partida.getCodiObject();
        this.dificultat = this.partida.getDificultat();
        this.partida.carregarPartida();
    }

    /**
     * Mètode que elimina la partida actual del sistema gestor de partides
     * 
     * @throws PartidaNoTrobada si la partida no existeix
     */
    public void eliminarPartida() throws PartidaNoTrobada {
        this.gestor.eliminarPartida(this.partida);
    }

    /**
     * Mètode que retorna el torn actual de la ronda actual de la partida
     * 
     * @return torn actual de la ronda actual de la partida
     */
    public Integer getTornActual() {
        return this.partida.getTornActual();
    }

    /**
     * Mètode que retorna el nombre de torns de la partida
     * 
     * @return nombre de torns de la partida
     */
    public Integer getTorns() {
        return this.partida.getTorns();
    }

    /**
     * Mètode que guarda la partida actual al sistema gestor de partides
     * 
     * @throws LimitPartidesGuardades si s'ha arribat al límit de 20 partides
     *                                guardades
     */
    public void guardarPartida() throws LimitPartidesGuardades {
        this.gestor.guardarPartida(this.partida);
    }

    /**
     * Mètode que retorna si s'ha guanyat la partida
     * 
     * @return true si s'ha guanyat la partida, false altrament
     */
    public Boolean partidaGuanyada() {
        return this.partida.partidaGuanyada();
    }

    /**
     * Mètode que afegeix la partida actual al rànking
     * 
     * @param nom Nom que li volem donar a la partida a guardar
     */
    public void afegirPartidaARanking(String nom) {
        this.partida.setNom(nom);
        this.estadistiques.afegirPartida(this.partida);
        try {
            eliminarPartida(this.partida);
        } catch (PartidaNoTrobada e) {
            // TODO Auto-generated catch block
        }
    }

    /**
     * Mètode que reinicia el rànking
     */
    public void reiniciarRanking() {
        this.estadistiques.reiniciarRanking();
    }

    /**
     * Mètode que retorna si s'ha acabat la ronda que s'està jugant
     * 
     * @return true si s'ha acabat la ronda, false altrament
     */
    public Boolean rondaAcabada() {
        return this.partida.getRondaAcabada();
    }

    /**
     * Mètode que canvia els rols de la partida i suma els punts del codemaker de la
     * ronda al total de la partida
     */
    public void nextTurn() {
        this.partida.nextTurn();
    }

    /**
     * Mètode que incialitza una nova ronda com a codebreaker
     * 
     * @throws codiAlreadyGenerated si el codi ja ha estat generat
     * @throws InvalidCodiLength    si la longitud del codi és incorrecta
     * @throws InvalidCodiColors    si els colors del codi són incorrectes
     */
    public void setupRondaCodebreaker() throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        this.partida.setupRonda(List.of(Colors.BLANC, Colors.BLANC, Colors.BLANC, Colors.BLANC));
    }

    /**
     * Mètode que retorna la ronda actual de la partida
     * 
     * @return ronda actual de la partida
     */
    public Integer getRondaActual() {
        return this.partida.getRondaActual();
    }

    /**
     * Mètode que retorna la última resposta de codemaker
     * 
     * @return última resposta de codemaker
     */
    public String[] getResposta() {
        return this.codi.colorsToString(this.partida.getLastResponse());
    }

    /**
     * Mètode que retorna la última solució de codebreaker
     * 
     * @return última solució de codebreaker
     */
    public String[] getUltimCodi() {
        return this.codi.colorsToString(this.partida.getLastGuess());
    }

    /**
     * Mètode que comprova si el codi introduït és correcte
     * 
     * @param code codi a comprovar
     * @throws InvalidCodiColors si els colors del codi són incorrectes
     * @throws InvalidCodiLength si la longitud del codi és incorrecta
     */
    public void tryCode(String[] code) throws InvalidCodiColors, InvalidCodiLength {
        this.partida.comprovarCodi(this.codi.stringToColors(code));
    }

    /**
     * Mètode que retorna el nombre de rondes de la partida
     * 
     * @return nombre de rondes de la partida
     */
    public Integer getRondes() {
        return this.partida.getRondes();
    }

    /**
     * Mètode que retorna la ronda actual de la partida
     * 
     * @return ronda actual de la partida
     */
    public Integer getRonda() {
        return this.partida.getRondaActual();
    }

    /**
     * Mètode que retorna una matriu de Strings amb les partides guardades
     * 
     * @return matriu de Strings amb les partides guardades
     */
    public Object[][] getPartidesGuardades() {
        return this.gestor.getPartidesPresentacio();
    }

    /**
     * Mètode que retorna una matriu de Strings amb el rànking
     * 
     * @return matriu de Strings amb el rànking
     */
    public Object[][] getRankingPresentacio() {
        return this.estadistiques.getRanking();
    }

    /**
     * Mètode que retorna la puntuació de l'humà de la partida
     * 
     * @return puntuació de l'humà de la partida
     */
    public Integer getPuntuacioHuma() {
        return this.partida.getPuntuacioHuma();
    }

    /**
     * Mètode que retorna la puntuació de la màquina de la partida
     * 
     * @return puntuació de la màquina de la partida
     */
    public Integer getPuntuacioMaquina() {
        return this.partida.getPuntuacioMaquina();
    }
}