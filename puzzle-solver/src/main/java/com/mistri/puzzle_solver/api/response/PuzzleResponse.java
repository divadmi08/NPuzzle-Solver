package com.mistri.puzzle_solver.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PuzzleResponse {

    @JsonProperty("grid")
    private int[][] griglia;

    public PuzzleResponse(int[][] griglia) {
        this.griglia = griglia;
    }

    public int[][] getGriglia() {
        return griglia;
    }

    public void setGriglia(int[][] griglia) {
        this.griglia = griglia;
    }
}
