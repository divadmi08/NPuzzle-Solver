package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.puzzle.algorithms.solvers.AStarSolver;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@ConditionalOnProperty(name = "puzzle.benchmark.enabled", havingValue = "true")
public class Test implements CommandLineRunner {

    private final IDAStarSolver idaSolver;
    private final AStarSolver aStarSolver;

    public Test(IDAStarSolver idaSolver, AStarSolver aStarSolver) {
        this.idaSolver = idaSolver;
        this.aStarSolver = aStarSolver;
    }

    @Override
    public void run(String... args) throws Exception {

        int[][] hardest = {
                {15, 14,  8, 12},
                {10, 11,  9, 13},
                { 2,  6,  5,  1},
                { 3,  7,  4,  0}
        };

        PuzzleState puzzle = new PuzzleState(hardest);
        System.out.println("Puzzle:");
        System.out.println(puzzle);

        Set<Integer> patternSet = Set.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);

        // IDA*
        long startIDA = System.nanoTime();
        List<Move> solIDA = idaSolver.solve(puzzle, patternSet);
        long endIDA = System.nanoTime();
        System.out.println("IDA* → " + solIDA.size() + " mosse in " + (endIDA - startIDA)/1_000_000 + " ms");

        // A*
        long startA = System.nanoTime();
        List<Move> solA = aStarSolver.solve(puzzle, patternSet);
        long endA = System.nanoTime();
        System.out.println("A* → " + solA.size() + " mosse in " + (endA - startA)/1_000_000 + " ms");
    }
}
