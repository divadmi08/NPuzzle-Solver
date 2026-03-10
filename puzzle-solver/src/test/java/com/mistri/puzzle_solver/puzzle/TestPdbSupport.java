package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.puzzle.PDB.PDBLoader;
import com.mistri.puzzle_solver.puzzle.algorithms.Heuristic;

final class TestPdbSupport {
    private static final String DEFAULT_PDB_DIR = "pdb";

    static final PDBLoader PDB_LOADER = new PDBLoader(resolvePdbDir());
    static final Heuristic HEURISTIC = new Heuristic(PDB_LOADER);

    private TestPdbSupport() {
    }

    private static String resolvePdbDir() {
        String env = System.getenv("PUZZLE_PDB_DIR");
        if (env == null || env.isBlank()) {
            return DEFAULT_PDB_DIR;
        }
        return env;
    }
}
