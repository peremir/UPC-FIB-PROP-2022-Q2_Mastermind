package domain;

import java.util.*;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ArbreNAri {
    
    private NodeArbreNAri arrel;
    private NodeArbreNAri minimax;

    private int nivells;

    private Hashtable<List<Colors>,Integer> indexPoints = new Hashtable<>();
    private ArrayList<Integer> points = new ArrayList<Integer>();

    public ArbreNAri() {
        arrel = new NodeArbreNAri();
        minimax = new NodeArbreNAri();
    }

    public ArbreNAri(Colors c, int h) {

        arrel = new NodeArbreNAri(c);
        minimax = new NodeArbreNAri();

        nivells = h;
        setHashtable();
    }

    private void setHashtable() {

        indexPoints.put(Arrays.asList(),0);
        indexPoints.put(Arrays.asList(Colors.NEGRE),1);
        indexPoints.put(Arrays.asList(Colors.NEGRE, Colors.NEGRE),2);
        indexPoints.put(Arrays.asList(Colors.NEGRE, Colors.NEGRE, Colors.NEGRE),3);
        indexPoints.put(Arrays.asList(Colors.NEGRE, Colors.NEGRE, Colors.NEGRE, Colors.NEGRE),4);
        indexPoints.put(Arrays.asList(Colors.BLANC),5);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.NEGRE),6);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.NEGRE, Colors.NEGRE),7);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.NEGRE, Colors.NEGRE, Colors.NEGRE),8);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC),9);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC, Colors.NEGRE),10);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC, Colors.NEGRE, Colors.NEGRE),11);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC, Colors.BLANC),12);
        indexPoints.put(Arrays.asList(Colors.BLANC, Colors.BLANC, Colors.BLANC, Colors.BLANC),13);
    }

    public Colors getArrel() {
        return arrel.getInfo();
    }

    public NodeArbreNAri getNode() {
        return arrel;
    }

    private void construirArbreRec(NodeArbreNAri n, List<Colors> col, List<Colors> comb, int h) {

        for (int i = 0; h < nivells - 1 && i < col.size(); ++i) {

            comb.add(col.get(i));

            NodeArbreNAri next = new NodeArbreNAri(col.get(i));
            if (comb.size() == nivells) {
                next.setComb(comb);
                next.setLeaf();
                next.setS(true);
                //System.out.println(comb);
            }
            n.setChild(next);

            construirArbreRec(next, col, comb, h+1);

            int indexOfLastElement = comb.size() - 1;
            comb.remove(indexOfLastElement);
        }
    }

    public void construirArbre(List<Colors> colors) {

        List<Colors> comb = new ArrayList<Colors>();
        comb.add(arrel.getInfo());

        construirArbreRec(arrel, colors, comb, 0);
    }

    private void preordreRec(NodeArbreNAri n, PrintWriter writer) throws FileNotFoundException {

        try {
            
            Colors col = n.getInfo();
            //writer.print(col.name() + " ");
            System.out.print(col.name() + " ");

            for (int i = 0; i < n.getNumChilds(); ++i) {
                preordreRec(n.getChild(i), writer);
            }
            if (n.getNumChilds() == 0) {
                //writer.println("0");
                System.out.println("0");
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void eliminaColorRec(NodeArbreNAri n, Colors c) {

        if (n != null) {
            
            int i = 0; 
            while (i < n.getNumChilds()) {
                //System.out.print(n.getInfo());
                if (n.getChild(i).getInfo().equals(c)) n.removeChild(i);
                else {
                    //System.out.println(" -> no borrem");
                    eliminaColorRec(n.getChild(i), c);
                    ++i;
                }
            }
            for (int x = 0; x < n.getNumChilds(); ++x) {
                if (!n.getChild(x).esFulla() && n.getChild(x).getNumChilds() == 0) {
                    n.removeChild(x);
                }
            }
            
        }
    }

    public void eliminaColor(Colors c) {
        
        if (arrel.getInfo() != null) {
            if (arrel.getInfo().equals(c)) arrel = new NodeArbreNAri();
            else eliminaColorRec(arrel, c);

            if (arrel.getNumChilds() == 0) arrel = new NodeArbreNAri();
        }

    }

    public void escriurePreordre() throws FileNotFoundException {

        PrintWriter writer;
        writer = new PrintWriter("output.txt");
        if (arrel != null) preordreRec(arrel, writer);
        else System.out.println("0");
        System.out.println("###########################################");
        writer.close();
    }

    private boolean compleixCond(List<Colors> code, List<Colors> solution, List<Colors> seq) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {


        List<Colors> resposta = new ArrayList<>();
        Codi solucio = new Codi(Dificultat.FACIL);
        solucio.setCodi(solution);

        int NBres = 0;
        int NBcode = 0;
        int NNres = 0;
        int NNcode = 0;

        resposta = solucio.checkCodiSeq(seq);
        for (int i = 0; i < resposta.size(); ++i) {
            if (resposta.get(i).equals(Colors.BLANC)) NBres += 1;
            else NNres += 1;
        }
        for (int i = 0; i < code.size(); ++i) {
            if (code.get(i).equals(Colors.BLANC)) NBcode += 1;
            else NNcode += 1;
        }

        boolean r = true;
        if (NBres > NBcode) r = false;
        else if (seq.size() == nivells && (NBcode != NBres || NNcode != NNres)) r = false;
        return r;
    }

    private void eliminaCombRec(List<Colors> code, List<Colors> solution, List<Colors> seq, NodeArbreNAri n) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        if (n != null) {

            int i = 0;
            //seq.add(n.getInfo());

            while (i < n.getNumChilds()) {

                seq.add(n.getChild(i).getInfo());
                
                //System.out.print("seq: " + seq);

                if (compleixCond(code, solution, seq)) {
                    //System.out.println(" -> true");
                    eliminaCombRec(code, solution, seq, n.getChild(i));
                    ++i;
                }
                else {
                    //System.out.println(" -> false");
                    n.removeChild(i);
                }

                int indexOfLastElement = seq.size() - 1;
                seq.remove(indexOfLastElement);
            }
            int x = 0;
            while (x < n.getNumChilds()) {
                if (!n.getChild(x).esFulla() && n.getChild(x).getNumChilds() == 0) n.removeChild(x);
                else ++x;
            }
        }
    }

    public void eliminaComb(List<Colors> code, List<Colors> solution) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        List<Colors> seq = new ArrayList<Colors>();
        seq.add(arrel.getInfo());
        //System.out.println("code: " + code + " sol: " + solution);
        if (compleixCond(code, solution, seq)) eliminaCombRec(code, solution, seq, arrel);
        else arrel = new NodeArbreNAri();

        if (arrel.getNumChilds() == 0) arrel = new NodeArbreNAri();
    }

    public int getNivell() {
        return nivells;
    }

    private void getLastOneRec(NodeArbreNAri n) {

        if (n.esFulla()) minimax = n;
        else getLastOneRec(n.getChild(0));
    }

    public List<Colors> getLastOne() {

        getLastOneRec(arrel);
        return minimax.getComb();
    }

    public boolean onlyOne() {
        return (arrel.getNumChilds() == 1);
    }

    private void removeCombinationRec(NodeArbreNAri n, List<Colors> c, int i) {

        if (n != null) {
            for (int x = 0; x < n.getNumChilds(); ++x) {
                if (n.getChild(x).esFulla() && n.getChild(x).getInfo().equals(c.get(i))) {
                    n.removeChild(x);
                    return;
                }
                else if (n.getChild(x).getInfo().equals(c.get(i))) {
                    removeCombinationRec(n.getChild(x), c, i+1);
                }
            }
            int x = 0;
            while (x < n.getNumChilds()) {
                if (!n.getChild(x).esFulla() && n.getChild(x).getNumChilds() == 0) n.removeChild(x);
                else ++x;
            }
        }
    }

    public void removeCombination(List<Colors> c) {

        removeCombinationRec(arrel, c, 1);
    }

    private void recorreSegonArbre(NodeArbreNAri r, List<Colors> comb) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        if (r != null) {

            for (int i = 0; i < r.getNumChilds(); ++i) {
                recorreSegonArbre(r.getChild(i), comb);
            }
            if (r.esFulla()) {
                Codi code = new Codi(Dificultat.FACIL);
                code.setCodi(comb);

                List<Colors> res = new ArrayList<>();
                res = code.checkCodi(r.getComb());

                //System.out.println(res);
                int index = indexPoints.get(res);
                points.set(index, points.get(index)+1);
            }
        }
    }

    private void recorrePrimerArbre(List<ArbreNAri> r, NodeArbreNAri n) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        if (n != null) {

            for (int i = 0; i < n.getNumChilds(); ++i) {
                recorrePrimerArbre(r, n.getChild(i));
            }
            if (n.esFulla()) {

                //io.println("FULLA " + n.getComb());
                int max = -1;
                boolean isOnSet = false;

                if (points.size() != 0) points.clear();
                for (int p = 0; p < 14; ++p) points.add(0);

                //io.println("comb: " + n.getComb() + " points: ");
                for (int i = 0; i < r.size(); ++i) {
                    recorreSegonArbre(r.get(i).getNode(), n.getComb());
                }
                int maxcomb = points.get(0);
                for (int x = 1; x < points.size(); ++x) {
                    if (maxcomb <= points.get(x)) maxcomb = points.get(x);
                    if (x == points.size()-1 && points.get(x) == 1) isOnSet = true;
                }

                if (max == -1 || maxcomb > max) max = maxcomb;
                n.setPuntuacio(max);
                n.setS(isOnSet);
                //io.println(points + " " + maxcomb + " " + max);

                if (minimax.getInfo() == null || minimax.getPoints() > n.getPoints()) minimax = n;
                else if (minimax.getPoints() == n.getPoints() && n.getS() && !minimax.getS()) minimax = n;
                //io.println(" || minimax: " + minimax.getComb() + " points: " + minimax.getPoints());
            }
        }
    }

    public NodeArbreNAri calculateMinimax(List<ArbreNAri> r) throws codiAlreadyGenerated, InvalidCodiLength, InvalidCodiColors {

        minimax = new NodeArbreNAri();
        //if (points.size() != 0) points.clear();

        recorrePrimerArbre(r, arrel);
        return minimax;
    }
}
