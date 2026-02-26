package main.java.com.mistri.npuzzle.solver;

import main.java.com.mistri.npuzzle.model.Move;
import main.java.com.mistri.npuzzle.model.PuzzleState;

import java.util.List;

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
}