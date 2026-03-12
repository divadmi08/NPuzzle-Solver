package com.mistri.puzzle_solver.core.model;

public class Node implements Comparable<Node> {

    private final PuzzleState puzzleState;
    private final Node nodoPadre;
    private final Move mossaPrecedente;

    private final int g;
    private final int h;
    private final int f;

    public Node(PuzzleState puzzleState, Node nodoPadre, Move mossaPrecedente, int h) {
        this.puzzleState = puzzleState;
        this.nodoPadre = nodoPadre;
        this.mossaPrecedente = mossaPrecedente;
        this.g = (nodoPadre == null) ? 0 : nodoPadre.g + 1;
        this.h = h;
        this.f = g + h;
    }

    public Node(PuzzleState puzzleState, int h) {
        this(puzzleState, null, null, h);
    }

    @Override
    public int compareTo(Node altro) {
        int diff = Integer.compare(this.f, altro.f);
        if (diff != 0) return diff;
        return Integer.compare(this.h, altro.h);
    }

    public int getG() {
        return g;
    }
    public int getH() {
        return h;
    }
    public int getF() {
        return f;
    }
    public Node getNodoPadre() {
        return nodoPadre;
    }
    public Move getMossaPrecedente() {
        return mossaPrecedente;
    }
    public PuzzleState getPuzzleState() {
        return puzzleState;
    }
}