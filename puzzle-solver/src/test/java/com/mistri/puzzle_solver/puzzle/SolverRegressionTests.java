package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.core.algorithms.Heuristic;
import com.mistri.puzzle_solver.core.algorithms.solvers.AStarSolver;
import com.mistri.puzzle_solver.core.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.PuzzleState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolverRegressionTests {

    private static final Heuristic euristica = TestPdbSupport.EURISTICA;
    private final AStarSolver risolutoreAStar = new AStarSolver(euristica);
    private final IDAStarSolver risolutoreIdaStar = new IDAStarSolver(euristica);

    @Test
    void aStarRisolvePuzzle3x3SempliceInModoOttimale() {
        PuzzleState statoIniziale = new PuzzleState(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        });

        List<Move> soluzione = risolutoreAStar.risolvi(statoIniziale);
        PuzzleState statoFinale = applicaMosse(statoIniziale, soluzione);

        stampaSoluzione("A*", statoIniziale, soluzione, statoFinale);

        assertNotNull(soluzione);
        assertEquals(1, soluzione.size());
        assertTrue(statoFinale.isGoal());
    }

    @Test
    void idaStarRisolvePuzzle4x4Semplice() {
        PuzzleState statoIniziale = new PuzzleState(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 0, 15}
        });

        List<Move> soluzione = risolutoreIdaStar.risolvi(statoIniziale);
        PuzzleState statoFinale = applicaMosse(statoIniziale, soluzione);

        stampaSoluzione("IDA*", statoIniziale, soluzione, statoFinale);

        assertNotNull(soluzione);
        assertEquals(1, soluzione.size());
        assertTrue(statoFinale.isGoal());
    }

    @Test
    void aStarRisolvePuzzle3x3PiuDifficile() {
        PuzzleState statoIniziale = new PuzzleState(new int[][]{
                {1, 3, 6},
                {5, 0, 2},
                {4, 7, 8}
        });

        List<Move> soluzione = risolutoreAStar.risolvi(statoIniziale);
        PuzzleState statoFinale = applicaMosse(statoIniziale, soluzione);

        stampaSoluzione("A* harder", statoIniziale, soluzione, statoFinale);

        assertNotNull(soluzione);
        assertEquals(8, soluzione.size());
        assertTrue(statoFinale.isGoal());
    }

    @Test
    void idaStarRisolvePuzzle4x4PiuDifficile() {
        PuzzleState statoIniziale = new PuzzleState(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 8, 12},
                {9, 10, 7, 15},
                {13, 14, 11, 0}
        });

        List<Move> soluzione = risolutoreIdaStar.risolvi(statoIniziale);
        PuzzleState statoFinale = applicaMosse(statoIniziale, soluzione);

        stampaSoluzione("IDA* harder", statoIniziale, soluzione, statoFinale);

        assertNotNull(soluzione);
        assertEquals(6, soluzione.size());
        assertTrue(statoFinale.isGoal());
    }

    @Test
    void conflittoLineareMiglioraEuristicaConTessereInvertite() {
        PuzzleState stato = new PuzzleState(new int[][]{
                {2, 1, 3},
                {4, 5, 6},
                {7, 8, 0}
        });

        assertEquals(4, euristica.stima(stato));
    }

    @Test
    void puzzle3x3IrresolubileVieneRifiutatoPrimaDellaRicerca() {
        PuzzleState statoIniziale = new PuzzleState(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}
        });

        assertNull(risolutoreAStar.risolvi(statoIniziale));
    }

    @Test
    void puzzle4x4IrresolubileVieneRifiutatoPrimaDellaRicerca() {
        PuzzleState statoIniziale = new PuzzleState(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 15, 14, 0}
        });

        assertNull(risolutoreIdaStar.risolvi(statoIniziale));
    }

    private PuzzleState applicaMosse(PuzzleState stato, List<Move> mosse) {
        PuzzleState corrente = stato;
        for (Move mossa : mosse) {
            corrente = corrente.applicaMossa(mossa);
        }
        return corrente;
    }

    private void stampaSoluzione(String nomeRisolutore, PuzzleState statoIniziale, List<Move> soluzione, PuzzleState statoFinale) {
        System.out.println("==== " + nomeRisolutore + " ====");
        System.out.println("Start:");
        System.out.println(statoIniziale);
        System.out.println("Moves: " + soluzione);
        System.out.println("Steps: " + soluzione.size());
        System.out.println("End:");
        System.out.println(statoFinale);
    }
}
