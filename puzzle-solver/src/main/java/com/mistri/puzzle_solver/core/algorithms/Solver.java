package com.mistri.puzzle_solver.core.algorithms;

import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.PuzzleState;


import java.util.List;
import java.util.Set;

public interface Solver {
    List<Move> solve(PuzzleState start, Set<Integer> patternSet);
}
