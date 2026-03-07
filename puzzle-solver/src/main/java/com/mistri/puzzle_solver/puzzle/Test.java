package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.puzzle.algorithms.Solver;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.AStarSolver;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;

import java.util.List;

public class Test {
    static void main() {
        Solver aStar = new AStarSolver();
        Solver idaStar = new IDAStarSolver();
        int[][] hardest = {
                {15, 14,  8, 12},
                {10, 11,  9, 13},
                { 2,  6,  5,  1},
                { 3,  7,  4,  0}
        };
        PuzzleState p = new PuzzleState(hardest);
        System.out.println("Puzzle:");
        System.out.println(p);

        // IDA*
        long startIDA = System.nanoTime();
        List<Move> solIDA = idaStar.solve(p);
        long endIDA = System.nanoTime();
        System.out.println("IDA* → " + solIDA.size() + " mosse in " + (endIDA - startIDA) / 1_000_000 + " ms");

        // A*
        long startA = System.nanoTime();
        List<Move> solA = aStar.solve(p);
        long endA = System.nanoTime();
        System.out.println("A* → " + solA.size() + " mosse in " + (endA - startA) / 1_000_000 + " ms");
    }
}