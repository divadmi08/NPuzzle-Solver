package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.puzzle.PDB.PDBLoader;
import com.mistri.puzzle_solver.puzzle.algorithms.Heuristic;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolverBenchmarkTests {

    private static final Heuristic heuristic = createHeuristic();
    private final IDAStarSolver idaStarSolver = new IDAStarSolver(heuristic);

    @Test
    void idaStarBenchmarkOnKnownHard4x4Puzzle() {
        PuzzleState hardest = new PuzzleState(new int[][]{
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 0, 9, 15},
                {8, 13, 6, 3}
        });

        long start = System.nanoTime();
        List<Move> solution = idaStarSolver.solve(hardest, Set.of());
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        assertNotNull(solution);

        PuzzleState endState = applyMoves(hardest, solution);

        System.out.println("==== IDA* benchmark hardest 4x4 ====");
        System.out.println("Start:");
        System.out.println(hardest);
        System.out.println("Steps: " + solution.size());
        System.out.println("Time ms: " + elapsedMs);
        System.out.println("Moves: " + solution);
        System.out.println("End:");
        System.out.println(endState);

        assertTrue(endState.isGoal());
    }

    private PuzzleState applyMoves(PuzzleState state, List<Move> moves) {
        PuzzleState current = state;
        for (Move move : moves) {
            current = current.applicaMossa(move);
        }
        return current;
    }

    private static Heuristic createHeuristic() {
        try {
            return new Heuristic(new PDBLoader());
        } catch (Exception e) {
            throw new RuntimeException("Unable to create heuristic for benchmark tests", e);
        }
    }
}
