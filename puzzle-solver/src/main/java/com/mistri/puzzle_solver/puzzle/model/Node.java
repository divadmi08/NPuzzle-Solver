package com.mistri.puzzle_solver.puzzle.model;

import com.mistri.puzzle_solver.puzzle.algorithms.Heuristic;


public class Node implements Comparable<Node>{

    private final PuzzleState puzzleState;
    private final Node nodoPadre;
    private final Move mossaPrecedente;

    private final int g;
    private final int h;
    private final int f;

    public Node(PuzzleState puzzleState, Node nodoPadre, Move mossaPrecedente){
        this.puzzleState = puzzleState;
        this.nodoPadre = nodoPadre;
        this.mossaPrecedente = mossaPrecedente;

        this.g = (nodoPadre == null) ? 0 : nodoPadre.g + 1;

        this.h = Heuristic.manhattanLinearConflict(puzzleState);
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

    public int getG() { return g; }

    public int getH() { return h; }

    public int getF() { return f; }

    public Node getNodoPadre() { return nodoPadre; }

    public Move getMossaPrecedente() { return mossaPrecedente; }

    public PuzzleState getPuzzleState() { return puzzleState; }
}