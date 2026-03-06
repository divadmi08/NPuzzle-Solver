package com.mistri.puzzle_solver.puzzle;

import com.mistri.puzzle_solver.puzzle.model.Move;
import com.mistri.puzzle_solver.puzzle.model.PuzzleState;

import java.util.Random;

public class PuzzleGenerator {

    private static final Random random = new Random();

    //numMosse 50 per 3x3, 200 per 4x4
    public static PuzzleState generaPuzzle(int size, int numMosse) {
        int[][] goal = new int[size][size];
        int val = 1;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (y == size - 1 && x == size - 1)
                    goal[y][x] = 0;
                else
                    goal[y][x] = val++;
            }
        }

        PuzzleState puzzle = new PuzzleState(goal);
        Move ultimaMossa = null;

        for (int i = 0; i < numMosse; i++) {
            Move[] mossePossibili = Move.values();
            Move mossa;
            do {
                mossa = mossePossibili[random.nextInt(mossePossibili.length)];
            } while (puzzle.applicaMossa(mossa) == null || (ultimaMossa != null && sonoMosseOpposte(mossa, ultimaMossa)));

            PuzzleState nuovo = puzzle.applicaMossa(mossa);
            if (nuovo != null) {
                puzzle = nuovo;
                ultimaMossa = mossa;
            }
        }

        return puzzle;
    }

    private static boolean sonoMosseOpposte(Move a, Move b) {
        return (a == Move.SOPRA && b == Move.SOTTO) ||
                (a == Move.SOTTO && b == Move.SOPRA) ||
                (a == Move.SINISTRA && b == Move.DESTRA) ||
                (a == Move.DESTRA && b == Move.SINISTRA);
    }
}
