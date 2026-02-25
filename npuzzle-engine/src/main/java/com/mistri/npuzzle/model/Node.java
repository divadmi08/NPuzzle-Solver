package main.java.com.mistri.npuzzle.model;

import main.java.com.mistri.npuzzle.solver.Heuristic;

public class Node implements Comparable<Node>{
    private PuzzleState puzzleState;
    private Node nodoPadre;
    private Move mossaPrecedente;
    int g;//passi precedenti
    int h;//previsione manhattan
    int f;//g + f

    public Node(PuzzleState puzzleState, Node nodoPadre, Move mossaPrecedente ){
        this.puzzleState = puzzleState;
        this.nodoPadre = nodoPadre;
        this.mossaPrecedente = mossaPrecedente;
        if(nodoPadre == null){
            this.g = 0;
        } else {
            this.g = nodoPadre.getG() + 1;
        }
        this.h = Heuristic.manhattan(puzzleState);
        this.f = g + h;
    }

    public Node(PuzzleState puzzleState){
        this(puzzleState, null, null);
    }

    @Override
    public int compareTo(Node altro) {
        int diff = Integer.compare(this.f, altro.f);
        if(diff != 0) return diff;
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
