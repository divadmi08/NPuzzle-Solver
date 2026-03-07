package com.mistri.puzzle_solver.puzzle.algorithms.solvers;

import com.mistri.puzzle_solver.puzzle.algorithms.Solver;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.Node;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;

import java.util.ArrayList;

public class IDAStarSolver implements Solver {

    private static final int FOUND = -1;

    @Override
    public ArrayList<Move> solve(PuzzleState start) {

        Node startNode = new Node(start);

        int threshold = startNode.getH();
        ArrayList<Move> path = new ArrayList<>();

        while (true) {

            int temp = dfs(startNode, 0, threshold, path, null);

            if (temp == FOUND)
                return path;

            if (temp == Integer.MAX_VALUE)
                return null;

            threshold = temp;
        }
    }

    private int dfs(Node node,
                    int g,
                    int threshold,
                    ArrayList<Move> path,
                    Move lastMove) {

        int f = g + node.getH();

        if (f > threshold)
            return f;

        if (node.getPuzzleState().isGoal())
            return FOUND;

        int min = Integer.MAX_VALUE;

        for (Move move : Move.values()) {

            // evita mosse opposte
            if (lastMove != null && move.isOpposite(lastMove))
                continue;

            PuzzleState nextState = node.getPuzzleState().applicaMossa(move);

            if (nextState == null)
                continue;

            Node nextNode = new Node(nextState);

            path.add(move);

            int temp = dfs(nextNode, g + 1, threshold, path, move);

            if (temp == FOUND)
                return FOUND;

            if (temp < min)
                min = temp;

            path.remove(path.size() - 1);
        }

        return min;
    }
}