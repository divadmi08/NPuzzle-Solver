package com.mistri.puzzle_solver.puzzle.algorithms;

import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;


import java.util.List;
import java.util.Set;

public interface Solver {
    List<Move> solve(PuzzleState start, Set<Integer> patternSet);
}
