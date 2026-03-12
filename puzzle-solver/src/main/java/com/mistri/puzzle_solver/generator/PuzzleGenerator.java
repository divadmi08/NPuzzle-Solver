package com.mistri.puzzle_solver.generator;

import com.mistri.puzzle_solver.core.model.Move;
import com.mistri.puzzle_solver.core.model.PuzzleState;

import java.util.Random;

public class PuzzleGenerator {

    private static final Random casuale = new Random();

    //numMosse 50 per 3x3, 200 per 4x4
    public static PuzzleState generaPuzzle(int dimensione, int numeroMosse) {
        int[][] grigliaObiettivo = new int[dimensione][dimensione];
        int valore = 1;
        for (int riga = 0; riga < dimensione; riga++) {
            for (int colonna = 0; colonna < dimensione; colonna++) {
                if (riga == dimensione - 1 && colonna == dimensione - 1)
                    grigliaObiettivo[riga][colonna] = 0;
                else
                    grigliaObiettivo[riga][colonna] = valore++;
            }
        }

        PuzzleState statoPuzzle = new PuzzleState(grigliaObiettivo);
        Move ultimaMossa = null;

        for (int i = 0; i < numeroMosse; i++) {
            Move[] mossePossibili = Move.values();
            PuzzleState statoNuovo = null;
            Move mossa;
            do {
                mossa = mossePossibili[casuale.nextInt(mossePossibili.length)];
                if (ultimaMossa == null || !mossa.isOpposite(ultimaMossa)) {
                    statoNuovo = statoPuzzle.applicaMossa(mossa);
                }
            } while (statoNuovo == null);

            statoPuzzle = statoNuovo;
            ultimaMossa = mossa;
        }

        return statoPuzzle;
    }
}
