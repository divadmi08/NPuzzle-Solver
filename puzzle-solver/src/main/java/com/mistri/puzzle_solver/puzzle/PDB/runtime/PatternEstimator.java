package com.mistri.puzzle_solver.puzzle.PDB.runtime;

interface PatternEstimator {
    int estimate(int[] board);
    long stateCount();
    String description();
}
