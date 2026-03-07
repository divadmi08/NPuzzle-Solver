package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.puzzle.algorithms.Solver;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.AStarSolver;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;

public class Test {
    static void main() {
        Solver aStar = new AStarSolver();
        Solver idaStar = new IDAStarSolver();
        PuzzleState p = PuzzleGenerator.generaPuzzle(4,200);
        System.out.println(p);
        System.out.println(idaStar.solve(p));

    }
}
