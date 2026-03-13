package com.mistri.puzzle_solver.core.algorithms;

import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.PuzzleState;


import java.util.List;

public interface Solver {
    List<Move> risolvi(PuzzleState statoIniziale);
}
