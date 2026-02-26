package main.java.com.mistri.npuzzle.solver;

import main.java.com.mistri.npuzzle.generator.PuzzleGenerator;
import main.java.com.mistri.npuzzle.model.PuzzleState;

public class Test {
    public static void main(String[] args) {
        Solver solver = new AStarSolver();
        PuzzleState stato = PuzzleGenerator.generaPuzzle(3,100);
        System.out.println(stato.toString());
        System.out.println("----------------");
        System.out.println(solver.solve(stato));


    }
}
