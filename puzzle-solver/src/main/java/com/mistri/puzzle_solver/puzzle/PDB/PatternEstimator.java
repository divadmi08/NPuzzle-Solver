package com.mistri.puzzle_solver.puzzle.PDB;

interface PatternEstimator {
    int estimate(int[] board);
    long stateCount();
    String description();
}
