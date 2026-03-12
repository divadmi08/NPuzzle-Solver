package com.mistri.puzzle_solver.api.service;

import com.mistri.puzzle_solver.api.request.GenerateRequest;
import com.mistri.puzzle_solver.api.request.SolveRequest;
import com.mistri.puzzle_solver.api.response.PuzzleResponse;
import com.mistri.puzzle_solver.api.response.PuzzleSolutionResponse;
import com.mistri.puzzle_solver.core.algorithms.solvers.AStarSolver;
import com.mistri.puzzle_solver.core.algorithms.solvers.IDAStarSolver;
import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.PuzzleState;
import com.mistri.puzzle_solver.generator.PuzzleGenerator;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PuzzleService {

    private final AStarSolver aStarSolver;
    private final IDAStarSolver idaStarSolver;
    private static final Set<Integer> PATTERN_SET_VUOTO = Set.of();

    public PuzzleService(AStarSolver aStarSolver, IDAStarSolver idaStarSolver) {
        this.aStarSolver = aStarSolver;
        this.idaStarSolver = idaStarSolver;
    }

    public PuzzleResponse generate(GenerateRequest richiesta) {
        int dimensione = richiesta.getDimensione();
        if (dimensione != 3 && dimensione != 4) {
            throw new IllegalArgumentException("Grid size must be 3 or 4");
        }

        int mosse = (dimensione == 3) ? 50 : 200;
        PuzzleState puzzle = PuzzleGenerator.generaPuzzle(dimensione, mosse);
        return new PuzzleResponse(puzzle.getGriglia());
    }

    public PuzzleSolutionResponse solve(SolveRequest richiesta) {
        int[][] griglia = richiesta.getGriglia();
        if (griglia == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }

        int dimensione = griglia.length;
        if (dimensione != 3 && dimensione != 4) {
            throw new IllegalArgumentException("Only 3x3 and 4x4 puzzles supported");
        }

        PuzzleState stato = new PuzzleState(griglia);
        if (!stato.isSolvable()) {
            throw new IllegalArgumentException("Puzzle not solvable");
        }

        List<Move> soluzione = (dimensione == 3)
                ? aStarSolver.solve(stato, PATTERN_SET_VUOTO)
                : idaStarSolver.solve(stato, PATTERN_SET_VUOTO);
        if (soluzione == null) {
            throw new RuntimeException("No solution found");
        }

        List<String> mosse = soluzione.stream().map(Enum::name).collect(Collectors.toList());
        return new PuzzleSolutionResponse(mosse);
    }
}
