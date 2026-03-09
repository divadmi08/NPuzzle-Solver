package com.mistri.puzzle_solver.puzzle.PDB;

import java.util.Arrays;

final class PatternIndexer {

    private static final int CELL_COUNT = 16;

    private final int[] tiles;
    private final int[] tileToPatternIndex;
    private final long[] permutationBases;

    PatternIndexer(int... tiles) {
        this.tiles = tiles.clone();
        Arrays.sort(this.tiles);
        this.tileToPatternIndex = createTileIndex(this.tiles);
        this.permutationBases = createPermutationBases(this.tiles.length + 1);
    }

    long rankBoard(int[] board) {
        int[] positions = new int[tiles.length + 1];
        for (int index = 0; index < board.length; index++) {
            int value = board[index];
            if (value == 0) {
                positions[0] = index;
            } else {
                int patternIndex = tileToPatternIndex[value];
                if (patternIndex >= 0) {
                    positions[patternIndex + 1] = index;
                }
            }
        }
        return rankPositions(positions);
    }

    long rankPositions(int[] positions) {
        int[] available = createAvailableCells();
        long rank = 0;

        for (int i = 0; i < positions.length; i++) {
            int availableIndex = indexOf(available, CELL_COUNT - i, positions[i]);
            rank += availableIndex * permutationBases[i];
            removeAt(available, CELL_COUNT - i, availableIndex);
        }

        return rank;
    }

    void unrank(long rank, int[] positions) {
        int[] available = createAvailableCells();
        long remainingRank = rank;

        for (int i = 0; i < positions.length; i++) {
            long base = permutationBases[i];
            int availableIndex = (int) (remainingRank / base);
            remainingRank %= base;
            positions[i] = available[availableIndex];
            removeAt(available, CELL_COUNT - i, availableIndex);
        }
    }

    long stateCount() {
        return permutationBases[0] * CELL_COUNT;
    }

    int positionsLength() {
        return tiles.length + 1;
    }

    int[] goalPositions() {
        int[] positions = new int[tiles.length + 1];
        positions[0] = CELL_COUNT - 1;
        for (int i = 0; i < tiles.length; i++) {
            positions[i + 1] = tiles[i] - 1;
        }
        return positions;
    }

    String description() {
        return Arrays.toString(tiles);
    }

    private static int[] createTileIndex(int[] patternTiles) {
        int[] mapping = new int[CELL_COUNT];
        Arrays.fill(mapping, -1);
        for (int i = 0; i < patternTiles.length; i++) {
            mapping[patternTiles[i]] = i;
        }
        return mapping;
    }

    private static long[] createPermutationBases(int itemCount) {
        long[] bases = new long[itemCount];
        for (int i = 0; i < itemCount; i++) {
            long permutations = 1;
            for (int j = i + 1; j < itemCount; j++) {
                permutations *= CELL_COUNT - j;
            }
            bases[i] = permutations;
        }
        return bases;
    }

    private static int[] createAvailableCells() {
        int[] available = new int[CELL_COUNT];
        for (int i = 0; i < CELL_COUNT; i++) {
            available[i] = i;
        }
        return available;
    }

    private static int indexOf(int[] values, int length, int target) {
        for (int i = 0; i < length; i++) {
            if (values[i] == target) {
                return i;
            }
        }
        throw new IllegalStateException("Target cell not found while ranking pattern database state");
    }

    private static void removeAt(int[] values, int length, int index) {
        for (int i = index; i < length - 1; i++) {
            values[i] = values[i + 1];
        }
    }
}
