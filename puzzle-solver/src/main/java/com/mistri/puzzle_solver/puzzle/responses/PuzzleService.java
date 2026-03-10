package com.mistri.puzzle_solver.puzzle.responses;

import com.mistri.puzzle_solver.puzzle.PuzzleGenerator;
import com.mistri.puzzle_solver.puzzle.PDB.PDBLoader;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.AStarSolver;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/puzzle")
public class PuzzleService {
    private final IDAStarSolver idaSolver;
    private final AStarSolver aStarSolver;
    private final PDBLoader pdbLoader;

    public PuzzleService(IDAStarSolver idaSolver, AStarSolver aStarSolver, PDBLoader pdbLoader) {
        this.idaSolver = idaSolver;
        this.aStarSolver = aStarSolver;
        this.pdbLoader = pdbLoader;
    }

    @PostMapping("/generate")
    public PuzzleState generatePuzzle(
            @RequestBody int size
    ) {
        return PuzzleGenerator.generaPuzzle(size, 100);
    }

    @PostMapping("/solve")
    public PuzzleSolutionResponse solvePuzzle(@RequestBody PuzzleSolveRequest request) {
        long totalStart = System.nanoTime();

        int[][] grid = request.getGrid();
        if (grid == null || grid.length == 0) {
            return new PuzzleSolutionResponse(false, null, 0, 0,
                    pdbLoader.getLoadElapsedMs(), pdbLoader.getTotalStates(),
                    pdbLoader.getModeDescription(), "NONE");
        }

        PuzzleState puzzle = new PuzzleState(grid);
        int size = puzzle.getGrandezza();
        Set<Integer> patternSet = buildPatternSet(size);
        SolverChoice choice = resolveSolver(request.getAlgorithm());

        boolean solvable = puzzle.isSolvable();
        List<Move> moves = null;
        long solveMs = 0;
        if (solvable) {
            long solveStart = System.nanoTime();
            moves = choice == SolverChoice.ASTAR
                    ? aStarSolver.solve(puzzle, patternSet)
                    : idaSolver.solve(puzzle, patternSet);
            solveMs = (System.nanoTime() - solveStart) / 1_000_000;
        }

        long totalMs = (System.nanoTime() - totalStart) / 1_000_000;
        return new PuzzleSolutionResponse(solvable, moves, solveMs, totalMs,
                pdbLoader.getLoadElapsedMs(), pdbLoader.getTotalStates(),
                pdbLoader.getModeDescription(), choice.name());
    }

    private Set<Integer> buildPatternSet(int size) {
        int max = size * size - 1;
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i <= max; i++) {
            set.add(i);
        }
        return Set.copyOf(set);
    }

    private SolverChoice resolveSolver(String algorithm) {
        if (algorithm == null || algorithm.isBlank()) {
            return SolverChoice.IDASTAR;
        }
        String value = algorithm.trim().toUpperCase();
        if (value.equals("A*") || value.equals("ASTAR") || value.equals("A_STAR")) {
            return SolverChoice.ASTAR;
        }
        return SolverChoice.IDASTAR;
    }

    private enum SolverChoice {
        IDASTAR,
        ASTAR
    }

}
