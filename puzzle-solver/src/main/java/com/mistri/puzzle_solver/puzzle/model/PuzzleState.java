package com.mistri.puzzle_solver.puzzle.model;

import java.util.Arrays;

public class PuzzleState {

    private final int[] tiles;
    private final int size;
    private final int zeroPos;
    private final int hashCode;

    public PuzzleState(int[][] griglia) {
        this(flatten(griglia), griglia.length);
    }

    private PuzzleState(int[] tiles, int size) {
        this(tiles, size, findZero(tiles));
    }

    private PuzzleState(int[] tiles, int size, int zeroPos) {
        this.tiles = tiles;
        this.size = size;
        this.zeroPos = zeroPos;
        this.hashCode = Arrays.hashCode(tiles);
    }

    private static int[] flatten(int[][] grid) {
        int size = grid.length;
        int[] flat = new int[size * size];
        int index = 0;
        for (int[] row : grid) {
            for (int value : row) {
                flat[index++] = value;
            }
        }
        return flat;
    }

    private static int findZero(int[] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                return i;
            }
        }
        throw new IllegalArgumentException("Puzzle senza zero");
    }

    public boolean isGoal() {
        int last = tiles.length - 1;
        for (int i = 0; i < last; i++) {
            if (tiles[i] != i + 1) {
                return false;
            }
        }
        return tiles[last] == 0;
    }

    public PuzzleState applicaMossa(Move move) {
        int zeroRow = zeroPos / size;
        int zeroCol = zeroPos % size;
        int nextRow = zeroRow + move.getMovimentoY();
        int nextCol = zeroCol + move.getMovimentoX();
        if (nextRow < 0 || nextRow >= size || nextCol < 0 || nextCol >= size) {
            return null;
        }

        int nextZeroPos = nextRow * size + nextCol;
        int[] nextTiles = tiles.clone();
        nextTiles[zeroPos] = nextTiles[nextZeroPos];
        nextTiles[nextZeroPos] = 0;
        return new PuzzleState(nextTiles, size, nextZeroPos);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PuzzleState other)) {
            return false;
        }
        return Arrays.equals(tiles, other.tiles);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public int getZeroY() {
        return zeroPos / size;
    }

    public int getZeroX() {
        return zeroPos % size;
    }

    public int getGrandezza() {
        return size;
    }

    public int[][] getGriglia() {
        int[][] grid = new int[size][size];
        for (int i = 0; i < tiles.length; i++) {
            grid[i / size][i % size] = tiles[i];
        }
        return grid;
    }

    public int[] getTiles() {
        return tiles.clone();
    }

    // Returns the internal array for read-only use to avoid copying in hot paths.
    public int[] getTilesUnsafe() {
        return tiles;
    }

    public int getTileAt(int index) {
        return tiles[index];
    }

    public int getZeroPos() {
        return zeroPos;
    }

    public boolean isSolvable() {
        int inversions = countInversions(tiles);
        if ((size & 1) == 1) {
            return (inversions & 1) == 0;
        }

        int blankRowFromBottom = size - (zeroPos / size);
        boolean blankOnEvenRowFromBottom = (blankRowFromBottom & 1) == 0;
        boolean inversionsEven = (inversions & 1) == 0;
        return blankOnEvenRowFromBottom != inversionsEven;
    }

    public static long encode(int[] tiles) {
        long encoded = 0L;
        for (int i = 0; i < tiles.length; i++) {
            encoded |= ((long) tiles[i] & 0xFL) << (i * 4);
        }
        return encoded;
    }

    private static int countInversions(int[] tiles) {
        int inversions = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < tiles.length; j++) {
                if (tiles[j] != 0 && tiles[i] > tiles[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tiles.length; i++) {
            builder.append(tiles[i]).append(' ');
            if ((i + 1) % size == 0) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }
}
