package com.mistri.puzzle_solver.puzzle.algorithms;

import com.mistri.puzzle_solver.puzzle.model.PuzzleState;

import com.mistri.puzzle_solver.puzzle.model.PuzzleState;

public class Heuristic {

    public static int manhattanLinearConflict(PuzzleState state) {

        int distanza = manhattan(state);
        int conflict = 0;

        int[][] grid = state.getGriglia();
        int size = state.getGrandezza();

        // controlla righe
        for (int r = 0; r < size; r++) {

            for (int c1 = 0; c1 < size; c1++) {

                int t1 = grid[r][c1];
                if (t1 == 0) continue;

                int goalRow1 = (t1 - 1) / size;
                int goalCol1 = (t1 - 1) % size;

                if (goalRow1 != r) continue;

                for (int c2 = c1 + 1; c2 < size; c2++) {

                    int t2 = grid[r][c2];
                    if (t2 == 0) continue;

                    int goalRow2 = (t2 - 1) / size;
                    int goalCol2 = (t2 - 1) % size;

                    if (goalRow2 == r && goalCol1 > goalCol2) {
                        conflict++;
                    }
                }
            }
        }

        // controlla colonne
        for (int c = 0; c < size; c++) {

            for (int r1 = 0; r1 < size; r1++) {

                int t1 = grid[r1][c];
                if (t1 == 0) continue;

                int goalRow1 = (t1 - 1) / size;
                int goalCol1 = (t1 - 1) % size;

                if (goalCol1 != c) continue;

                for (int r2 = r1 + 1; r2 < size; r2++) {

                    int t2 = grid[r2][c];
                    if (t2 == 0) continue;

                    int goalRow2 = (t2 - 1) / size;
                    int goalCol2 = (t2 - 1) % size;

                    if (goalCol2 == c && goalRow1 > goalRow2) {
                        conflict++;
                    }
                }
            }
        }

        return distanza + 2 * conflict;
    }


    public static int manhattan(PuzzleState state){

        int distanza = 0;
        int size = state.getGrandezza();
        int[][] grid = state.getGriglia();

        for(int r = 0; r < size; r++){
            for(int c = 0; c < size; c++){

                int value = grid[r][c];

                if(value == 0) continue;

                int targetRow = (value - 1) / size;
                int targetCol = (value - 1) % size;

                distanza += Math.abs(r - targetRow) + Math.abs(c - targetCol);
            }
        }

        return distanza;
    }
}
