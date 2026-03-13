package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.core.algorithms.Heuristic;
import com.mistri.puzzle_solver.core.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.PuzzleState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolverBenchmarkTests {

    private static final Heuristic euristica = TestPdbSupport.EURISTICA;
    private final IDAStarSolver risolutoreIdaStar = new IDAStarSolver(euristica);

    @Test
    void idaStarBenchmarkSuPuzzle4x4DifficileNoto() {
        PuzzleState puzzleDifficile = new PuzzleState(new int[][]{
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 0, 9, 15},
                {8, 13, 6, 3}
        });

        long inizio = System.nanoTime();
        List<Move> soluzione = risolutoreIdaStar.risolvi(puzzleDifficile);
        long msTrascorsi = (System.nanoTime() - inizio) / 1_000_000;

        assertNotNull(soluzione);

        PuzzleState statoFinale = applicaMosse(puzzleDifficile, soluzione);

        System.out.println("==== IDA* benchmark hardest 4x4 ====");
        System.out.println("Start:");
        System.out.println(puzzleDifficile);
        System.out.println("Steps: " + soluzione.size());
        System.out.println("Time ms: " + msTrascorsi);
        System.out.println("Moves: " + soluzione);
        System.out.println("End:");
        System.out.println(statoFinale);

        assertTrue(statoFinale.isGoal());
    }

    private PuzzleState applicaMosse(PuzzleState stato, List<Move> mosse) {
        PuzzleState corrente = stato;
        for (Move mossa : mosse) {
            corrente = corrente.applicaMossa(mossa);
        }
        return corrente;
    }

    // PDB loader and heuristic are shared via TestPdbSupport.
}
