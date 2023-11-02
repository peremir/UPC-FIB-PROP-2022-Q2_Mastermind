package domain;

import java.util.*;
import domain.Codi.Colors;

public class NodeArbreNAri {

    private Colors info;
    private List<NodeArbreNAri> childs = new ArrayList<NodeArbreNAri>();
    private List<Colors> comb;

    private boolean fulla;
    private boolean s;
    private int points;
    
    public NodeArbreNAri(Colors c) {
        info = c;
        fulla = false;
        s = false;
    }

    public NodeArbreNAri() {
        info = null;
        fulla = false;
        s = false;
    }

    public Colors getInfo() {
        return info;
    }

    // ERROR: no existeix fill i
    public NodeArbreNAri getChild(int i) {
        return childs.get(i);
    }

    public List<NodeArbreNAri> getChilds() {
        return childs;
    }

    public void setChild(NodeArbreNAri info) {
        childs.add(info);
    }

    public void setComb(List<Colors> c) {
        comb = new ArrayList<>(c);
    }

    public List<Colors> getComb() {
        return comb;
    }

    public void setLeaf() {
        fulla = true;
    }

    public boolean esFulla() {
        return fulla;
    }

    public int getNumChilds() {
        return childs.size();
    }

    public void removeChild(int i) {
        childs.remove(i);
    }

    public void setPuntuacio(int p) {
        points = p;
    }

    public int getPoints() {
        return points;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public boolean getS() {
        return s;
    }
}
