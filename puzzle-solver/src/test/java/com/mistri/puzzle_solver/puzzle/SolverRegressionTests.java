package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.core.algorithms.Heuristic;
import com.mistri.puzzle_solver.core.algorithms.solvers.AStarSolver;
import com.mistri.puzzle_solver.core.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.PuzzleState;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolverRegressionTests {

    private static final Heuristic heuristic = TestPdbSupport.HEURISTIC;
    private final AStarSolver aStarSolver = new AStarSolver(heuristic);
    private final IDAStarSolver idaStarSolver = new IDAStarSolver(heuristic);

    @Test
    void aStarSolvesSimple3x3PuzzleOptimally() {
        PuzzleState start = new PuzzleState(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        });

        List<Move> solution = aStarSolver.solve(start, Set.of());
        PuzzleState endState = applyMoves(start, solution);

        printSolution("A*", start, solution, endState);

        assertNotNull(solution);
        assertEquals(1, solution.size());
        assertTrue(endState.isGoal());
    }

    @Test
    void idaStarSolvesSimple4x4Puzzle() {
        PuzzleState start = new PuzzleState(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 0, 15}
        });

        List<Move> solution = idaStarSolver.solve(start, Set.of());
        PuzzleState endState = applyMoves(start, solution);

        printSolution("IDA*", start, solution, endState);

        assertNotNull(solution);
        assertEquals(1, solution.size());
        assertTrue(endState.isGoal());
    }

    @Test
    void aStarSolvesHarder3x3Puzzle() {
        PuzzleState start = new PuzzleState(new int[][]{
                {1, 3, 6},
                {5, 0, 2},
                {4, 7, 8}
        });

        List<Move> solution = aStarSolver.solve(start, Set.of());
        PuzzleState endState = applyMoves(start, solution);

        printSolution("A* harder", start, solution, endState);

        assertNotNull(solution);
        assertEquals(8, solution.size());
        assertTrue(endState.isGoal());
    }

    @Test
    void idaStarSolvesHarder4x4Puzzle() {
        PuzzleState start = new PuzzleState(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 8, 12},
                {9, 10, 7, 15},
                {13, 14, 11, 0}
        });

        List<Move> solution = idaStarSolver.solve(start, Set.of());
        PuzzleState endState = applyMoves(start, solution);

        printSolution("IDA* harder", start, solution, endState);

        assertNotNull(solution);
        assertEquals(6, solution.size());
        assertTrue(endState.isGoal());
    }

    @Test
    void linearConflictImprovesHeuristicWhenTilesAreReversed() {
        PuzzleState state = new PuzzleState(new int[][]{
                {2, 1, 3},
                {4, 5, 6},
                {7, 8, 0}
        });

        assertEquals(4, heuristic.heuristic(state, Set.of()));
    }

    @Test
    void unsolvable3x3IsRejectedBeforeSearch() {
        PuzzleState start = new PuzzleState(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}
        });

        assertNull(aStarSolver.solve(start, Set.of()));
    }

    @Test
    void unsolvable4x4IsRejectedBeforeSearch() {
        PuzzleState start = new PuzzleState(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 15, 14, 0}
        });

        assertNull(idaStarSolver.solve(start, Set.of()));
    }

    private PuzzleState applyMoves(PuzzleState state, List<Move> moves) {
        PuzzleState current = state;
        for (Move move : moves) {
            current = current.applicaMossa(move);
        }
        return current;
    }

    private void printSolution(String solverName, PuzzleState start, List<Move> solution, PuzzleState endState) {
        System.out.println("==== " + solverName + " ====");
        System.out.println("Start:");
        System.out.println(start);
        System.out.println("Moves: " + solution);
        System.out.println("Steps: " + solution.size());
        System.out.println("End:");
        System.out.println(endState);
    }
}
