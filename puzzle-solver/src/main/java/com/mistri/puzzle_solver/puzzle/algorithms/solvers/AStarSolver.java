package com.mistri.puzzle_solver.puzzle.algorithms.solvers;

import com.mistri.puzzle_solver.puzzle.algorithms.Solver;
import com.mistri.puzzle_solver.puzzle.algorithms.Heuristic;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.Node;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AStarSolver implements Solver {

    private final Heuristic heuristic;

    public AStarSolver(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public List<Move> solve(PuzzleState start, Set<Integer> patternSet) {
        if (!start.isSolvable()) {
            return null;
        }

        PriorityQueue<Node> open = new PriorityQueue<>();
        HashSet<PuzzleState> closed = new HashSet<>();
        HashMap<PuzzleState, Integer> bestG = new HashMap<>();

        Node startNode = new Node(start, heuristic.heuristic(start, patternSet));
        open.add(startNode);
        bestG.put(start, 0);

        while (!open.isEmpty()) {
            Node node = open.poll();
            if (node.getPuzzleState().isGoal()) return retrivePath(node);

            if (node.getG() > bestG.getOrDefault(node.getPuzzleState(), Integer.MAX_VALUE))
                continue;

            closed.add(node.getPuzzleState());

            for (Move move : Move.values()) {
                PuzzleState nextState = node.getPuzzleState().applicaMossa(move);
                if (nextState == null || closed.contains(nextState)) continue;

                int g = node.getG() + 1;
                if (g >= bestG.getOrDefault(nextState, Integer.MAX_VALUE)) continue;

                bestG.put(nextState, g);
                int h = heuristic.heuristic(nextState, patternSet);
                Node nextNode = new Node(nextState, node, move, h);
                open.add(nextNode);
            }
        }

        return null;
    }

    private List<Move> retrivePath(Node node) {
        List<Move> moves = new ArrayList<>();
        while (node.getNodoPadre() != null) {
            moves.add(node.getMossaPrecedente());
            node = node.getNodoPadre();
        }
        Collections.reverse(moves);
        return moves;
    }
}
