package com.mistri.puzzle_solver.puzzle.algorithms.solvers;

import com.mistri.puzzle_solver.puzzle.algorithms.Solver;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.Node;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;

import java.util.ArrayList;


public class IDAStarSolver implements Solver{

    private static final int FOUND = -1;

    @Override
    public ArrayList<Move> solve(PuzzleState start){
        Node nodoStart = new Node(start);
        int soglia = nodoStart.getH();
        int g = 0;
        ArrayList<Move> path = new ArrayList<>();

        while (true){
            int tmp = dfs(nodoStart,soglia,g,path);

            if (tmp == FOUND) return path;

            if (tmp == Integer.MAX_VALUE) return null;

            soglia = tmp;
        }
    }

    private int dfs(Node nodo, int soglia, int g, ArrayList<Move> path){
        int f = g + nodo.getH();

        if (f > soglia) return f;

        if (nodo.getPuzzleState().isGoal()) return FOUND;

        int min = Integer.MAX_VALUE;

        for (Move move : Move.values()) {

            PuzzleState next = nodo.getPuzzleState().applicaMossa(move);

            if (next == null) continue;

            path.add(move);

            int temp = dfs(new Node(next,nodo,move), soglia ,g + 1, path );

            if (temp == FOUND) {
                return FOUND;
            }

            if (temp < min) {
                min = temp;
            }

            path.removeLast();
        }

        return min;
    }


}
