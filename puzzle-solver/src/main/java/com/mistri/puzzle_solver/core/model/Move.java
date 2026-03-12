package com.mistri.puzzle_solver.core.model;

public enum Move {

    SOPRA(-1, 0),
    SOTTO(1, 0),
    SINISTRA(0, -1),
    DESTRA(0, 1);

    public final int deltaRiga;
    public final int deltaColonna;

    Move(int deltaRiga, int deltaColonna) {
        this.deltaRiga = deltaRiga;
        this.deltaColonna = deltaColonna;
    }

    public int getDeltaRiga() {
        return deltaRiga;
    }

    public int getDeltaColonna() {
        return deltaColonna;
    }

    public boolean isOpposite(Move other) {
        return (this == SOPRA && other == SOTTO) ||
                (this == SOTTO && other == SOPRA) ||
                (this == SINISTRA && other == DESTRA) ||
                (this == DESTRA && other == SINISTRA);
    }
}
