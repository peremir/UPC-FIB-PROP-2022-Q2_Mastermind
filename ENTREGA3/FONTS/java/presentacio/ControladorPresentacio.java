package presentacio;

import javax.swing.JFrame;

import ControladorsDomini.CtrDomini;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.LimitPartidesGuardades;
import exceptions.NombreTornsInvalid;
import exceptions.PartidaNoTrobada;
import exceptions.codiAlreadyGenerated;
import presentacio.vistes.vistaCodebreaker;
import presentacio.vistes.vistaCodemaker;
import presentacio.vistes.vistaJugar;
import presentacio.vistes.vistaMenuPausa;
import presentacio.vistes.vistaNormes;
import presentacio.vistes.vistaPartidaAcabada;
import presentacio.vistes.vistaPartidesGuardades;
import presentacio.vistes.vistaPrincipal;
import presentacio.vistes.vistaRanking;
import presentacio.vistes.vistaSelectCodi;

/**
 * Controlador de presentació
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @version 31.5.2023
 * @author Gerard Oliva Viñas
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/presentacio/ControladorPresentacio.java">ControladorPresentacio.java</a>
 */
public class ControladorPresentacio {
    /**
     * Atributs
     */
    private JFrame vp;
    private CtrDomini cd;
    Boolean inicialitzacio = false;

    /**
     * Constructora de la classe
     */
    public ControladorPresentacio() {
        menuPrincipal();
        cd = new CtrDomini();
    }

    /**
     * Mètode per inicialitzar el controlador de presentació
     */
    public void menuPrincipal() {
        this.vp = new vistaPrincipal(this);
    }

    /**
     * Mètode per inicialitzar la vista obrirRanking
     */
    public void obrirRanking() {
        vp.setVisible(false);
        this.vp = new vistaRanking(this);
    }

    /**
     * Retorna una String[] amb una possible solució al codi
     * 
     * @return String[] amb una possible solució al codi
     * @throws Exception
     */
    public String[] getAjuda() throws Exception {
        return cd.getAjuda();
    }

    /**
     * Mètode per inicialitzar la vista Jugar
     */
    public void obrirJugar() {
        vp.setVisible(false);
        this.vp = new vistaJugar(this);
    }

    /**
     * Mètode per inicilitzar la vista PartidesGuardades
     */
    public void obrirPartidesGuardades() {
        vp.setVisible(false);
        this.vp = new vistaPartidesGuardades(this);
    }

    /**
     * Mètode per establir la dificultat de la partida
     * 
     * @param dificultat String amb dificultat de la partida
     */
    public void setDificultat(String dificultat) {
        if (dificultat.equals("Mitjà"))
            cd.setDificultat(Dificultat.MITJA);
        else if (dificultat.equals("Difícil"))
            cd.setDificultat(Dificultat.DIFICIL);
        else
            cd.setDificultat(Dificultat.FACIL);
    }

    /**
     * Mètode per establir el rol inicial del jugador humà
     * 
     * @param Rol String amb rol del inicial jugador humà
     */
    public void setRol(String Rol) {
        if (Rol.equals("Codemaker"))
            cd.setRol(true, false);
        else
            cd.setRol(false, true);
    }

    /**
     * Mètode per establir la estrategia del jugador
     * 
     * @param Estrategia String amb l'estrategia del codebreaker
     */
    public void setEstrategia(String Estrategia) {
        if (Estrategia.equals("Five Guess"))
            cd.setEstrategia(0);
        else
            cd.setEstrategia(1);
    }

    /**
     * Mètode per establir el nombre de rondes de la partida
     * 
     * @param Rondes String amb el nombre de rondes de la partida
     * @throws NumberFormatException Si el nombre de rondes no és un número
     * @throws NombreTornsInvalid    Si el nombre de rondes no és vàlid
     */
    public void setRondes(String Rondes) throws NumberFormatException, NombreTornsInvalid {
        cd.setRondes(Integer.parseInt(Rondes));
    }

    /**
     * Mètode que incilitza la partida amb els valors establerts
     * 
     * @throws codiAlreadyGenerated Si el codi ja ha estat generat
     * @throws InvalidCodiLength    Si la longitud del codi no és vàlida
     * @throws InvalidCodiColors    Si els colors del codi no són vàlids
     */
    public void setPartida() throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        cd.setPartida();
    }

    /**
     * Mètode per inicialitzar la vista menuPrincipal
     */
    public void MenuPrincipal() {
        vp.setVisible(false);
        this.vp = new vistaPrincipal(this);
    }

    /**
     * Mètode que simula jugar un torn de la partida.
     * 
     * Depenent de l'estat de la partida, es crida a un mètode o un altre.
     * 
     * @throws Exception
     */
    public void Jugar() throws Exception {
        vp.setVisible(false);
        if (cd.getRolCodemaker()) {
            if (!cd.codeSet())
                this.vp = new vistaSelectCodi(this);
            else
                this.vp = new vistaCodemaker(this, cd.getGuessCodi(), cd.getCodi());
        } else {
            if (!cd.codeSet()) {
                cd.setupRondaCodebreaker();
                String[] resposta = new String[0];
                String[] codi = new String[0];
                this.vp = new vistaCodebreaker(this, resposta, codi);
            } else {
                String[] resposta = cd.getResposta();
                String[] codi = cd.getUltimCodi();
                this.vp = new vistaCodebreaker(this, resposta, codi);
            }
        }
    }

    /**
     * Mètode que retorna els colors disponibles per jugar
     * 
     * @return String[] amb els colors disponibles per jugar
     */
    public String[] getAvailableColors() {
        String[] colors = new String[cd.getAvailableColors().size()];
        for (int i = 0; i < cd.getAvailableColors().size(); i++) {
            colors[i] = cd.getAvailableColors().get(i).toString();
        }
        return colors;
    }

    /**
     * Mètode que retorna el nombre de posicions del codis
     * 
     * @return nombre de posicions del codi
     */
    public Integer getNumberColors() {
        return cd.posicions();
    }

    /**
     * Mètode per establir el codi amb el que es jugarà la ronda
     * 
     * @param code String[] amb el codi amb el que es jugarà la ronda
     * @throws codiAlreadyGenerated Si el codi ja ha estat generat
     * @throws InvalidCodiLength    Si la longitud del codi no és vàlida
     * @throws InvalidCodiColors    Si els colors del codi no són vàlids
     */
    public void setCodi(String[] code) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        cd.setCodi(code);
    }

    /**
     * Mètode per inicilitzar la vista Codemaker
     * 
     * @throws Exception
     */
    public void jugarCodemaker() throws Exception {
        vp.setVisible(false);
        this.vp = new vistaCodemaker(this, cd.getGuessCodi(), cd.getCodi());
    }

    /**
     * Mètode que comprova que la correcció del codi sigui correcte
     * 
     * @param answer String[] amb la correcció del codi
     * @param guess  String[] amb el codi a comprovar
     * @return true si la correcció és correcte, false altrament
     * @throws InvalidCodiColors Si els colors del codi no són vàlids
     * @throws InvalidCodiLength Si la longitud del codi no és vàlida
     */
    public Boolean setAnswer(String[] answer, String[] guess) throws InvalidCodiColors, InvalidCodiLength {
        Integer negres = 0;
        Integer blanques = 0;
        for (int i = 0; i < answer.length; i++) {
            if (answer[i].equals("Negre"))
                negres++;
            else if (answer[i].equals("Blanc"))
                blanques++;
        }
        return cd.checkCorreccio(negres, blanques, guess);
    }

    /**
     * Mètode que simula avançar al següent torn de la ronda
     * 
     * @throws Exception
     */
    public void nextTurn() throws Exception {
        if (cd.partidaAcabada()) {
            cd.nextTurn();
            vp.setVisible(false);
            this.vp = new vistaPartidaAcabada(this);
        } else {
            this.vp.setVisible(false);
            if (cd.rondaAcabada()) {
                if (!cd.getRolCodemaker()) {
                    cd.nextTurn();
                    this.vp = new vistaSelectCodi(this);
                } else {
                    cd.nextTurn();
                    cd.setupRondaCodebreaker();
                    String[] resposta = new String[0];
                    String[] codi = new String[0];
                    this.vp = new vistaCodebreaker(this, resposta, codi);
                }
            } else
                Jugar();
        }
    }

    /**
     * Mètode que comprova si el codi introduït és correcte i avança al següent torn
     * 
     * @param code String[] amb el codi a comprovar
     * @throws Exception
     */
    public void tryCode(String[] code) throws Exception {
        cd.tryCode(code);
        nextTurn();
    }

    /**
     * Mètode que retorna quina és la ronda actual
     * 
     * @return ronda actual
     */
    public Integer getRonda() {
        return cd.getRonda();
    }

    /**
     * Mètode que retorna el nombre de rondes a jugar a la partida
     * 
     * @return nombre de rondes a jugar a la partida
     */
    public Integer getRondes() {
        return cd.getRondes();
    }

    /**
     * Mètode que retorna el nombre de torns a jugar a la ronda
     * 
     * @return nombre de torns a jugar a la ronda
     */
    public Integer getTorns() {
        return cd.getTorns();
    }

    /**
     * Mètode que retorna el torn actual
     * 
     * @return torn actual
     */
    public Integer getTorn() {
        return cd.getTornActual();
    }

    /**
     * Mètode que retorna un matriu de Strings amb les partides guardades al sistema
     * 
     * @return matriu de Strings amb les partides guardades al sistema
     */
    public Object[][] getPartidesGuardades() {
        return cd.getPartidesGuardades();
    }

    /**
     * Mètode que elimina la partida identificada per id
     * 
     * @param id identificador de la partida a eliminar
     * @throws PartidaNoTrobada Si la partida no existeix
     */
    public void eliminarPartida(Integer id) throws PartidaNoTrobada {
        cd.eliminarPartidaPresentacio(id);
    }

    /**
     * Mètode que carrega la partida identificada per id
     * 
     * @param id identificador de la partida a carregar
     * @throws Exception Si la partida no existeix
     */
    public void carregarPartida(Integer id) throws Exception {
        cd.carregarPartida(id);
        Jugar();
    }

    /**
     * Mètode que retorna una amtriu de Strings amb el ranking de la partida
     * 
     * @return matriu de Strings amb el ranking de la partida
     */
    public Object[][] getRankingPresentacio() {
        return cd.getRankingPresentacio();
    }

    /**
     * Mètode que reinicia el ranking
     */
    public void reiniciarRanking() {
        cd.reiniciarRanking();
    }

    /**
     * Mètode que retorna la puntuació de la partida del jugador humà
     * 
     * @return puntuació del jugador humà
     */
    public Integer getPuntuacioHuma() {
        return cd.getPuntuacioHuma();
    }

    /**
     * Mètode que retorna la puntuació de la partida de la màquina
     * 
     * @return puntuació de la màquina
     */
    public Integer getPuntuacioMaquina() {
        return cd.getPuntuacioMaquina();
    }

    /**
     * Mètode que estableix el nom a la partida actual
     * 
     * @param nom nom a establir a la partida actual
     */
    public void afegirPartidaARanking(String nom) {
        cd.afegirPartidaARanking(nom);
    }

    /**
     * Mètode que inicialitza la vista MenuPausa
     */
    public void vistaMenuPausa() {
        vp.setVisible(false);
        this.vp = new vistaMenuPausa(this);
    }

    /**
     * Mètode que inicialitza la vista Normes
     */
    public void vistaNormes() {
        vp.setVisible(false);
        this.vp = new vistaNormes(this);
    }

    /**
     * Mètode que guarda la partida actual al gestor de partides
     * 
     * @throws LimitPartidesGuardades Si s'ha arribat al límit de partides guardades
     */
    public void guardarPartida() throws LimitPartidesGuardades {
        cd.guardarPartida();
    }
}
