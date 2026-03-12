package com.mistri.puzzle_solver.core.model;

public class Node implements Comparable<Node> {

    private final PuzzleState statoPuzzle;
    private final Node nodoPadre;
    private final Move mossaPrecedente;

    private final int costoG;
    private final int costoH;
    private final int costoF;

    public Node(PuzzleState statoPuzzle, Node nodoPadre, Move mossaPrecedente, int costoH) {
        this.statoPuzzle = statoPuzzle;
        this.nodoPadre = nodoPadre;
        this.mossaPrecedente = mossaPrecedente;
        this.costoG = (nodoPadre == null) ? 0 : nodoPadre.costoG + 1;
        this.costoH = costoH;
        this.costoF = costoG + costoH;
    }

    public Node(PuzzleState statoPuzzle, int costoH) {
        this(statoPuzzle, null, null, costoH);
    }

    @Override
    public int compareTo(Node altro) {
        int differenza = Integer.compare(this.costoF, altro.costoF);
        if (differenza != 0) return differenza;
        return Integer.compare(this.costoH, altro.costoH);
    }

    public int getCostoG() {
        return costoG;
    }
    public int getCostoH() {
        return costoH;
    }
    public int getCostoF() {
        return costoF;
    }
    public Node getNodoPadre() {
        return nodoPadre;
    }
    public Move getMossaPrecedente() {
        return mossaPrecedente;
    }
    public PuzzleState getStatoPuzzle() {
        return statoPuzzle;
    }
}
