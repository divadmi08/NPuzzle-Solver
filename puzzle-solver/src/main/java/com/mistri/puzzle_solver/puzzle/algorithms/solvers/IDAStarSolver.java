package com.mistri.puzzle_solver.puzzle.algorithms.solvers;

import com.mistri.puzzle_solver.puzzle.algorithms.Solver;
import com.mistri.puzzle_solver.puzzle.algorithms.Heuristic;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class IDAStarSolver implements Solver {

    private static final int FOUND = -1;

    private final Heuristic heuristic;

    public IDAStarSolver(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public List<Move> solve(PuzzleState start, Set<Integer> patternSet) {
        if (!start.isSolvable()) {
            return null;
        }

        int size = start.getGrandezza();
        int[] board = start.getTiles();
        int threshold = heuristic.heuristic(board, size, patternSet);
        ArrayList<Move> path = new ArrayList<>();
        HashSet<Long> pathStates = new HashSet<>();
        pathStates.add(PuzzleState.encode(board));

        while (true) {
            int temp = dfs(board, size, start.getZeroPos(), 0, threshold, path, pathStates, null, patternSet);
            if (temp == FOUND) {
                return List.copyOf(path);
            }
            if (temp == Integer.MAX_VALUE) {
                return null;
            }
            threshold = temp;
        }
    }

    private int dfs(int[] board, int size, int zeroPos, int g, int threshold, ArrayList<Move> path,
                    Set<Long> pathStates, Move lastMove, Set<Integer> patternSet) {
        int h = heuristic.heuristic(board, size, patternSet);
        int f = g + h;
        if (f > threshold) {
            return f;
        }
        if (h == 0) {
            return FOUND;
        }

        int min = Integer.MAX_VALUE;

        for (Move move : Move.values()) {
            if (lastMove != null && move.isOpposite(lastMove)) {
                continue;
            }

            int nextZeroPos = nextZeroPos(zeroPos, size, move);
            if (nextZeroPos < 0) {
                continue;
            }

            swap(board, zeroPos, nextZeroPos);
            long encoded = PuzzleState.encode(board);
            if (!pathStates.add(encoded)) {
                swap(board, zeroPos, nextZeroPos);
                continue;
            }

            path.add(move);
            int temp = dfs(board, size, nextZeroPos, g + 1, threshold, path, pathStates, move, patternSet);
            if (temp == FOUND) {
                return FOUND;
            }
            if (temp < min) {
                min = temp;
            }
            pathStates.remove(encoded);
            path.remove(path.size() - 1);
            swap(board, zeroPos, nextZeroPos);
        }

        return min;
    }

    private int nextZeroPos(int zeroPos, int size, Move move) {
        int zeroRow = zeroPos / size;
        int zeroCol = zeroPos % size;
        int nextRow = zeroRow + move.getMovimentoY();
        int nextCol = zeroCol + move.getMovimentoX();
        if (nextRow < 0 || nextRow >= size || nextCol < 0 || nextCol >= size) {
            return -1;
        }
        return nextRow * size + nextCol;
    }

    private void swap(int[] board, int first, int second) {
        int temp = board[first];
        board[first] = board[second];
        board[second] = temp;
    }
}
