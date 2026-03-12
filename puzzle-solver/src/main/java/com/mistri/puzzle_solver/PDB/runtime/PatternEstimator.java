package com.mistri.puzzle_solver.PDB.runtime;

interface PatternEstimator {
    int estimate(int[] board);
    long stateCount();
    String description();
}
