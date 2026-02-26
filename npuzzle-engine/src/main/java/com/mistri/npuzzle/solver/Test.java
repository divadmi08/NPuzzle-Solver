package main.java.com.mistri.npuzzle.solver;

import main.java.com.mistri.npuzzle.model.PuzzleState;

public class Test {
    public static void main(String[] args) {
        int[][] griglia = {
                {0, 1, 3},
                {4, 2, 6},
                {7, 5, 8}
        };

        Solver solver = new AStarSolver();
        System.out.println(solver.solve(new PuzzleState(griglia)));
    }
}
