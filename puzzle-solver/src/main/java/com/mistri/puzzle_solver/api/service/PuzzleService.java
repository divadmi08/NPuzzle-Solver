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
import java.util.stream.Collectors;

@Service
public class PuzzleService {

    private final AStarSolver risolutoreAStar;
    private final IDAStarSolver risolutoreIdaStar;
    public PuzzleService(AStarSolver risolutoreAStar, IDAStarSolver risolutoreIdaStar) {
        this.risolutoreAStar = risolutoreAStar;
        this.risolutoreIdaStar = risolutoreIdaStar;
    }

    public PuzzleResponse genera(GenerateRequest richiesta) {
        int dimensione = richiesta.getDimensione();
        if (dimensione != 3 && dimensione != 4) {
            throw new IllegalArgumentException("Grid size must be 3 or 4");
        }

        int numeroMosse = (dimensione == 3) ? 50 : 200;
        PuzzleState statoPuzzle = PuzzleGenerator.generaPuzzle(dimensione, numeroMosse);
        
        // Verifica che il puzzle sia risolvibile
        if (!statoPuzzle.isSolvable()) {
            throw new RuntimeException("Generated puzzle is not solvable");
        }
        
        return new PuzzleResponse(statoPuzzle.getGriglia());
    }

    public PuzzleSolutionResponse risolvi(SolveRequest richiesta) {
        int[][] griglia = richiesta.getGriglia();
        if (griglia == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }

        int dimensione = griglia.length;
        if (dimensione != 3 && dimensione != 4) {
            throw new IllegalArgumentException("Only 3x3 and 4x4 puzzles supported");
        }

        PuzzleState statoPuzzle = new PuzzleState(griglia);
        if (!statoPuzzle.isSolvable()) {
            throw new IllegalArgumentException("Puzzle not solvable");
        }

        List<Move> soluzione = (dimensione == 3)
                ? risolutoreAStar.risolvi(statoPuzzle)
                : risolutoreIdaStar.risolvi(statoPuzzle);
        if (soluzione == null) {
            throw new RuntimeException("No solution found");
        }

        List<String> nomiMosse = soluzione.stream().map(Enum::name).collect(Collectors.toList());
        return new PuzzleSolutionResponse(nomiMosse);
    }
}
