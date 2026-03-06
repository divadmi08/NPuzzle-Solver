package com.mistri.puzzle_solver.puzzle.algorithms.solvers;

import com.mistri.puzzle_solver.puzzle.algorithms.Solver;
import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.Node;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;

import java.util.*;

public class AStarSolver implements Solver {
    //crea open PQ
    //crea closed HashSet
    //inserisci start node in open

    //LOOP:
    //prendi nodo migliore con poll()
    //se è goal → fine
    //aggiungi state al closed
    //genera vicini
    //per ogni vicino:
    //se non in closed
    //crea Node
    //aggiungi a open

    @Override
    public List<Move> solve(PuzzleState start) {
        PriorityQueue<Node> codaOpen = new PriorityQueue<>();
        HashSet<PuzzleState> setClosed = new HashSet<>();
        codaOpen.add(new Node(start, null, null));
        while (!codaOpen.isEmpty()) {
            Node nodo = codaOpen.poll();
            if (nodo.getPuzzleState().isGoal()) return ritrovaPath(nodo);
            setClosed.add(nodo.getPuzzleState());
            for (Move move : Move.values()) {
                PuzzleState vicini = nodo.getPuzzleState().applicaMossa(move);
                if (vicini == null)
                    continue;
                if (setClosed.contains(vicini))
                    continue;
                Node figlio = new Node(vicini, nodo, move);
                codaOpen.add(figlio);
            }
        }
        return null;
    }

    public List<Move> ritrovaPath(Node nodoFinale) {
        List<Move> listaMosse = new ArrayList<>();
        Node nodo = nodoFinale;
        while (nodo.getNodoPadre() != null) {
            listaMosse.add(nodo.getMossaPrecedente());
            nodo = nodo.getNodoPadre();
        }
        Collections.reverse(listaMosse);
        return listaMosse;
    }

}
