package domain;

import java.util.ArrayList;
import java.util.List;

import domain.Codi.Colors;

/**
 * Guarda tota la informació dels nodes dels Arbres N Aris.
 * <p>
 * <strong>Author:</strong> Aina Gomez Pinyol
 * @author Aina Gomez Pinyol
 * @version 2.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/domain/NodeArbreNAri.java>NodeArbreNAri.java</a>
 */

public class NodeArbreNAri {

    private Colors info;
    private List<NodeArbreNAri> childs = new ArrayList<NodeArbreNAri>();
    private List<Colors> comb;

    private boolean fulla;
    private boolean s;
    private int points;

    /**
     * Constructora.
     * @param c Color del node.
     */
    public NodeArbreNAri(Colors c) {
        info = c;
        fulla = false;
        s = false;
    }

    /**
     * Constructora buida.
     */
    public NodeArbreNAri() {
        info = null;
        fulla = false;
        s = false;
    }

    /**
     * Consulta el color del node del p.i.
     * @return El color del p.i.
     */
    public Colors getInfo() {
        return info;
    }

    /**
     * Consulta un fill del p.i.
     * @param i Índex del fill que volem consultar.
     * @return El fill 'i' del p.i.
     */
    public NodeArbreNAri getChild(int i) {
        return childs.get(i);
    }

    /**
     * Consulta tots els fills del p.i.
     * @return Llistat amb tots els fills del p.i.
     */
    public List<NodeArbreNAri> getChilds() {
        return childs;
    }

    /**
     * Actualitza els fills del p.i.
     * @param info Fill que volem afegir al llistat de fills del p.i.
     */
    public void setChild(NodeArbreNAri info) {
        childs.add(info);
    }

    /**
     * Actualitza la combinació del node.
     * @param c La combinació de tots els colors que contenen els pares del últim 
     * node que estem consultat. És a dir, si el pare és BLAU, el segon fill és
     * VERD i, ens trobem a l'últim fill que és MAGENTA, la combinació serà [BLAU,
     * VERD, MAGENTA].
     */
    public void setComb(List<Colors> c) {
        comb = new ArrayList<>(c);
    }

    /**
     * Consulta la combinació del p.i.
     * @return Llistat de colors amb la combinació de tots els nodes anteriors al
     * p.i.
     */
    public List<Colors> getComb() {
        return comb;
    }

    /**
     * Activa l'atribut 'fulla'. Aquest ens indica que el node és troba a l'alçada 
     * màxima (per tant, és fulla).
     */
    public void setLeaf() {
        fulla = true;
    }

    /**
     * Consulta si el p.i és fulla.
     * @return CERT si el node és troba a l'alçada màxima, FALSE altrament.
     */
    public boolean esFulla() {
        return fulla;
    }

    /**
     * Consulta el nombre de fills del p.i.
     * @return Enter amb el nombre de fills.
     */
    public int getNumChilds() {
        return childs.size();
    }

    /**
     * Elimina un fill del p.i.
     * @param i Índex del fill a eliminar.
     */
    public void removeChild(int i) {
        childs.remove(i);
    }

    /**
     * Actualitza la puntuació al calcular el minmax.
     * <p>
     * Aquesta puntuació indica el nombre de combinacions que quedaran, en cas pitjor,
     * en el llistat de possibles combinacions a guanyar. De manera que, quant més
     * petita sigui la combinació millor, ja que ens quedarem amb menys possibilitats.
     * @param p Combinacions que quedaran al jugar amb la combinació del p.i.
     */
    public void setPuntuacio(int p) {
        points = p;
    }

    /**
     * Consulta la puntuació.
     * @return Enter amb la puntuació calculada amb minmax.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Actualitza l'atribut que indica que la combinació del p.i és un codi amb 
     * possibilitats d'encertar.
     * @param s Boolea, CERT si la combinació del p.i és troba dintre de les 
     *          combinacions possibles a guanyar, FALSE altrament.
     */
    public void setS(boolean s) {
        this.s = s;
    }

    /**
     * Consulta si la combinació es una possible guanyadora.
     * @return CERT si la combinació és possible guanyadora, FALSE altrament.
     */
    public boolean getS() {
        return s;
    }
}
