package com.mistri.puzzle_solver.core.algorithms.solvers;

import com.mistri.puzzle_solver.core.algorithms.Solver;
import com.mistri.puzzle_solver.core.algorithms.Heuristic;
import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.PuzzleState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class IDAStarSolver implements Solver {

    private static final int TROVATO = -1;

    private final Heuristic euristica;

    public IDAStarSolver(Heuristic euristica) {
        this.euristica = euristica;
    }

    @Override
    public List<Move> risolvi(PuzzleState statoIniziale) {
        if (!statoIniziale.isSolvable()) {
            return null;
        }

        int dimensione = statoIniziale.getDimensione();
        int[] tessere = statoIniziale.getTessere();
        int soglia = euristica.stima(tessere, dimensione);
        ArrayList<Move> percorso = new ArrayList<>();
        HashSet<Long> statiPercorso = new HashSet<>();
        statiPercorso.add(PuzzleState.codifica(tessere));

        while (true) {
            int temporaneo = ricercaProfondita(tessere, dimensione, statoIniziale.getPosizioneZero(), 0, soglia, percorso, statiPercorso, null);
            if (temporaneo == TROVATO) {
                return List.copyOf(percorso);
            }
            if (temporaneo == Integer.MAX_VALUE) {
                return null;
            }
            soglia = temporaneo;
        }
    }

    private int ricercaProfondita(int[] tessere, int dimensione, int posizioneZero, int costoG, int soglia,
                                  ArrayList<Move> percorso, Set<Long> statiPercorso, Move ultimaMossa) {
        int costoH = euristica.stima(tessere, dimensione);
        int costoF = costoG + costoH;
        if (costoF > soglia) {
            return costoF;
        }
        if (costoH == 0) {
            return TROVATO;
        }

        int minimo = Integer.MAX_VALUE;

        for (Move mossa : Move.values()) {
            if (ultimaMossa != null && mossa.isOpposite(ultimaMossa)) {
                continue;
            }

            int prossimaPosizioneZero = calcolaProssimaPosizioneZero(posizioneZero, dimensione, mossa);
            if (prossimaPosizioneZero < 0) {
                continue;
            }

            scambia(tessere, posizioneZero, prossimaPosizioneZero);
            long codificato = PuzzleState.codifica(tessere);
            if (!statiPercorso.add(codificato)) {
                scambia(tessere, posizioneZero, prossimaPosizioneZero);
                continue;
            }

            percorso.add(mossa);
            int temporaneo = ricercaProfondita(tessere, dimensione, prossimaPosizioneZero, costoG + 1, soglia, percorso, statiPercorso, mossa);
            if (temporaneo == TROVATO) {
                return TROVATO;
            }
            if (temporaneo < minimo) {
                minimo = temporaneo;
            }
            statiPercorso.remove(codificato);
            percorso.remove(percorso.size() - 1);
            scambia(tessere, posizioneZero, prossimaPosizioneZero);
        }

        return minimo;
    }

    private int calcolaProssimaPosizioneZero(int posizioneZero, int dimensione, Move mossa) {
        int rigaZero = posizioneZero / dimensione;
        int colonnaZero = posizioneZero % dimensione;
        int prossimaRiga = rigaZero + mossa.getDeltaRiga();
        int prossimaColonna = colonnaZero + mossa.getDeltaColonna();
        if (prossimaRiga < 0 || prossimaRiga >= dimensione || prossimaColonna < 0 || prossimaColonna >= dimensione) {
            return -1;
        }
        return prossimaRiga * dimensione + prossimaColonna;
    }

    private void scambia(int[] tessere, int primo, int secondo) {
        int temporaneo = tessere[primo];
        tessere[primo] = tessere[secondo];
        tessere[secondo] = temporaneo;
    }
}
