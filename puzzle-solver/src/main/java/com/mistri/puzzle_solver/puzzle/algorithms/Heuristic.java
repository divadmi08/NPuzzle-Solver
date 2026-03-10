package com.mistri.puzzle_solver.puzzle.algorithms;

import com.mistri.puzzle_solver.puzzle.model.PuzzleState;
import org.springframework.stereotype.Component;
import com.mistri.puzzle_solver.puzzle.PDB.PDBLoader;

import java.util.Set;

@Component
public class Heuristic {

    private final PDBLoader loader;

    public Heuristic(PDBLoader loader) {
        this.loader = loader;
    }

    public int heuristic(PuzzleState state, Set<Integer> patternSet) {
        return heuristic(state.getTilesUnsafe(), state.getGrandezza(), patternSet);
    }

    public int heuristic(int[] tiles, int size, Set<Integer> patternSet) {
        int manhattan = manhattan(tiles, size);
        int linearConflict = linearConflict(tiles, size);
        int pdbValue = lookupPDB(tiles, size, patternSet);
        return Math.max(manhattan + linearConflict, pdbValue);
    }

    private int manhattan(int[] tiles, int size) {
        int distanza = 0;
        for (int index = 0; index < size * size; index++) {
            int value = tiles[index];
            if (value == 0) {
                continue;
            }
            int row = index / size;
            int col = index % size;
            int targetRow = (value - 1) / size;
            int targetCol = (value - 1) % size;
            distanza += Math.abs(row - targetRow) + Math.abs(col - targetCol);
        }
        return distanza;
    }

    private int linearConflict(int[] tiles, int size) {
        int conflicts = 0;

        for (int row = 0; row < size; row++) {
            for (int colA = 0; colA < size; colA++) {
                int tileA = tiles[row * size + colA];
                if (tileA == 0 || targetRow(tileA, size) != row) {
                    continue;
                }
                int targetColA = targetCol(tileA, size);
                for (int colB = colA + 1; colB < size; colB++) {
                    int tileB = tiles[row * size + colB];
                    if (tileB == 0 || targetRow(tileB, size) != row) {
                        continue;
                    }
                    if (targetColA > targetCol(tileB, size)) {
                        conflicts++;
                    }
                }
            }
        }

        for (int col = 0; col < size; col++) {
            for (int rowA = 0; rowA < size; rowA++) {
                int tileA = tiles[rowA * size + col];
                if (tileA == 0 || targetCol(tileA, size) != col) {
                    continue;
                }
                int targetRowA = targetRow(tileA, size);
                for (int rowB = rowA + 1; rowB < size; rowB++) {
                    int tileB = tiles[rowB * size + col];
                    if (tileB == 0 || targetCol(tileB, size) != col) {
                        continue;
                    }
                    if (targetRowA > targetRow(tileB, size)) {
                        conflicts++;
                    }
                }
            }
        }

        return conflicts * 2;
    }

    private int targetRow(int tile, int size) {
        return (tile - 1) / size;
    }

    private int targetCol(int tile, int size) {
        return (tile - 1) % size;
    }

    private int lookupPDB(int[] tiles, int size, Set<Integer> patternSet) {
        return loader.estimate(tiles, size, patternSet);
    }
}
