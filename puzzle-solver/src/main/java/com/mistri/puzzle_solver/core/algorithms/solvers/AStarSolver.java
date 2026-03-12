package com.mistri.puzzle_solver.core.algorithms.solvers;

import com.mistri.puzzle_solver.core.algorithms.Solver;
import com.mistri.puzzle_solver.core.algorithms.Heuristic;
import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.Node;
import com.mistri.puzzle_solver.core.model.PuzzleState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

@Component
public class AStarSolver implements Solver {

    private final Heuristic euristica;

    public AStarSolver(Heuristic euristica) {
        this.euristica = euristica;
    }

    @Override
    public List<Move> risolvi(PuzzleState statoIniziale) {
        if (!statoIniziale.isSolvable()) {
            return null;
        }

        PriorityQueue<Node> aperti = new PriorityQueue<>();
        HashSet<PuzzleState> chiusi = new HashSet<>();
        HashMap<PuzzleState, Integer> migliorG = new HashMap<>();

        Node nodoIniziale = new Node(statoIniziale, euristica.stima(statoIniziale));
        aperti.add(nodoIniziale);
        migliorG.put(statoIniziale, 0);

        while (!aperti.isEmpty()) {
            Node nodo = aperti.poll();
            if (nodo.getStatoPuzzle().isGoal()) return ricostruisciPercorso(nodo);

            if (nodo.getCostoG() > migliorG.getOrDefault(nodo.getStatoPuzzle(), Integer.MAX_VALUE))
                continue;

            chiusi.add(nodo.getStatoPuzzle());

            for (Move mossa : Move.values()) {
                PuzzleState statoSuccessivo = nodo.getStatoPuzzle().applicaMossa(mossa);
                if (statoSuccessivo == null || chiusi.contains(statoSuccessivo)) continue;

                int costoG = nodo.getCostoG() + 1;
                if (costoG >= migliorG.getOrDefault(statoSuccessivo, Integer.MAX_VALUE)) continue;

                migliorG.put(statoSuccessivo, costoG);
                int costoH = euristica.stima(statoSuccessivo);
                Node nodoSuccessivo = new Node(statoSuccessivo, nodo, mossa, costoH);
                aperti.add(nodoSuccessivo);
            }
        }

        return null;
    }

    private List<Move> ricostruisciPercorso(Node nodo) {
        List<Move> mosse = new ArrayList<>();
        while (nodo.getNodoPadre() != null) {
            mosse.add(nodo.getMossaPrecedente());
            nodo = nodo.getNodoPadre();
        }
        Collections.reverse(mosse);
        return mosse;
    }
}
