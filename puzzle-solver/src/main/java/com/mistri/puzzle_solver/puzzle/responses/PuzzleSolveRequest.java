package com.mistri.puzzle_solver.puzzle.responses;

public class PuzzleSolveRequest {
    private int[][] grid;
    private String algorithm;

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
