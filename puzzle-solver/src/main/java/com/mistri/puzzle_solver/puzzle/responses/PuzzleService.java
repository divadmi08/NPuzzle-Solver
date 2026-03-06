package com.mistri.puzzle_solver.puzzle.responses;

import com.mistri.puzzle_solver.puzzle.PuzzleGenerator;
import com.mistri.puzzle_solver.puzzle.algorithms.solvers.AStarSolver;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/puzzle")
public class PuzzleService {
    @PostMapping("/generate")
    public PuzzleState generatePuzzle(
            @RequestBody int size
    ) {
        return PuzzleGenerator.generaPuzzle(size, 100);
    }

    @PostMapping("/solve")
    public List<Move> solvePuzzle(
            @RequestBody int[][] matrix
            ) {
        PuzzleState start = new PuzzleState(matrix);
        AStarSolver solver = new AStarSolver();
        return solver.solve(start);
    }
}
