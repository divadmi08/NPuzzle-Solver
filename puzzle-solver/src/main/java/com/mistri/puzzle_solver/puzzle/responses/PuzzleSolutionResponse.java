package com.mistri.puzzle_solver.puzzle.responses;

import com.mistri.puzzle_solver.puzzle.model.Move;

import java.util.List;

public class PuzzleSolutionResponse {
    private final boolean solvable;
    private final List<Move> moves;
    private final int moveCount;
    private final long solveMs;
    private final long totalMs;
    private final long pdbLoadMs;
    private final long pdbStates;
    private final String pdbMode;
    private final String algorithm;

    public PuzzleSolutionResponse(boolean solvable,
                                  List<Move> moves,
                                  long solveMs,
                                  long totalMs,
                                  long pdbLoadMs,
                                  long pdbStates,
                                  String pdbMode,
                                  String algorithm) {
        this.solvable = solvable;
        this.moves = moves;
        this.moveCount = (moves == null) ? 0 : moves.size();
        this.solveMs = solveMs;
        this.totalMs = totalMs;
        this.pdbLoadMs = pdbLoadMs;
        this.pdbStates = pdbStates;
        this.pdbMode = pdbMode;
        this.algorithm = algorithm;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public long getSolveMs() {
        return solveMs;
    }

    public long getTotalMs() {
        return totalMs;
    }

    public long getPdbLoadMs() {
        return pdbLoadMs;
    }

    public long getPdbStates() {
        return pdbStates;
    }

    public String getPdbMode() {
        return pdbMode;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
