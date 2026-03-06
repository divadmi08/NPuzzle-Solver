package com.mistri.puzzle_solver.puzzle.model;

public enum Move {

    SOPRA(-1,0),
    SOTTO(1,0),
    SINISTRA(0,-1),
    DESTRA(0,1);

    public final int movimentoY;
    public final int movimentoX;

    Move(int y, int x){
        this.movimentoY = y;
        this.movimentoX = x;
    }

    public int getMovimentoY() {
        return movimentoY;
    }

    public int getMovimentoX() {
        return movimentoX;
    }
}
