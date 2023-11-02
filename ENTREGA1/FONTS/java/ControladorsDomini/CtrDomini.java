package ControladorsDomini;

import java.util.ArrayList;
import java.util.List;

import domain.Codebreaker;
import domain.Codemaker;
import domain.Codi;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import domain.Estadistiques;
import domain.GestorPartides;
import domain.Machine;
import domain.Partida;
import domain.Usuari;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.LimitPartidesGuardades;
import exceptions.PartidaNoTrobada;
import exceptions.codiAlreadyGenerated;

public class CtrDomini {

    private Boolean codemaker;
    private Boolean codebreaker;
    private Partida partida;
    private Dificultat dificultat;
    private Integer torns;
    private Codemaker maker;
    private Codebreaker breaker;
    private Codi codi;
    private GestorPartides gestor;
    private Estadistiques estadistiques;
    private Integer estrategia;

    public CtrDomini() {
        inicialitzar();
    }

    public void inicialitzar() {
        this.codemaker = false;
        this.codebreaker = true;
        this.partida = null;
        this.gestor = new GestorPartides();
        this.estadistiques = new Estadistiques();
    }

    public void setRol(Boolean codemaker, Boolean codebreaker) {
        this.codemaker = codemaker;
        this.codebreaker = codebreaker;
        if (this.codemaker) {
            this.dificultat = Dificultat.FACIL;
        }
    }

    public Boolean getRolCodemaker() {
        return this.codemaker;
    }

    public void setEstrategia(Integer estrategia) {
        this.estrategia = estrategia;
    }

    public void setPartida(Integer id, List<Colors> codi)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        Codi code = new Codi(this.dificultat);
        if (codi == null) {
            List<Colors> colors = new ArrayList<>();
            for (int i = 0; i < code.getPosicions(); i++) {
                colors.add(Colors.BLAU);
            }
            codi = colors;
        }
        if (this.codemaker) {
            code.setCodi(codi);
            this.maker = new Usuari(code);
            this.breaker = new Codebreaker(true, code);
        } else {
            this.maker = new Machine(code);
            this.breaker = new Codebreaker(true, code);
        }
        this.partida = new Partida(id, this.torns, this.dificultat, this.breaker, this.maker);
    }

    public void setDificultat(Dificultat dificultat) {
        this.dificultat = dificultat;
        this.codi = new Codi(dificultat);
    }

    public void setTorns(Integer torns) throws exceptions.NombreTornsInvalid {
        if (torns < 6 || torns > 12)
            throw new exceptions.NombreTornsInvalid(torns);
        this.torns = torns;
    }

    public void inicialitzarPartida(Integer id, List<Colors> codi)
            throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        setPartida(id, codi);
    }

    public void iniciarPartidaRapida(Integer id) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        setPartida(id, null);
    }

    public List<Colors> getAvailableColors() {
        return this.codi.getAvailColors();
    }

    public Integer posicions() {
        return this.codi.getPosicions();
    }

    public List<Colors> getGuessCodi() throws Exception {
        return this.partida.getGuess();
    }

    public Boolean checkCorreccio(Integer negres, Integer blanques, List<Colors> codi)
            throws InvalidCodiColors, InvalidCodiLength {
        return this.partida.checkCorreccio(negres, blanques, codi);
    }

    public Boolean partidaAcabada() {
        return this.partida.partidaAcabada();
    }

    public List<Colors> comprovarCodi(List<Colors> codi) throws InvalidCodiColors, InvalidCodiLength {
        return this.partida.comprovarCodi(codi);
    }

    public Dificultat getDificultat() {
        return this.partida.getDificultat();
    }

    public List<Colors> getCodi() {
        return this.partida.getCodi();
    }

    public void eliminarPartida(Partida partida) throws PartidaNoTrobada {
        this.gestor.eliminarPartida(partida);
    }

    public List<Partida> getPartides() {
        return this.gestor.getPartides();
    }

    public void carregarPartida(Partida partida) throws PartidaNoTrobada {
        this.partida = this.gestor.carregarPartida(partida);
        if (this.partida.rol()) {
            this.codemaker = true;
            this.codebreaker = false;
        } else {
            this.codemaker = false;
            this.codebreaker = true;
        }
        this.codi = this.partida.getCodiObject();
        this.torns = this.partida.getTorns();
        this.dificultat = this.partida.getDificultat();
    }

    public void eliminarPartida() throws PartidaNoTrobada {
        this.gestor.eliminarPartida(this.partida);
    }

    public Integer getTorns() {
        return this.partida.getTorns();
    }

    public Integer getTornActual() {
        return this.partida.getTornActual();
    }

    public void guardarPartida() throws LimitPartidesGuardades {
        this.gestor.guardarPartida(this.partida);
    }

    public Boolean partidaGuanyada() {
        return this.partida.partidaGuanyada();
    }

    public void afegirPartidaARanking(String nom) {
        this.partida.setNom(nom);
        this.estadistiques.afegirPartida(this.partida);
    }

    public List<Partida> getRanking() {
        return this.estadistiques.getRanking();
    }

    public void reiniciarRanking() {
        this.estadistiques.reiniciarRanking();
    }
}
