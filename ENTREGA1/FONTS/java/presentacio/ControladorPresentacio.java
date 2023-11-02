package presentacio;

import java.util.List;

import ControladorsDomini.CtrDomini;
import domain.Codi.Colors;
import domain.Codi.Dificultat;
import domain.Partida;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.LimitPartidesGuardades;
import exceptions.NombreTornsInvalid;
import exceptions.PartidaNoTrobada;
import exceptions.codiAlreadyGenerated;

public class ControladorPresentacio {
    private VistaTerminal vt;
    private CtrDomini cd;

    public ControladorPresentacio() {
        vt = new VistaTerminal(this);
        cd = new CtrDomini();
        vt.menuPrincipal();
    }

    public void crearPartida(Integer estrategia) {
        cd.setEstrategia(estrategia);
    }

    public void setEstrategia(Integer estrategia) {
        cd.setEstrategia(estrategia);
        vt.escollirCodi(cd.getAvailableColors(), cd.posicions());
    }

    public void setDificultat(Dificultat dificultat) {
        cd.setDificultat(dificultat);
    }

    public void setTorns(Integer torns) throws NombreTornsInvalid {
        cd.setTorns(torns);
    }

    public void setRol(Boolean codemaker, Boolean codebreaker) {
        cd.setRol(codemaker, codebreaker);
    }

    public void jugar() {
        if (!cd.partidaAcabada()) {
            if (cd.getRolCodemaker()) {
                vt.JugarCodemaker(cd.getTornActual(), cd.getTorns());
            } else {
                vt.JugarCodebreaker(cd.posicions(), cd.getAvailableColors(), cd.getTorns(), cd.getTornActual());
            }
        } else {
            try {
                cd.eliminarPartida();
            } catch (PartidaNoTrobada e) {
                // TODO Auto-generated catch block
            }
            if (cd.partidaGuanyada() && !cd.getRolCodemaker())
                vt.afegirPartidaARanking();
            else
                vt.partidaAcabada(cd.getCodi(), cd.getRolCodemaker());
        }
    }

    public void afegirPartidaARanking(String nom) {
        cd.afegirPartidaARanking(nom);
        vt.menuPrincipal();
    }

    public void setCodi(List<Colors> codi) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        cd.setPartida(0, codi);
        jugar();
    }

    public void escollirCodi() {
        vt.escollirCodi(cd.getAvailableColors(), cd.posicions());
    }

    public void guessCodi() throws Exception {
        vt.solucioCodemaker(cd.getGuessCodi(), cd.posicions());
    }

    public Boolean checkCorreccio(Integer negres, Integer blanques, List<Colors> codi)
            throws InvalidCodiColors, InvalidCodiLength {
        return cd.checkCorreccio(negres, blanques, codi);
    }

    public void iniciarPartidaCodebreaker() throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {
        cd.setPartida(0, null);
        jugar();
    }

    public void comprovarCodi(List<Colors> codi) throws InvalidCodiColors, InvalidCodiLength {
        List<Colors> solucio = cd.comprovarCodi(codi);
        vt.solucioCodebreaker(solucio);
    }

    public void eliminarPartida(Partida p) throws PartidaNoTrobada {
        cd.eliminarPartida(p);
        vt.menuPrincipal();
    }

    public List<Partida> getPartides() {
        return cd.getPartides();
    }

    public void jugarPartida(Partida p) throws PartidaNoTrobada {
        cd.carregarPartida(p);
        if (cd.getRolCodemaker())
            vt.showCodi(cd.getCodi());
        jugar();
    }

    public void guardarPartida() throws LimitPartidesGuardades {
        cd.guardarPartida();
        vt.menuPrincipal();
    }

    public List<Partida> getRanking() {
        return cd.getRanking();
    }

    public void reiniciarRanking() {
        cd.reiniciarRanking();
    }
}
