package com.mistri.puzzle_solver.core.model;

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

    public boolean isOpposite(Move other) {
        return (this == SOPRA && other == SOTTO) ||
                (this == SOTTO && other == SOPRA) ||
                (this == SINISTRA && other == DESTRA) ||
                (this == DESTRA && other == SINISTRA);
    }
}
