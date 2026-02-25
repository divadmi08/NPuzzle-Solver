package main.java.com.mistri.npuzzle.solver;

import main.java.com.mistri.npuzzle.model.Move;
import main.java.com.mistri.npuzzle.model.PuzzleState;

import java.util.List;

public interface Solver {
    List<Move> solve(PuzzleState start);

}
